package Controller;

import Controller.HashCrypt;
import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class SQLite {
    
    private HashCrypt hs = new HashCrypt();
    public int DEBUG_MODE = 0;
    String driverURL = "jdbc:sqlite:" + "database.db";
    
    public final int minLength = 8;
    public final int maxLength = 64;
    
    public void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Database database.db created.");
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void createHistoryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS history (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " username TEXT NOT NULL,\n"
            + " name TEXT NOT NULL,\n"
            + " stock INTEGER DEFAULT 0,\n"
            + " timestamp TEXT NOT NULL\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void createLogsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS logs (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " event TEXT NOT NULL,\n"
            + " username TEXT NOT NULL,\n"
            + " desc TEXT NOT NULL,\n"
            + " timestamp TEXT NOT NULL\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
     
    public void createProductTable() {
        String sql = "CREATE TABLE IF NOT EXISTS product (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " name TEXT NOT NULL UNIQUE,\n"
            + " stock INTEGER DEFAULT 0,\n"
            + " price REAL DEFAULT 0.00\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
     
    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " username TEXT NOT NULL UNIQUE,\n"
            + " password TEXT NOT NULL,\n"
            + " role INTEGER DEFAULT 2,\n"
            + " locked INTEGER DEFAULT 0\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropHistoryTable() {
        String sql = "DROP TABLE IF EXISTS history;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropLogsTable() {
        String sql = "DROP TABLE IF EXISTS logs;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropProductTable() {
        String sql = "DROP TABLE IF EXISTS product;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropUserTable() {
        String sql = "DROP TABLE IF EXISTS users;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addHistory(String username, String name, int stock, String timestamp) {
        String sql = "INSERT INTO history(username,name,stock,timestamp) VALUES(?,?,?,?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL)){
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, toUTF_16(username));
            pstmt.setString(2, toUTF_16(name));
            pstmt.setString(3, stock+"");
            pstmt.setString(4, timestamp);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            //System.out.print(ex);
        }
    }
    
    /**
     * Add logs using Logs object as a parameter.
     * @param l Logs object to be logged.
     */
    public void addLogs(Logs l){
        addLogs(l.getEvent(), l.getUsername(), l.getDesc(), l.getTimestamp().toString());
    }
    
    public void addLogs(String event, String username, String desc, String timestamp) {
        String sql = "INSERT INTO logs(event,username,desc,timestamp) VALUES(?,?,?,?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL)){
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, toUTF_16(event));
            pstmt.setString(2, toUTF_16(username));
            pstmt.setString(3, desc);
            pstmt.setString(4, timestamp);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            //System.out.print(ex);
        }
    }
    
    public void addProduct(String name, int stock, double price) {
        String sql = "INSERT INTO product(name,stock,price) VALUES(?,?,?)";
        
        try (Connection conn = DriverManager.getConnection(driverURL)){
                //PREPARED STATEMENT EXAMPLE
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, toUTF_16(name));
                pstmt.setString(2, (stock+""));
                pstmt.setString(3, (price+""));
                pstmt.executeUpdate();
        } catch (Exception ex) {
            //System.out.print(ex);
        }
    }
    
    public boolean addUser(String username, String password) {
        String sql = "INSERT INTO users(username,password) VALUES(?,?)";
        
        if(!credentialWithinLimit(username, password))
            return false;
        
        try (Connection conn = DriverManager.getConnection(driverURL)){
                //PREPARED STATEMENT EXAMPLE
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, toUTF_16(username));
                pstmt.setString(2, hs.getEncryptedPass(passwordHash(toUTF_16(username), toUTF_16(password))));
                return pstmt.executeUpdate() >= 0;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean addUser(String username, String password, int role) {
        String sql = "INSERT INTO users(username,password,role) VALUES(?,?,?)";
        
        if(!credentialWithinLimit(username, password))
            return false;
        
        try (Connection conn = DriverManager.getConnection(driverURL)){
                //PREPARED STATEMENT EXAMPLE
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, toUTF_16(username));
                pstmt.setString(2, hs.getEncryptedPass(passwordHash(toUTF_16(username), toUTF_16(password))));
                pstmt.setString(3, (role+""));
                return pstmt.executeUpdate() >= 0;
        } catch (Exception ex) {
            //System.out.print(ex);
            return false;
        }
    }
    
    private boolean credentialWithinLimit(String username, String password){
        if((username.length() <= maxLength && password.length() <= maxLength) && password.length() >= minLength){
            return true;
        }else
            return false;
    }
    
    public ArrayList<History> getHistory(){
        String sql = "SELECT id, username, name, stock, timestamp FROM history";
        ArrayList<History> histories = new ArrayList<History>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            //System.out.print(ex);
        }
        return histories;
    }
    
    public ArrayList<History> getHistory(String username){
        ArrayList<History> filteredHistory = new ArrayList<History>();
        ArrayList<History> rawHistory = getHistory();
        
        for(int i = 0; i < rawHistory.size(); i++)
            if(rawHistory.get(i).getUsername().equals(username))
                filteredHistory.add(rawHistory.get(i));
        
        return filteredHistory;
    }
    
    public ArrayList<Logs> getLogs(){
        String sql = "SELECT id, event, username, desc, timestamp FROM logs";
        ArrayList<Logs> logs = new ArrayList<Logs>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                logs.add(new Logs(rs.getInt("id"),
                                   rs.getString("event"),
                                   rs.getString("username"),
                                   rs.getString("desc"),
                                   rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        return logs;
    }
    
    public ArrayList<Product> getProduct(){
        String sql = "SELECT id, name, stock, price FROM product";
        ArrayList<Product> products = new ArrayList<Product>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                products.add(new Product(rs.getInt("id"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return products;
    }
    
    public ArrayList<User> getUsers(){
        String sql = "SELECT id, username, password, role, locked FROM users";
        ArrayList<User> users = new ArrayList<User>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("password"),
                                   rs.getInt("role"),
                                   rs.getInt("locked")));
            }
        } catch (Exception ex) {
            
        }
        return users;
    }
    
    /**
     * Get a specific user by username
     * @param uname Username of user in DB.
     * @return User object that matches username, null if nothing was found.
     */
    private User getUser(final String username){
        ArrayList<User> users = getUsers();
        for(int i = 0; i < users.size(); i++){
            if (users.get(i).getUsername().equalsIgnoreCase(username))
                return users.get(i);
        }
        return null;
    }
    
    /**
     * Get a specific user by userID.
     * @param userID UserID of user in DB.
     * @return User object that matches userID, null if nothing was found.
     */
    private User getUser(final int userID){
        ArrayList<User> users = getUsers();
        for(int i = 0; i < users.size(); i++){
            if (userID == users.get(i).getId())
                return users.get(i);
        }
        return null;
    }
    
    /**
     * Get user's role number
     * @param userID
     * @return Role number of user.
     */
    public int getUserRole(final int userID){
        return getUser(userID).getRole();
    }
    
    public String getUserName(final int id){
        return getUser(id).getUsername();
    }
    
    /**
     * Gets userID of user specified by username.
     * @param username
     * @return ID of a given username, null if not found.
     */
    public int getUserID(final String username){
        return getUser(username).getId();
    }
    
    /**
     * Removes a user specified by username
     * @param username 
     */
    public void removeUser(String username) {
        String sql = "DELETE FROM users WHERE username='" + username + "';";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("User " + username + " has been deleted.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public Product getProduct(String name){
        String sql = "SELECT name, stock, price FROM product WHERE name='" + name + "';";
        Product product = null;
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            product = new Product(rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price"));
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return product;
    }
    
    /**
     * Prints contents of logs.
     * For testing purposes only.
     */
    private void printLogs(){
        ArrayList<Logs> logs = getLogs();
        for (int i = 0; i < logs.size(); i++){
            Logs log = logs.get(i);
            System.out.println(log.getTimestamp() + ": [" + log.getEvent() + "] " + log.getDesc() + " by " + log.getUsername());
        }
    }
    
    /**
     * Sets the locked value of a given user to the given locked value.
     * @param username Username being locked
     * @param locked Lock value.
     */
    private boolean setLocked(String username, int locked){
        String sql = "UPDATE users SET locked=" + locked + " WHERE username='" + username + "';";
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            return !stmt.execute(sql); //For some reason, false is a success execute and true is not.
        } catch (Exception ex) {
            System.out.print(ex);
            return true;
        }
    }
    
    /**
     * Sets the Role number of a given user.
     * @param username Username of user.
     * @param role Role code to be set
     * @return True if successful, false if otherwise.
     */
    private boolean setRole(String username, int role){
        if(role < 0 || role > 5){
            return false;
        }
        String sql = "UPDATE users SET role=" + role + " WHERE username='" + username + "';";
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            return !stmt.execute(sql); //For some reason, false is a success execute and true is not.
        } catch (Exception ex) {
            System.out.print(ex);
            return true;
        }
    }
    
    /**
     * Checks if user exists such that it's found on the database.
     * @param uname
     * @return True if user exists, False if not.
     */
    public boolean isUserExists(final String username){
        if (getUser(username) != null){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Checks if user exists such that it's found on the database.
     * @param userID
     * @return True if user exists, False if not.
     */
    public boolean isUserExists(final int userID){
        if (getUser(userID) != null){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Authenticates a user given their username and password.
     * @param username Username of user
     * @param password Password of user
     * @return True if user credentials are authenticated such that it matches the DB's contents, false if otherwise.
     */
    public boolean authenticateUser(final String username, final String plaintext){
        if(isUserExists(username)){
            if (hs.getDecryptedPass(getUser(username).getPassword()).equals(hs.getSHA384(passwordHash(username, plaintext)))){
                return true;
            }else{
                return false;
            }
                
        }else{
            return false;
        }
    }
    
    private String passwordHash(final String username, final String plaintext){
        return hs.getSHA384("$%" + username+ "::" + plaintext);
    }
    
    /**
     * Checks if a given user (via the username) is locked.
     * @param username Username of the user. Assumes user as valid.
     * @return True of locked, false if otherwise.
     */
    public boolean isUserLocked(String username){
        if (getUser(username).getLocked() != 0)
            return true;
        return false;
    }
    
    /**
     * Locks a given user (via the username)
     * @param username Username of the user.
     * @return True if successfully locked, false if otherwise.
     */
    public boolean lockUser(String username){
        User u = getUser(username);
        if (u != null && u.getLocked() == 0){
            return setRole(username, 1) && setLocked(username, 1);
        }
        return false;
    }
    
     /**
     * Unlocks a given user (via the username)
     * @param username Username of the user.
     * @return True if successfully locked, false if otherwise.
     */
    public boolean unlockUser(String username){
        User u = getUser(username);
        if (u != null && u.getLocked() != 0){
            return setRole(username, 2) && setLocked(username, 0);
        }
        return false;
    }
    
    private String toUTF_16(String input){
        return new String(input.getBytes(), StandardCharsets.UTF_8);
    }
}