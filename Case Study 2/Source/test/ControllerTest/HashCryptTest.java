/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControllerTest;

import Utilities.HashCrypt;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 *
 * @author ejose
 */
public class HashCryptTest {
    static HashCrypt hs;
    public static void main(String[] args){
        hs = new HashCrypt();
        
        //<<HASH TEST>>
        
        String input = "abcd";
        
        String sha256 = hs.getSHA256(input);
        String sha384 = hs.getSHA384(input);
        
        System.out.println("SHA256: " + sha256);
        System.out.println("SHA384: " + sha384);
        
        //<<ENCRYPT/DECRYPT PASSWORD TEST>>
        String plaintext = "abcd1234";
        
        String plaintextSHA384 = hs.getSHA384(plaintext);
        String ciphertext = hs.getEncryptedPass("",plaintext);
        String fromCiphertext = hs.getDecryptedPass(ciphertext);
        
        System.out.println("SHA384 Plaintext: " + plaintextSHA384);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("From Ciphertext: " + fromCiphertext);
        
        //<<ENCRYPT/DECRYPT SESSION TEST>>
        System.out.println("\n");
        int[] id = {99,1,32,11};
        String[] username = {"user", "staff", "manager", "admin"};
        int[] role = {2,3,4,5};
        
        for(int i = 0; i < id.length; i++){
            String sessionID = buildSessionString(id[i], username[i], role[i]);
            String encryptedSessionID = hs.getEncryptedSession(sessionID);
            String decryptedSessionID = hs.getDecryptedSession(encryptedSessionID);
            System.out.println("Raw Session ID: " + sessionID);
            System.out.println("Encrypted Session ID: " + encryptedSessionID);
            System.out.println("Decrypted Session ID: " + decryptedSessionID);
            System.out.println("");
        }
        
    }
    
    private static String buildSessionString(final int id, final String username, final int role){
        byte[] randomizer = new byte[16];
        new SecureRandom().nextBytes(randomizer);
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");
        String datetime = new SimpleDateFormat("HH:mm:ss::yyyy/MM/dd").format(new Date());
        datetime = hs.getSHA256(datetime);
        return hs.getSHA256(datetime) + "," + (id+"")+ "," + username + "," + (role+"") + "," + new String(randomizer,StandardCharsets.UTF_16);
    }
}
