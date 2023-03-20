/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import java.util.regex.Pattern;

/**
 *
 * @author ejose
 */
public class Validator {
    //Cred parameters
    public final int minLength = 8;
    public final int maxLength = 64;
    public final String usernameRegex = "[a-z0-9_\\-.]+";
    public final String passwordRegex = "[A-Za-z0-9~`!@#$%^&*()_\\-+=\\{\\[\\}\\]|:\\<,>.?/]+";
    
    public boolean isSamePassword(){
        return true;
    }
    
    public boolean credentialWithinLimit(String username, String password){
        if((username.length() <= this.maxLength && password.length() <= this.maxLength) && password.length() >= minLength){
            return true;
        }else
            return false;
    }
    
    public boolean passwordWithinLimit(String password){
        return password.length() <= this.maxLength && password.length() >= this.minLength;
    }
    
    public boolean credentialWithinLimit(String username, String password, String confPassword){
        if((username.length() <= this.maxLength && password.length() <= this.maxLength && confPassword.length() <= this.maxLength) && (password.length() >= minLength && confPassword.length() >= minLength)){
            return true;
        }else
            return false;
    }
    
    public boolean passwordMatches(String password1, String password2){
        return password1.equals(password2);
    }
    
    /**
     * Checks if a given password meets minimum password requirements.
     * @param password Given password
     * @return True if valid, false if otherwise
     */
    public boolean isValidPasswordString(String password){
        final String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String lowercase = uppercase.toLowerCase();
        final String digits = "0123456789";
        final String special = "~`!@#$%^&*()_-+={[}]|:\\<,>.?/";
        
        boolean uppercaseRegex = false;
        boolean lowercaseRegex = false;
        boolean digitRegex = false;
        boolean specialRegex = false;
        
        for(int u = 0; u < uppercase.length(); u++){
            if(password.contains(uppercase.charAt(u) + ""))
                uppercaseRegex = true;
            if(password.contains(lowercase.charAt(u) + ""))
                lowercaseRegex = true;
        }
        for(int d = 0; d < digits.length(); d++)
            if(password.contains(digits.charAt(d) + ""))
                digitRegex = true;
        for(int s = 0; s < special.length(); s++)
            if(password.contains(special.charAt(s) + ""))
                specialRegex = true;
            
        return (uppercaseRegex && lowercaseRegex && digitRegex && specialRegex) && (password.length() >= minLength && password.length() <= maxLength);
    }
    
    public boolean isValidUsernameString(String username){
        return Pattern.compile(usernameRegex).matcher(username).matches() && (username.length() <= maxLength);
    }
    
    /**
     * Checks if fields are valid such that
     * it meets the minimum requirements of 
     * acceptable characters.
     * @return 
     */
    public boolean isRegisterValid(String username, String password, String confPassword){
        boolean usernameValid = Pattern.compile(usernameRegex).matcher(username).matches();
        boolean passwordValid = Pattern.compile(passwordRegex).matcher(password).matches();
        boolean confPasswordValid = Pattern.compile(passwordRegex).matcher(confPassword).matches();
        if (usernameValid && passwordValid && confPasswordValid)
            return true;
        return false;
    }
    
    public boolean isLoginValid(String username, String password){
        return isValidUsernameString(username) && isValidPasswordString(password);
    }
}
