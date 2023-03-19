package View;

import Utilities.Validator;
import Controller.SQLite;
import Model.Logs;

import javax.swing.JOptionPane;
import java.util.regex.Pattern;

public class Login extends javax.swing.JPanel {

    public Frame frame;
    private SQLite sql;
    
    private Validator validate;
    private int loginAttempt = 0;
    private final int loginAttemptLimit = 8;
    
    public Login() {
        validate = new Validator();
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        usernameFld = new javax.swing.JTextField();
        passwordFld = new javax.swing.JPasswordField();
        registerBtn = new javax.swing.JButton();
        loginBtn = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        usernameFld.setBackground(new java.awt.Color(240, 240, 240));
        usernameFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernameFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "USERNAME", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        usernameFld.setDragEnabled(true);

        passwordFld.setBackground(new java.awt.Color(240, 240, 240));
        passwordFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passwordFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        passwordFld.setDragEnabled(true);

        registerBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        registerBtn.setText("REGISTER");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        loginBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        loginBtn.setText("LOGIN");
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(passwordFld)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(registerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(usernameFld)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(usernameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(108, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        //Check if both fields are empty or invalid characters
        if (fieldIsBlank() || !validate.isLoginValid(getUsername(), getPassword())){ //Invalid Entries
            invalidLogin();
        }else{
            sql = new SQLite();
            sql.addLogs(loginLog(getUsername(), "Logging in..."));
            if (sql.isUserExists(getUsername())){ //Verify user existence
                if(sql.isUserLocked(getUsername())){
                    sql.addLogs(loginLog(getUsername(), "Locked account attempted to login"));
                    invalidLogin();
                }else{ //User is not locked
                    if(sql.authenticateUser(getUsername(), getPassword())){ //Valid username and password.
                        loginAttempt = 0;
                        int id = sql.getUserID(getUsername());
                        clearInputs();
                        //frame.main.createSession(id);
                        frame.mainNav(id);
                    }else{ //Invalid password.
                        sql.addLogs(loginLog(getUsername(), "Failed attempt to login"));
                        invalidLogin();
                        loginAttempt++;
                        if(loginAttempt >= loginAttemptLimit)
                            if(sql.lockUser(getUsername()))
                                sql.addLogs(loginLog(getUsername(), "Account locked due to excessive login attempts"));
                    }
                }
            }else{ 
                //No user found!(assumes it meets the field validation conditions)
                invalidLogin();
                sql.addLogs(loginLog(usernameFld.getText(), "Unknown account attempted to login"));
            }
            sql = null;
        }
    }//GEN-LAST:event_loginBtnActionPerformed
    
    private void clearInputs(){
        usernameFld.setText("");
        passwordFld.setText("");
    }
    
    private String getUsername(){
        return usernameFld.getText();
    }
    
    private String getPassword(){
        return new String(passwordFld.getPassword());
    }
    
    /**
     * Checks if login input fields are empty.
     * @return True if either are blank, false if otherwise.
     */
    private boolean fieldIsBlank(){
        if (usernameFld.getText().isBlank() || getPassword().isBlank())
            return true;
        return false;
    }
    
    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        frame.registerNav();
    }//GEN-LAST:event_registerBtnActionPerformed
    
    /**
     * Ambiguous message if username/password don't match on DB.
     */
    private void invalidLogin(){
        JOptionPane.showMessageDialog(frame, "Invalid username or password, please try again.", "Invalid Login", JOptionPane.WARNING_MESSAGE);
        passwordFld.setText("");
    }
    
    /**
     * Create a Logs object for logging on Login related events.
     * @param username Username of the user attempting to login 
     * @param desc Description of the login event.
     * @return Logs object from the given parameters
     */
    private Logs loginLog(String username, String desc){
        return new Logs("LOGIN", username, desc);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loginBtn;
    private javax.swing.JPasswordField passwordFld;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField usernameFld;
    // End of variables declaration//GEN-END:variables
}
