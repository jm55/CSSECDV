package Utilities;

import Controller.Main;
import Controller.SQLite;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ejose
 */
public class Validator {
    //Cred parameters
    public final int minLength = 8;
    public final int maxLength = 64;
    public final String usernameRegex = "^[a-z0-9_\\-.]+$";
    public final String passwordRegex = "^[A-Za-z0-9~`!@#$%^&*()_\\-+=\\{\\[\\}\\]|:\\<,>.?/]+$";
    public final String basicRegex = "^[A-Za-z0-9~`!@#$%^&*()_\\-+=\\{\\[\\}\\]|:\\<,>.?/ ]+$";
    public final String timestampRegex = "^[0-9-.: ]+$";
    
    private final Dialogs dialog = new Dialogs();
    
    private void designer(JTextField component, String text){
        component.setSize(70, 600);
        component.setFont(new java.awt.Font("Tahoma", 0, 18));
        component.setBackground(new java.awt.Color(240, 240, 240));
        component.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        component.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), text, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));
    }
    
    public boolean isTimestamp(String timestamp){
        return Pattern.compile(timestampRegex).matcher(timestamp).matches();
    }
    
    public boolean isBasicChar(String input){
        return Pattern.compile(basicRegex).matcher(input).matches();
    }
    
    public boolean confirmAdmin(String title, Main m, SQLite sqlite){
        JPasswordField adminPass = new JPasswordField();
        
        designer(adminPass, "ADMIN PASSWORD");
        
        Object[] message = {
            "Confirm Admin Password: ", adminPass
        };

        int result = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        if(result == JOptionPane.OK_OPTION){
            if(sqlite.authenticateUser(m.getSessionUserName(), new String(adminPass.getPassword()))){
                return true;
            }else{
                dialog.errorDialog("Incorrect Admin password,\nplease try again.", "User Management");
                return false;
            }
        }else{
            return false;
        }
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
        if(username == "SYSTEM")
                return true;
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
    
    private boolean checkSession(int[] allowableRole, int role){
        for(int a: allowableRole)
            if(role == a)
                return true;
        return false;
    }
    
    public void validateSession(int[] allowableRole, int role){
        if(!checkSession(allowableRole, role)){
            JOptionPane.showMessageDialog(null, "Invalid User Account Role Detected!\nProgram terminating for DB safety.", "Error Occured", JOptionPane.ERROR);
            System.exit(1);
        }else
            return;
    }
    
    public void validateSession(JFrame frame, int allowableRole, int role){
        int[] allowables = {allowableRole};
        validateSession(allowables, role);
    }
}
