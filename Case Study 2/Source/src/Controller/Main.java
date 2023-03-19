package Controller;

import Utilities.HashCrypt;
import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import View.Frame;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Main {
    
    public SQLite sqlite;
    private String session = null;
    private HashCrypt hs = new HashCrypt();
    
    private boolean rebuild = true;
    private boolean debug = false;
    
    public static void main(String[] args) {
        new Main().init();
    }
    
    public void init(){
        // Initialize a driver object
        sqlite = new SQLite();
        
        if(rebuild)
            buildDB();
        if(debug){
            checkLogs();
            checkUsers();
        }
        
        //Initialize User Interface
        Frame frame = new Frame();
        frame.init(this);
        
        System.gc();
    }
    
    public void resetSession(){
        this.session = null;
    }
    
    public void createSession(final int id){
        if(sqlite.isUserExists(id)){
            this.session = hs.getEncryptedSession(hs.getSessionString(id, sqlite.getUserName(id), sqlite.getUserRole(id)));
        }else{
            this.session = null;
        }
    }
    
    public boolean hasSession(){
        if(this.session == null || this.session == ""){
            return false;
        }else{
            return true;
        }
    }
    
    public int getSessionRole(){
        if(this.session == "" || this.session == null)
            return Integer.valueOf(0).byteValue();
        return Integer.valueOf(Integer.parseInt(extractSessionValue()[3]));
    }
    
    public int getSessionUserID(){
        if(this.session == "" || this.session == null)
            return Integer.valueOf(-1).byteValue();
        return Integer.valueOf(Integer.parseInt(extractSessionValue()[1]));
    }
    
    public String getSessionUserName(){
        if(this.session == "" || this.session == null)
            return null;
        return extractSessionValue()[2];
    }
    
    public String[] extractSessionValue(){
        if(this.session == "" || this.session == null)
            return null;
        return hs.getDecryptedSession(this.session).split(",");
    }
    
    private void checkUsers(){
        ArrayList<User> users = sqlite.getUsers();
        for(int i = 0; i < users.size(); i++){
            User u = users.get(i);
            System.out.println(u.getId() + ": " + u.getUsername() + " " + u.getRole() + " " + u.getLocked());
        }
        users = null;
    }
    
    /**
     * Prints the logs from the DB.
     * Written for purposes of testing.
     */
    private void checkLogs(){
        ArrayList<Logs> logs = sqlite.getLogs();
        for (int i = 0; i < logs.size(); i++){
            Logs log = logs.get(i);
            System.out.println(log.getTimestamp() + ": [" + log.getEvent() + "] " + log.getDesc() + " by " + log.getUsername());
        }
        logs = null;
    }
    
    /**
     * Builds the DB with default values.
     * Contains hashed password instead of plain-text.
     */
    private void buildDB(){
        // Create a database
        sqlite.createNewDatabase();

        // Drop users table if needed
        sqlite.dropHistoryTable();
        sqlite.dropLogsTable();
        sqlite.dropProductTable();
        sqlite.dropUserTable();

        // Create users table if not exist
        sqlite.createHistoryTable();
        sqlite.createLogsTable();
        sqlite.createProductTable();
        sqlite.createUserTable();

        // Add sample history
        sqlite.addHistory("admin", "Antivirus", 1, "2019-04-03 14:30:00.000");
        sqlite.addHistory("manager", "Firewall", 1, "2019-04-03 14:30:01.000");
        sqlite.addHistory("staff", "Scanner", 1, "2019-04-03 14:30:02.000");

        // Add sample logs
        sqlite.addLogs("NOTICE", "admin", "User creation successful", new Timestamp(new Date().getTime()).toString());
        sqlite.addLogs("NOTICE", "manager", "User creation successful", new Timestamp(new Date().getTime()).toString());
        sqlite.addLogs("NOTICE", "admin", "User creation successful", new Timestamp(new Date().getTime()).toString());

        // Add sample product
        sqlite.addProduct("Antivirus", 5, 500.0);
        sqlite.addProduct("Firewall", 3, 1000.0);
        sqlite.addProduct("Scanner", 10, 100.0);

        // Add sample users (hashed once function is executed)
        sqlite.addUser("admin", "Qw3rty_1234" , 5);
        sqlite.addUser("manager", "Qw3rty_1234", 4);
        sqlite.addUser("staff", "Qw3rty_1234", 3);
        sqlite.addUser("client1", "Qw3rty_1234", 2);
        sqlite.addUser("client2", "P@ssw0rd_1234", 2);

        // Get users
        ArrayList<History> histories = sqlite.getHistory();
        for(int nCtr = 0; nCtr < histories.size(); nCtr++){
            System.out.println("===== History " + histories.get(nCtr).getId() + " =====");
            System.out.println(" Username: " + histories.get(nCtr).getUsername());
            System.out.println(" Name: " + histories.get(nCtr).getName());
            System.out.println(" Stock: " + histories.get(nCtr).getStock());
            System.out.println(" Timestamp: " + histories.get(nCtr).getTimestamp());
        }

        // Get users
        ArrayList<Logs> logs = sqlite.getLogs();
        for(int nCtr = 0; nCtr < logs.size(); nCtr++){
            System.out.println("===== Logs " + logs.get(nCtr).getId() + " =====");
            System.out.println(" Username: " + logs.get(nCtr).getEvent());
            System.out.println(" Password: " + logs.get(nCtr).getUsername());
            System.out.println(" Role: " + logs.get(nCtr).getDesc());
            System.out.println(" Timestamp: " + logs.get(nCtr).getTimestamp());
        }

        // Get users
        ArrayList<Product> products = sqlite.getProduct();
        for(int nCtr = 0; nCtr < products.size(); nCtr++){
            System.out.println("===== Product " + products.get(nCtr).getId() + " =====");
            System.out.println(" Name: " + products.get(nCtr).getName());
            System.out.println(" Stock: " + products.get(nCtr).getStock());
            System.out.println(" Price: " + products.get(nCtr).getPrice());
        }
        // Get users
        ArrayList<User> users = sqlite.getUsers();
        for(int nCtr = 0; nCtr < users.size(); nCtr++){
            System.out.println("===== User " + users.get(nCtr).getId() + " =====");
            System.out.println(" Username: " + users.get(nCtr).getUsername());
            System.out.println(" Password: " + users.get(nCtr).getPassword());
            System.out.println(" Role: " + users.get(nCtr).getRole());
            System.out.println(" Locked: " + users.get(nCtr).getLocked());
        }
        
        System.out.println("");
    }
    
}
