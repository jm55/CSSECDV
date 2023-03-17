package Controller;

import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import View.Frame;
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
    
    public SQLite sqlite;
    private String session = "";
    
    public static void main(String[] args) {
        new Main().init();
    }
    
    public void init(){
        // Initialize a driver object
        sqlite = new SQLite();
        
        //buildDB();
        checkLogs();
        checkUsers();
        
        //Initialize User Interface
        Frame frame = new Frame();
        frame.init(this);
    }
    
    public void resetSession(){
        this.session = "";
    }
    
    public void createSession(final int id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");
        String datetime = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss").format(new Date());
        String sessionID = Base64.getEncoder().encodeToString(datetime.getBytes()) + "," + (id+"")+ "," +sqlite.getUserName(id) + "," + (sqlite.getUserRole(id)+"");
        
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
    
    private void checkUsers(){
        ArrayList<User> users = sqlite.getUsers();
        for(int i = 0; i < users.size(); i++){
            User u = users.get(i);
            System.out.println(u.getId() + ": " + u.getUsername() + " " + u.getRole() + " " + u.getLocked());
        }
        users = null;
    }
    
    /**
     * Prints the logs from the DB.
     * Written for purposes of testing.
     */
    private void checkLogs(){
        ArrayList<Logs> logs = sqlite.getLogs();
        for (int i = 0; i < logs.size(); i++){
            Logs log = logs.get(i);
            System.out.println(log.getTimestamp() + ": [" + log.getEvent() + "] " + log.getDesc() + " by " + log.getUsername());
        }
        logs = null;
    }
    
    /**
     * Builds the DB with default values.
     * Contains hashed password instead of plain-text.
     */
    private void buildDB(){
        // Create a database
        sqlite.createNewDatabase();

        // Drop users table if needed
        sqlite.dropHistoryTable();
        sqlite.dropLogsTable();
        sqlite.dropProductTable();
        sqlite.dropUserTable();

        // Create users table if not exist
        sqlite.createHistoryTable();
        sqlite.createLogsTable();
        sqlite.createProductTable();
        sqlite.createUserTable();

        // Add sample history
        sqlite.addHistory("admin", "Antivirus", 1, "2019-04-03 14:30:00.000");
        sqlite.addHistory("manager", "Firewall", 1, "2019-04-03 14:30:01.000");
        sqlite.addHistory("staff", "Scanner", 1, "2019-04-03 14:30:02.000");

        // Add sample logs
        sqlite.addLogs("NOTICE", "admin", "User creation successful", new Timestamp(new Date().getTime()).toString());
        sqlite.addLogs("NOTICE", "manager", "User creation successful", new Timestamp(new Date().getTime()).toString());
        sqlite.addLogs("NOTICE", "admin", "User creation successful", new Timestamp(new Date().getTime()).toString());

        // Add sample product
        sqlite.addProduct("Antivirus", 5, 500.0);
        sqlite.addProduct("Firewall", 3, 1000.0);
        sqlite.addProduct("Scanner", 10, 100.0);

        // Add sample users (hashed once function is executed)
        sqlite.addUser("admin", "qwerty1234" , 5);
        sqlite.addUser("manager", "qwerty1234", 4);
        sqlite.addUser("staff", "qwerty1234", 3);
        sqlite.addUser("client1", "qwerty1234", 2);
        sqlite.addUser("client2", "qwerty1234", 2);


        // Get users
        ArrayList<History> histories = sqlite.getHistory();
        for(int nCtr = 0; nCtr < histories.size(); nCtr++){
            System.out.println("===== History " + histories.get(nCtr).getId() + " =====");
            System.out.println(" Username: " + histories.get(nCtr).getUsername());
            System.out.println(" Name: " + histories.get(nCtr).getName());
            System.out.println(" Stock: " + histories.get(nCtr).getStock());
            System.out.println(" Timestamp: " + histories.get(nCtr).getTimestamp());
        }

        // Get users
        ArrayList<Logs> logs = sqlite.getLogs();
        for(int nCtr = 0; nCtr < logs.size(); nCtr++){
            System.out.println("===== Logs " + logs.get(nCtr).getId() + " =====");
            System.out.println(" Username: " + logs.get(nCtr).getEvent());
            System.out.println(" Password: " + logs.get(nCtr).getUsername());
            System.out.println(" Role: " + logs.get(nCtr).getDesc());
            System.out.println(" Timestamp: " + logs.get(nCtr).getTimestamp());
        }

        // Get users
        ArrayList<Product> products = sqlite.getProduct();
        for(int nCtr = 0; nCtr < products.size(); nCtr++){
            System.out.println("===== Product " + products.get(nCtr).getId() + " =====");
            System.out.println(" Name: " + products.get(nCtr).getName());
            System.out.println(" Stock: " + products.get(nCtr).getStock());
            System.out.println(" Price: " + products.get(nCtr).getPrice());
        }
        // Get users
        ArrayList<User> users = sqlite.getUsers();
        for(int nCtr = 0; nCtr < users.size(); nCtr++){
            System.out.println("===== User " + users.get(nCtr).getId() + " =====");
            System.out.println(" Username: " + users.get(nCtr).getUsername());
            System.out.println(" Password: " + users.get(nCtr).getPassword());
            System.out.println(" Role: " + users.get(nCtr).getRole());
            System.out.println(" Locked: " + users.get(nCtr).getLocked());
        }
    }
    
}
