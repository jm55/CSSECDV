package Utilities;

import Controller.SQLite;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ejose
 */
public class HashCrypt {
    private final int passwordKeyLength = 32;
    private final int sessionKeyLength = 24;
    private final String sessionKey = "CS5eCdV_sI1_5eSsi0n";
    private final String passwordKey = "C5sEcDv_s1I_p@5sW0rD";
    
    private final IvParameterSpec acctIV = generateIv(passwordKey.substring(0,16));
    private final IvParameterSpec sessionIV = generateIv(passwordKey.substring(0,16));
    
    private final Logger logger = new Logger(new SQLite());
    
    /**
     * Get SHA256 of a given String
     * @param input String to be hashed
     * @return SHA256 of the input
     */
    public String getSHA256(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return toHexString(md.digest(input.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            
        }
        return null;
    }
    
    /**
     * Get SHA384 of a given String
     * @param input String to be hashed
     * @return SHA384 of the input
     */
    public String getSHA384(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            return toHexString(md.digest(input.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            
        }
        return null;
    }
    
    /**
     * Get the Password Hash of a given credential
     * @param username Username credential
     * @param plaintext Password credential (in plaintext)
     * @return Hashed credentials
     */
    public String getPasswordHash(final String username, final String plaintext){
        return getSHA384("$/" + username+ "::" + plaintext);
    }
    
    /**
     * FOR USE IN PASSWORDS
     * Get the encrypted has from the credentials of a user
     * @param username Username credential
     * @param plaintext Password credential (in plaintext)
     * @return Encrypted string of the given credentials
     */
    public String getEncryptedPass(final String username, final String plaintext){
        return encrypt(getPasswordHash(username, plaintext), passwordKey, passwordKeyLength, acctIV);
    }
    
    /**
     * FOR USE IN PASSWORDS
     * Get the decrypted hash given a ciphertext.
     * @param ciphertext Ciphertext to be decrypted
     * @return Decrypted hash from ciphertext
     */
    public String getDecryptedPass(final String ciphertext){
        return decrypt(ciphertext, passwordKey, passwordKeyLength, acctIV);
    }
    
    /**
     * FOR USE IN SESSIONS
     * Get the encrypted session data.
     * @param plainSession Plaintext session data
     * @return Encrypted session data
     */
    public String getEncryptedSession(final String plainSession){
        return encrypt(plainSession, sessionKey, sessionKeyLength, sessionIV);
    }
    
    /**
     * FOR USE IN SESSIONS
     * Get the decrypted session data
     * @param cipherSession Encrypted session data
     * @return Decrypted session data
     */
    public String getDecryptedSession(final String cipherSession){
        return decrypt(cipherSession, sessionKey, sessionKeyLength, sessionIV);
    }
    
    public String getSessionString(final int id, final String username, final int role){
        return buildSessionString(id, username, role);
    }
    
    private String buildSessionString(final int id, final String username, final int role){
        byte[] randomizer = new byte[4];
        new SecureRandom().nextBytes(randomizer);
        
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss::yyyy/MM/dd");
        String datetime = new SimpleDateFormat("HH:mm:ss::yyyy/MM/dd").format(new Date());
        datetime = getSHA256(datetime);
        //randomizer,date,id,uname,role,randomizer
        return new String(randomizer,StandardCharsets.UTF_8) + "," + datetime + "," + (id+"") + "," + username + "," + (role+"") + "," + new String(randomizer,StandardCharsets.UTF_8);
    }
    
    /**
     * 
     * @param hash
     * @return 
     */
    private final String toHexString(byte[] hash){
        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(new BigInteger(1, hash).toString(16));
 
        // Pad with leading zeros
        while(hexString.length() < 64)
            hexString.insert(0, '0');
 
        return hexString.toString();
    }
    
    private IvParameterSpec generateIv(String ivValue) {
        return new IvParameterSpec(ivValue.getBytes());
    }
    
    /**
     * Gets the hash of a given plain-text password.
     * @param plaintext
     * @return 
     */
    private String encrypt(final String plaintext, final String secretKey, final int keyLength, IvParameterSpec iv){
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, AES(keyLength, secretKey), iv);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
        }
        return null;
    }
    
    private String decrypt(final String ciphertext, final String secretKey, final int keyLength, IvParameterSpec iv){
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, AES(keyLength, secretKey), iv);
            byte[] plaintext = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(plaintext);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
        }
        return null;
    }
    
    /**
     * Gets AES configuration for hashing function.
     * @return SecretKeySpec object that contains configured AES.
     */
    private final SecretKeySpec AES(final int keyLength, final String privateKey){
        MessageDigest sha;
        byte[] key;
        try {
            key = privateKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-384");
            key = sha.digest(key);
            key = Arrays.copyOf(key, keyLength);
            return new SecretKeySpec(key, "AES");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
        }
        return null;
    }
}
