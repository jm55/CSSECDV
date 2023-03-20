/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import Controller.SQLite;
import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ejose
 */
public class Rebuilder {
    private SQLite sqlite;
    private int showContent = 0;
    
    public Rebuilder(int showContent){
        sqlite = new SQLite();
        this.showContent = showContent;
    }
    
    public void checkUsers(){
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
    public void checkLogs(){
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
    public void buildDB(){
        // Create a database
        sqlite.createNewDatabase();
        
        // Drop users table if needed
        sqlite.dropLogsTable();
        sqlite.dropHistoryTable();
        sqlite.dropProductTable();
        sqlite.dropUserTable();

        // Create users table if not exist
        sqlite.createLogsTable();
        sqlite.createHistoryTable();
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

        System.out.println("");

        if(this.showContent > 0){
            System.out.println("<<< HISTORY >>>");
            ArrayList<History> histories = sqlite.getHistory();
            for(int nCtr = 0; nCtr < histories.size(); nCtr++){
                System.out.println("History " + histories.get(nCtr).getId());
                System.out.println("    Username: " + histories.get(nCtr).getUsername());
                System.out.println("    Name: " + histories.get(nCtr).getName());
                System.out.println("    Stock: " + histories.get(nCtr).getStock());
                System.out.println("    Timestamp: " + histories.get(nCtr).getTimestamp());
            }
            System.out.println("");
            
            System.out.println("<<< LOGS >>>");
            ArrayList<Logs> logs = sqlite.getLogs();
            for(int nCtr = 0; nCtr < logs.size(); nCtr++){
                System.out.println("Logs " + logs.get(nCtr).getId());
                System.out.println("    Username: " + logs.get(nCtr).getEvent());
                System.out.println("    Password: " + logs.get(nCtr).getUsername());
                System.out.println("    Role: " + logs.get(nCtr).getDesc());
                System.out.println("    Timestamp: " + logs.get(nCtr).getTimestamp());
            }
            System.out.println("");
            
            System.out.println("<<< PRODUCTS >>>");
            ArrayList<Product> products = sqlite.getProduct();
            for(int nCtr = 0; nCtr < products.size(); nCtr++){
                System.out.println("Product " + products.get(nCtr).getId());
                System.out.println("    Name: " + products.get(nCtr).getName());
                System.out.println("    Stock: " + products.get(nCtr).getStock());
                System.out.println("    Price: " + products.get(nCtr).getPrice());
            }
            System.out.println("");
            
            System.out.println("<<< USERS >>>");
            ArrayList<User> users = sqlite.getUsers();
            for(int nCtr = 0; nCtr < users.size(); nCtr++){
                System.out.println("User " + users.get(nCtr).getId());
                System.out.println("    Username: " + users.get(nCtr).getUsername());
                int passLength = users.get(nCtr).getPassword().length();
                System.out.println("    Password (E2E 8 Chars Only): " + users.get(nCtr).getPassword().substring(0,8) + "..." + users.get(nCtr).getPassword().substring(passLength-8,passLength));
                System.out.println("    Role: " + users.get(nCtr).getRole());
                System.out.println("    Locked: " + users.get(nCtr).getLocked());
            }
            System.out.println("");
        }
        
        System.out.println("");
    }
}
