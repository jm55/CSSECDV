package Utilities;

import View.MgmtUser;
import javax.swing.JOptionPane;

/**
 *
 * @author ejose
 */
public class Dialogs {

    public void errorDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public void warningDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    public void informationDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void notifyDialog(String message, String title, boolean success) {
        String state = "Successful!";
        if (!success) {
            state = "Failed";
        }
        JOptionPane.showMessageDialog(null, message + " " + state, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
}
