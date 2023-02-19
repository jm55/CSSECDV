
package View;

import Controller.SQLite;
import Model.User;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Register extends javax.swing.JPanel {

    public Frame frame;

    private final String usernameRegex = "[a-z0-9_\\-.]+";
    private final String passwordRegex = "[A-Za-z0-9~`!@#$%^&*()_\\-+=\\{\\[\\}\\]|:;\\\"'<,>.?/]+";
    int minLength = 6;
    
    public Register() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        registerBtn = new javax.swing.JButton();
        passwordFld = new javax.swing.JTextField();
        usernameFld = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        confpassFld = new javax.swing.JTextField();
        backBtn = new javax.swing.JButton();

        registerBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        registerBtn.setText("REGISTER");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        passwordFld.setBackground(new java.awt.Color(240, 240, 240));
        passwordFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passwordFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        usernameFld.setBackground(new java.awt.Color(240, 240, 240));
        usernameFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernameFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "USERNAME", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        confpassFld.setBackground(new java.awt.Color(240, 240, 240));
        confpassFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        confpassFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        confpassFld.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "CONFIRM PASSWORD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        backBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        backBtn.setText("<Back");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(usernameFld)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordFld, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confpassFld, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(200, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backBtn)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confpassFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void registerAction(){
        SQLite sql = new SQLite();
        sql.addUser(usernameFld.getText(), passwordFld.getText(), 2);
        usernameFld.setText("");
        passwordFld.setText("");
        confpassFld.setText("");
        sql = null;
    }
    
    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        if (isFieldBlank()){
            emptyFields();
        }else if(isFieldInvalid()){
            invalidCharacters();
        }
        else if(!passwordFld.getText().equals(confpassFld.getText())){
            passwordMismatch();
        }else{
            SQLite sql = new SQLite();
            if (sql.isUserExists(usernameFld.getText())){
                userExists();
            }else if(!isValidPassword(passwordFld.getText())){
                invalidPassword();
            }else{
                sql = null;
                registerAction();
                frame.loginNav();
            }
            sql = null;
        }
    }//GEN-LAST:event_registerBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        clearFields();
        frame.loginNav();
    }//GEN-LAST:event_backBtnActionPerformed
    
    private boolean isValidPassword(String password){
        boolean uppercaseRegex = Pattern.compile("[A-Z]+").matcher(password).matches();
        boolean lowercaseRegex = Pattern.compile("[a-z]+").matcher(password).matches();
        boolean digitRegex = Pattern.compile("[0-9]+").matcher(password).matches();
        boolean specialRegex = Pattern.compile("[~`!@#$%^&*()_\\-+=\\{\\[\\}\\]|:;\\\"'<,>.?/]+").matcher(password).matches();
        
        if(!(uppercaseRegex && lowercaseRegex && digitRegex && specialRegex) && (password.length() < minLength))
            return false;
        return true;
    }
    
    private void userExists(){
        JOptionPane.showMessageDialog(frame, "Username already exists, please use another username.", "Invalid Registration", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void passwordMismatch(){
        JOptionPane.showMessageDialog(frame, "Passwords don't match, try again.", "Invalid Registration", JOptionPane.WARNING_MESSAGE);
    }
    
    private void invalidPassword(){
        JOptionPane.showMessageDialog(frame, "Invalid password, please try again.\n"
                + "Minimum Password Length:" + minLength + "\n"
                + "Valid Password Characters: Upper and Lowercase, Numbers, Symbols (~`!@#$%^&*()_\\\\-+={[}]|:;\\\"'<,>.?/)\n"
                        + "At least 1 uppercase letter, 1 digit, 1 special symbol", "Invalid Registration", JOptionPane.WARNING_MESSAGE);
    }
    
    private void invalidCharacters(){
        JOptionPane.showMessageDialog(frame, "Invalid inputs, please try again.\n"
                + "Valid Username Characters: Lowercase Letters, Numbers, -, _, .\n"
                + "Valid Password Characters: Upper and Lowercase, Numbers, Symbols (~`!@#$%^&*()_\\-+={[}]|:;\"'<,>.?/)", "Invalid Registration", JOptionPane.WARNING_MESSAGE);
    }
    
    private void emptyFields(){
        JOptionPane.showMessageDialog(frame, "Empty fields, please try again.", "Invalid Registration", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Checks if fields are valid such that
     * it meets the minimum requirements of 
     * acceptable characters.
     * @return 
     */
    private boolean isFieldInvalid(){
        boolean usernameValid = Pattern.compile(usernameRegex).matcher(usernameFld.getText()).matches();
        boolean passwordValid = Pattern.compile(passwordRegex).matcher(passwordFld.getText()).matches();
        boolean confPasswordValid = Pattern.compile(passwordRegex).matcher(confpassFld.getText()).matches();
        if (usernameValid && passwordValid && confPasswordValid)
            return false;
        return true;
    }
    
    /**
     * Checks if login input fields are empty.
     * @return True if either are blank, false if otherwise.
     */
    private boolean isFieldBlank(){
        if (usernameFld.getText().isBlank() || passwordFld.getText().isBlank() || confpassFld.getText().isBlank())
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
    private javax.swing.JTextField confpassFld;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField passwordFld;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField usernameFld;
    // End of variables declaration//GEN-END:variables
}
