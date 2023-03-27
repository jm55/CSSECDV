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
    public final int[] roleLimit = {0,5};
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
    
    /**
     * Check if given value is a timestamp.
     * @param timestamp String version of timestamp.
     * @return True if timestamp, false if not.
     */
    public boolean isTimestamp(final String timestamp){
        return Pattern.compile(timestampRegex).matcher(timestamp).matches();
    }
    
    /**
     * Check if given value only contains basic characters:
     * A-Za-z0-9~`!@#$%^&*()_-+={[}]|:\<,>.?/
     * @param input String to be checked
     * @return True if it matches regex, false if otherwise.
     */
    public boolean isBasicChar(final String input){
        return Pattern.compile(basicRegex).matcher(input).matches();
    }
    
    /**
     * Shows a dialog box asking for admin's password.
     * Compares it to current session given the main value.
     * @param title Title of the window
     * @param m Main object containing session value
     * @param sqlite SQLite instance.
     * @return True if confirmed as admin, false if otherwise.
     */
    public boolean confirmAdmin(final String title, final Main m, final SQLite sqlite){
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
    
    /**
     * Checks if the given credentials are within limit (length).
     * @param username Username of user
     * @param password Password of user (plaintext)
     * @return True if within limit, false if otherwise.
     */
    public boolean credentialWithinLimit(final String username, final String password){
        if((username.length() <= this.maxLength && password.length() <= this.maxLength) && password.length() >= minLength){
            return true;
        }else
            return false;
    }
    
    /**
     * Checks only password if within limit (length).
     * @param password Password of user (plaintext).
     * @return True if within limit, false if otherwise.
     */
    public boolean passwordWithinLimit(final String password){
        return password.length() <= this.maxLength && password.length() >= this.minLength;
    }
    
    /**
     * Checks if the given inputs are within limit (length).
     * @param username Username of user
     * @param password Password of user (plaintext)
     * @param confPassword Confirmation password of user (plaintext)
     * @return True if within limit, false if !within limit or password!=confPassword
     */
    public boolean credentialWithinLimit(final String username, final String password, final String confPassword){
        if(!passwordMatches(password, confPassword))
            return false;
        if((username.length() <= this.maxLength && password.length() <= this.maxLength && confPassword.length() <= this.maxLength) && (password.length() >= minLength && confPassword.length() >= minLength)){
            return true;
        }else
            return false;
    }
    
    /**
     * Checks if both passwords match or not such that A==B.
     * @param A Password A (plaintext).
     * @param B Password B (plaintext).
     * @return True if A==B, false if otherwise.
     */
    public boolean passwordMatches(final String A, final String B){
        return A.equals(B);
    }
    
    /**
     * Checks if a given password meets minimum password requirements.
     * @param password User password (plaintext).
     * @return True if valid, false if otherwise
     */
    public boolean isValidPasswordString(final String password){
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
    
    /**
     * Checks if a username string is valid or not
     * @param username Username of user.
     * @return True if valid, false if otherwise.
     */
    public boolean isValidUsernameString(final String username){
        if(username == "SYSTEM")
                return true;
        return Pattern.compile(usernameRegex).matcher(username).matches() && (username.length() <= maxLength);
    }
    
    /**
     * Checks whether the registration inputs are valid or not.
     * @param username Username of new user
     * @param password Password of new user
     * @param confPassword Confirm password of new user
     * @return True if valid, false if otherwise.
     */
    public boolean isRegisterValid(final String username, final String password, final String confPassword){
        if(!credentialWithinLimit(username, password, confPassword))
            return false;
        boolean usernameValid = Pattern.compile(usernameRegex).matcher(username).matches();
        boolean passwordValid = Pattern.compile(passwordRegex).matcher(password).matches();
        boolean confPasswordValid = Pattern.compile(passwordRegex).matcher(confPassword).matches();
        if (usernameValid && passwordValid && confPasswordValid)
            return true;
        return false;
    }
    
    /**
     * Checks if login credentials are valid or not.
     * @param username Username of user.
     * @param password Password of user (plaintext)
     * @return True if valid, false if otherwise.
     */
    public boolean isLoginValid(final String username, final String password){
        return isValidUsernameString(username) && isValidPasswordString(password);
    }
    
    /**
     * Checks session if value of role matches contents of allowable roles.
     * @param allowableRole List of allowable roles.
     * @param role Role given.
     * @return True if valid role, false if otherwise or not within valid roles.
     */
    private boolean checkSession(final int[] allowableRole, final int role){
        if(role > this.roleLimit[1] || role < roleLimit[0])
            return false;
        for(int a: allowableRole)
            if(role == a)
                return true;
        return false;
    }
    
    /**
     * Checks session and warns user if invalid role has been detected.
     * @param allowableRole List of allowable roles.
     * @param role Role given.
     */
    public void validateSession(final int[] allowableRole, final int role){
        if(!checkSession(allowableRole, role)){
            JOptionPane.showMessageDialog(null, "Invalid User Account Role Detected!\nProgram terminating for DB safety.", "Error Occured", JOptionPane.ERROR);
            System.exit(1);
        }else
            return;
    }
    
    /**
     * Checks session and warns user if invalid role has been detected.
     * @param allowableRole Value of allowable role.
     * @param role Role given.
     */
    public void validateSession(final int allowableRole, final int role){
        if(allowableRole != role){
            JOptionPane.showMessageDialog(null, "Invalid User Account Role Detected!\nProgram terminating for DB safety.", "Error Occured", JOptionPane.ERROR);
            System.exit(1);
        }else
            return;
    }
}
