package Utilities;

import View.MgmtUser;
import javax.swing.JOptionPane;

/**
 *
 * @author ejose
 */
public class Dialogs {

    /**
     * Create error dialog
     * @param message Message to show
     * @param title Title of dialog
     */
    public void errorDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Create warning dialog
     * @param message Message to show
     * @param title Title of dialog
     */
    public void warningDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Create information dialog
     * @param message Message to show
     * @param title Title of dialog
     */
    public void informationDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Create notification dialog
     * @param message Message to show
     * @param title Title of dialog
     * @param success State whether successful(true) or not(false).
     */
    public void notifyDialog(String message, String title, boolean success) {
        String state = "Successful!";
        if (!success) {
            state = "Failed";
        }
        JOptionPane.showMessageDialog(null, message + " " + state, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Ask yes/no question to user.
     * @param message Message to show
     * @param title Title of dialog
     * @return True if yes, false if otherwise.
     */
    public boolean yesno(String message, String title){
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
