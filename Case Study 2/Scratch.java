import java.util.Scanner;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Main {

    private String session = "";
    
    public static void main(String[] args) {
        new Main().init();
    }
    
    public void init(){
       Scanner sc = new Scanner(System.in);
       while(sc.hasNext()){
            String[] in = sc.nextLine().split(","); //id, uname, role
            createSession(in);
            System.out.println("ID: " + in[0] + " | Username: " + in[1] + " | Role: " + in[2]  + " | SessionID: " + decrypt(this.session));
       }
    }
    
    public void resetSession(){
        this.session = "";
    }

    public void createSession(String[] inputs){
        createSession(Integer.parseInt(inputs[0]), inputs[1], Integer.parseInt(inputs[2]));
    }
    
    public void createSession(final int id, final String uname, final int role){
        byte[] array = new byte[16]; // length is bounded by 7
        new Random().nextBytes(array);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");
        String datetime = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss").format(new Date());
        String sessionID = Base64.getEncoder().encodeToString((new String(array, Charset.forName("UTF-8")) + "," + (id+"") + "," + uname + "," + (role+"")).getBytes());
        
        this.session = encrypt(sessionID);
        
        sessionID = null;
        datetime = null;
    }
    
    public Byte getSessionRole(){
        if(this.session == "" || this.session == null)
            return Integer.valueOf(0).byteValue();
        return Integer.valueOf(Integer.parseInt(extractSessionValue()[3])).byteValue();
    }
    
    public Byte getSessionUserID(){
        if(this.session == "" || this.session == null)
            return Integer.valueOf(-1).byteValue();
        return Integer.valueOf(Integer.parseInt(extractSessionValue()[1])).byteValue();
    }
    
    public Byte getSessionUserName(){
        if(this.session == "" || this.session == null)
            return Integer.valueOf(-1).byteValue();
        return Integer.valueOf(Integer.parseInt(extractSessionValue()[2])).byteValue();
    }
    
    public String[] extractSessionValue(){
        if(this.session == "" || this.session == null)
            return null;
        return decrypt(this.session).split(",");
    }
    
    /**
     * Gets AES configuration for hashing function.
     * @return SecretKeySpec object that contains configured AES.
     */
    private final SecretKeySpec AES(){
        final String privateKey = "C5SecDV_s11_5esSi0n";
        MessageDigest sha;
        byte[] key;
        try {
            key = privateKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 24);
            return new SecretKeySpec(key, "AES");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            //System.out.println("AES Error: " + ex.getLocalizedMessage());
        }
        return null;
    }
    
    /**
     * Gets the hash of a given plain-text password.
     * @param plaintext
     * @return 
     */
    private String encrypt(final String plaintext){
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, AES());
            return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
            //System.out.println("AES Error: " + ex.getLocalizedMessage());
        }
        return null;
    }
    
    private String decrypt(final String ciphertext){
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, AES());
            byte[] plaintext = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(plaintext);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            //System.out.println("AES Error: " + ex.getLocalizedMessage());
        }
        return null;
    }
}
