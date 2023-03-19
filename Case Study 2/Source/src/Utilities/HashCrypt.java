/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public String getSHA256(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return toHexString(md.digest(input.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HashCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String getSHA384(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            return toHexString(md.digest(input.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HashCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String passwordHash(final String username, final String plaintext){
        return getSHA384("$%" + username+ "::" + plaintext);
    }
    
    public String getEncryptedPass(final String username, final String plaintext){
        return encrypt(passwordHash(username, plaintext), "C5SecDV_s11_p@5sW0rD",32);
    }
    
    public String getDecryptedPass(final String ciphertext){
        return decrypt(ciphertext, "C5SecDV_s11_p@5sW0rD", 32);
    }
    
    public String getEncryptedSession(String plainSession){
        return encrypt(plainSession, "C5SecDV_s11_5eSsi0n", 16);
    }
    
    public String getDecryptedSession(String cipherSession){
        return decrypt(cipherSession, "C5SecDV_s11_5eSsi0n", 16);
    }
    
    public String getSessionString(final int id, final String username, final int role){
        return buildSessionString(id, username, role);
    }
    
    private String buildSessionString(final int id, final String username, final int role){
        byte[] randomizer = new byte[16];
        new SecureRandom().nextBytes(randomizer);
        
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss::yyyy/MM/dd");
        String datetime = new SimpleDateFormat("HH:mm:ss::yyyy/MM/dd").format(new Date());
        datetime = getSHA256(datetime);
        
        //Session Pattern: sha256(datetime),id,username,role,randomizer
        return getSHA256(datetime) + "," + (id+"")+ "," + username + "," + (role+"") + "," + new String(randomizer,StandardCharsets.UTF_16);
    }
    
    /**
     * Reference: https://www.geeksforgeeks.org/sha-256-hash-in-java/
     * @param hash
     * @return 
     */
    private final String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);
 
        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));
 
        // Pad with leading zeros
        while (hexString.length() < 64)
            hexString.insert(0, '0');
 
        return hexString.toString();
    }
    
    private IvParameterSpec generateIv() {
        byte[] iv = "C5SecDV_s11_2023".getBytes();
        return new IvParameterSpec(iv);
    } 
    
    /**
     * Gets the hash of a given plain-text password.
     * @param plaintext
     * @return 
     */
    private String encrypt(final String plaintext, final String secretKey, final int keyLength){
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, AES(keyLength, secretKey), generateIv());
            return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
            System.out.println("AES Error: " + ex.getLocalizedMessage());
        }
        return null;
    }
    
    private String decrypt(final String ciphertext, final String secretKey, final int keyLength){
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, AES(keyLength, secretKey), generateIv());
            byte[] plaintext = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(plaintext);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
            System.out.println("AES Error: " + ex.getLocalizedMessage());
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
            //System.out.println("AES Error: " + ex.getLocalizedMessage());
        }
        return null;
    }
}
