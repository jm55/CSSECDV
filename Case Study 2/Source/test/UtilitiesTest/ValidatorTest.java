/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UtilitiesTest;

import Utilities.Validator;

/**
 *
 * @author ejose
 */
public class ValidatorTest {
    public static void main(String[] args){
        Validator validate = new Validator();
        
        String validTimestamp = "1234-12-11 12:12:12.1234";
        String invalidTimestamp = "1234-12-11 ab:cd:ef.GHIJ";
        String invalidString = "abcdABCD123$";
        String basicString = "abcdABCD123-";
        System.out.println("basicString = " + validate.isBasicChar(basicString));
        
        System.out.println("Valid: " + !(validate.isBasicChar(basicString)&&validate.isBasicChar(basicString)&&validate.isTimestamp(validTimestamp)));
        System.out.println("Invalid: " + !(validate.isBasicChar(basicString)&&validate.isBasicChar(invalidString)&&validate.isTimestamp(validTimestamp)));
        System.out.println("Invalid: " + !(validate.isBasicChar(basicString)&&validate.isBasicChar(basicString)&&validate.isTimestamp(invalidTimestamp)));
        
        String username = "useraccount";
        String password = "Password@123";
        boolean valid = validate.isRegisterValid(username, password, password);
        boolean withinLimit = validate.credentialWithinLimit(username, password, password);
        
        
        System.out.println(username + ":" + password);
        System.out.println("Valid: " + valid);
        System.out.println("Within Limit: " + withinLimit);
    }
}
