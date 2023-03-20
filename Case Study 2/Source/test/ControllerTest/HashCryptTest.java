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
        System.out.println("<<HASH TEST>>");
        
        String input = "abcd";
        
        String sha256 = hs.getSHA256(input);
        String sha384 = hs.getSHA384(input);
        
        System.out.println("SHA256: " + sha256);
        System.out.println("SHA384: " + sha384);
        System.out.println("");
        //<<ENCRYPT/DECRYPT PASSWORD TEST>>
        System.out.println("<<ENCRYPT/DECRYPT PASSWORD TEST>>");
        
        String username = "username";
        String plaintext = "password";
        
        String plaintextSHA384 = hs.getPasswordHash(username,plaintext);
        String ciphertext = hs.getEncryptedPass(username, plaintext);
        String fromCiphertext = hs.getDecryptedPass(ciphertext);
        
        System.out.println("SHA384 Plaintext & Ciphertext match: " + plaintextSHA384.equals(fromCiphertext));
        System.out.println("");
        //<<ENCRYPT/DECRYPT SESSION TEST
        System.out.println("<<ENCRYPT/DECRYPT SESSION TEST");
        int[] id = {99,1,32,11};
        String[] usernames = {"user", "staff", "manager", "admin"};
        int[] role = {2,3,4,5};
        
        for(int i = 0; i < id.length; i++){
            String sessionID = hs.getSessionString(id[i], usernames[i], role[i]);
            String encryptedSessionID = hs.getEncryptedSession(sessionID);
            String decryptedSessionID = hs.getDecryptedSession(encryptedSessionID);
            System.out.println("Session: " + id[i] + ", " + usernames[i] + ", " +  role[i] + " = " + encryptedSessionID);
            System.out.println("Session ID Encrypt/Decrypt Match: " + sessionID.equals(decryptedSessionID));
            System.out.println("");
        }
        System.out.println("");
    }
}
