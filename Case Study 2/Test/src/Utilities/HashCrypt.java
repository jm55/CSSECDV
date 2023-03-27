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
    
    public String getGenericEncryption(final String plaintext){
        return encrypt(plaintext, "a0b1c2d3e4f56789", 16, generateIv("a0b1c2d3e4f56789".substring(0,16)));
    }
    
    public String getGenericDecryption(final String ciphertext){
        return decrypt(ciphertext, "a0b1c2d3e4f56789", 16, generateIv("a0b1c2d3e4f56789".substring(0,16)));
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
    
    /**
     * Get session data
     * @param id UserID of the user.
     * @param username Username of the user.
     * @param role Role of the user
     * @return (Unencrypted) session string.
     */
    public String getSessionString(final int userID, final String username, final int role){
        return buildSessionString(userID, username, role);
    }
    
    /**
     * Builds the actual session string
     * @param id UserID of the user.
     * @param username Username of the user.
     * @param role Role of the user
     * @return (Unencrypted) session string
     */
    private String buildSessionString(final int userID, final String username, final int role){
        byte[] randomizer = new byte[4];
        new SecureRandom().nextBytes(randomizer);
        
        String datetime = new SimpleDateFormat("HH:mm:ss::yyyy/MM/dd").format(new Date());
        datetime = getSHA256(datetime);
        //randomizer,date,id,uname,role,randomizer
        return new String(randomizer,StandardCharsets.UTF_8) + "," + datetime + "," + (userID+"") + "," + username + "," + (role+"") + "," + new String(randomizer,StandardCharsets.UTF_8);
    }
    
    /**
     * Converts byte[] to hex String
     * @param hash Hash to turn into hex string
     * @return Hex String equivalent of the hash.
     */
    private final String toHexString(byte[] hash){
        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(new BigInteger(1, hash).toString(16));
 
        // Pad with leading zeros
        while(hexString.length() < 64)
            hexString.insert(0, '0');
 
        return hexString.toString();
    }
    
    /**
     * Generates the IV given the ivValue string.
     * @param ivValue ivValue.
     * @return IvParameterSpec of the ivValue.
     */
    private IvParameterSpec generateIv(String ivValue) {
        return new IvParameterSpec(ivValue.getBytes());
    }
    
    /**
     * Get the encrypted value of the plaintext input.
     * @param plaintext Plaintext to be encrypted
     * @param secretKey Secretkey used to lock the input text
     * @param keyLength Resulting keylength (limited to 32)
     * @param iv IvParameterSpec to use for encryption
     * @return Encrypted string of the given plaintext.
     */
    private String encrypt(final String plaintext, final String secretKey, final int keyLength, IvParameterSpec iv){
        int newKeyLength = keyLength;
        if(newKeyLength > 32)
            newKeyLength = 32;
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, AES(newKeyLength, secretKey), iv);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
            new Logger().log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
        }
        return null;
    }
    
    /**
     * Get the decrypted value of the ciphertext input.
     * @param ciphertext Ciphertext to be Decrypted
     * @param secretKey Secretkey used to lock the input text
     * @param keyLength Resulting keylength (limited to 32)
     * @param iv IvParameterSpec to use for encryption
     * @return Decrypted string of the given ciphertext.
     */
    private String decrypt(final String ciphertext, final String secretKey, final int keyLength, IvParameterSpec iv){
        int newKeyLength = keyLength;
        if(newKeyLength > 32)
            newKeyLength = 32;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, AES(newKeyLength, secretKey), iv);
            byte[] plaintext = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(plaintext);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
            new Logger().log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
        }
        return null;
    }
    
    /**
     * Get the AES configuration
     * @param keyLength Resulting keylength (limited to 32)
     * @param privateKey Secret key to be used as the key for encryption/decryption
     * @return SecretKeySpec of specified AES configuration
     */
    private final SecretKeySpec AES(final int keyLength, final String privateKey){
        MessageDigest sha;
        byte[] key;
        int newKeyLength = keyLength;
        if(newKeyLength > 32)
            newKeyLength = 32;
        try {
            key = privateKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-384");
            key = sha.digest(key);
            key = Arrays.copyOf(key, keyLength);
            return new SecretKeySpec(key, "AES");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            new Logger().log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
        }
        return null;
    }
}
