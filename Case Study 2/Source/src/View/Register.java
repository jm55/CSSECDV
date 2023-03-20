package View;

import Utilities.Validator;
import Controller.SQLite;
import Model.Logs;
import javax.swing.JOptionPane;

public class Register extends javax.swing.JPanel {

    public Frame frame;

    private Validator validate;
    
    public Register() {
        this.validate = new Validator();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        registerBtn = new javax.swing.JButton();
        usernameFld = new javax.swing.JTextField();
        confpassFld = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        backBtn = new javax.swing.JButton();
        passwordFld = new javax.swing.JPasswordField();

        registerBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        registerBtn.setText("REGISTER");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        usernameFld.setBackground(new java.awt.Color(240, 240, 240));
        usernameFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernameFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "USERNAME", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        confpassFld.setBackground(new java.awt.Color(240, 240, 240));
        confpassFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        confpassFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        confpassFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "CONFIRM PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        confpassFld.setDragEnabled(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        backBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        backBtn.setText("<Back");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        passwordFld.setBackground(new java.awt.Color(240, 240, 240));
        passwordFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passwordFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        passwordFld.setDragEnabled(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(212, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(usernameFld)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                    .addComponent(confpassFld, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passwordFld, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(213, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backBtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backBtn)
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(usernameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(passwordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confpassFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(114, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void registerAction(SQLite sql){
        sql.addUser(getUsername(), getPassword(), 2);
    }
    
    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        if (isFieldBlank()){
            emptyFields();
        }else if(!validate.isRegisterValid(getUsername(), getPassword(), getConfPassword())){
            invalidCharacters();
        }else if(!getPassword().equals(getConfPassword())){
            passwordMismatch();
        }else if(!validate.isValidPasswordString(getPassword())){
            invalidPassword();
        }else if(!validate.credentialWithinLimit(getUsername(), getPassword(), getConfPassword())){
            bigInputs();
        }else{ //All prior conditions are met
            SQLite sql = new SQLite();
            if (sql.isUserExists(getUsername())){
                userExists();
                sql.addLogs(registerLog("New user attempted to register using unavailable username: " + getUsername()));
            }else{
                registerAction(sql);
                sql.addLogs(registerLog("New user registered as " + getUsername()));
                clearInputs();
                frame.loginNav();
            }
            sql = null;
        }
    }//GEN-LAST:event_registerBtnActionPerformed
    
    private void clearInputs(){
        usernameFld.setText("");
        passwordFld.setText("");
        confpassFld.setText("");
    }
    
    private String getUsername(){
        return usernameFld.getText();
    }
    
    private String getConfPassword(){
        return new String(confpassFld.getPassword());
    }
    
    /**
     * Gets the String equivalent of entry in passwordField
     * @return 
     */
    private String getPassword(){
        return new String(passwordFld.getPassword());
    }
    
    /**
     * Create a Logs object for logging on Register related events.
     * @param desc Description of the registration event.
     * @return Logs object from the given parameters
     */
    private Logs registerLog(String desc){
        return new Logs("REGISTER", "NEW USER", desc);
    }
    
    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        clearFields();
        frame.loginNav();
    }//GEN-LAST:event_backBtnActionPerformed
    
    /***
     * Shows a popup that user exists.
     */
    private void userExists(){
        JOptionPane.showMessageDialog(frame, "Username already in use, please use another username.", "Invalid Registration", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Shows a popup that passwords entered don't match
     */
    private void passwordMismatch(){
        JOptionPane.showMessageDialog(frame, "Passwords don't match, try again.", "Invalid Registration", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Shows a popup that the password entered is invalid (i.e., does not minimum requirements or contains invalid characters)
     */
    private void invalidPassword(){
        JOptionPane.showMessageDialog(frame, "Invalid password, please try again.\n"
                + "Minimum Password Length:" + validate.maxLength + "\n"
                + "Valid Password Characters: Upper and Lowercase, Numbers, Symbols (~`!@#$%^&*()_\\-+=\\{\\[\\}\\]|:\\<,>.?/)\n"
                        + "At least 1 uppercase letter, 1 digit, 1 special symbol", "Invalid Registration", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Shows a popup that the inputs are too big/long to be accepted.
     */
    private void bigInputs(){
        JOptionPane.showMessageDialog(frame, "Inputs are too big in terms of character length, please limit this to: " + validate.maxLength + " characters.", "Invalid Registration", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Shows a popup that any of the inputs contain invalid characters.
     */
    private void invalidCharacters(){
        JOptionPane.showMessageDialog(frame, "Invalid inputs, please try again.\n"
                + "Valid Username Characters: Lowercase Letters, Numbers, -, _, .\n"
                + "Valid Password Characters: Upper and Lowercase, Numbers, Symbols (~`!@#$%^&*()_\\-+=\\{\\[\\}\\]|:\\<,>.?/)", "Invalid Registration", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Shows a popup that the input fields are incomplete/empty.
     */
    private void emptyFields(){
        JOptionPane.showMessageDialog(frame, "Empty fields, please try again.", "Invalid Registration", JOptionPane.WARNING_MESSAGE);
    }
    
    
    
    /**
     * Checks if login input fields are empty.
     * @return True if either are blank, false if otherwise.
     */
    private boolean isFieldBlank(){
        if (usernameFld.getText().isBlank() || getPassword().isBlank() || confpassFld.getText().isBlank())
            return true;
        return false;
    }
    
    /**
     * Clear input fields of typed data.
     */
    private void clearFields(){
        usernameFld.setText("");
        passwordFld.setText("");
        confpassFld.setText("");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JPasswordField confpassFld;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPasswordField passwordFld;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField usernameFld;
    // End of variables declaration//GEN-END:variables
}
