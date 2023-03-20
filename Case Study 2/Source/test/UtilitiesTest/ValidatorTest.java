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
        
        String username = "useraccount";
        String password = "Password@123";
        boolean valid = validate.isRegisterValid(username, password, password);
        boolean withinLimit = validate.credentialWithinLimit(username, password, password);
        
        System.out.println(username + ":" + password);
        System.out.println("Valid: " + valid);
        System.out.println("Within Limit: " + withinLimit);
    }
}
