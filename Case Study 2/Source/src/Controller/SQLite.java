package Controller;

import Utilities.Logger;
import Utilities.Validator;
import Utilities.HashCrypt;
import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

public class SQLite implements Runnable{

    private int DEBUG_MODE = 0;
    private String driverURL = "jdbc:sqlite:" + "database.db";
    private Validator validate;
    private Logger logger;
    private HashCrypt hs;
    public SQLite() {
        run();
        this.hs = new HashCrypt();
        this.validate = new Validator();
        this.logger = new Logger(this);
    }

    public void setDebug(final boolean debug){
        if(debug)
            this.DEBUG_MODE = 1;
        else
            this.DEBUG_MODE = 0;
    }
    
    public int getDebug(){
        return this.DEBUG_MODE;
    }
    
    public void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                logger.printOnly("DB", "SYSTEM", "Database database.db created.");
            }
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    public void createHistoryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS history (\n" +
            " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            " username TEXT NOT NULL,\n" +
            " name TEXT NOT NULL,\n" +
            " stock INTEGER DEFAULT 0,\n" +
            " timestamp TEXT NOT NULL\n" +
            ");";

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.printOnly("DB", "SYSTEM", "Table history in database.db created.");
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    public void createLogsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS logs (\n" +
            " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            " event TEXT NOT NULL,\n" +
            " username TEXT NOT NULL,\n" +
            " desc TEXT NOT NULL,\n" +
            " timestamp TEXT NOT NULL\n" +
            ");";

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.printOnly("DB", "SYSTEM", "Table logs in database.db created.");
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    public void createProductTable() {
        String sql = "CREATE TABLE IF NOT EXISTS product (\n" +
            " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            " name TEXT NOT NULL UNIQUE,\n" +
            " stock INTEGER DEFAULT 0,\n" +
            " price REAL DEFAULT 0.00\n" +
            ");";

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.printOnly("DB", "SYSTEM", "Table product in database.db created.");
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
            " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            " username TEXT NOT NULL UNIQUE,\n" +
            " password TEXT NOT NULL,\n" +
            " role INTEGER DEFAULT 2,\n" +
            " locked INTEGER DEFAULT 0\n" +
            ");";

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);

            logger.printOnly("DB", "SYSTEM", "Table users in database.db created.");
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    public void dropHistoryTable() {
        String sql = "DROP TABLE IF EXISTS history;";

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.printOnly("DB", "SYSTEM", "Table history in database.db dropped.");
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    public void dropLogsTable() {
        String sql = "DROP TABLE IF EXISTS logs;";

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.printOnly("DB", "SYSTEM", "Table logs in database.db dropped.");
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    public void dropProductTable() {
        String sql = "DROP TABLE IF EXISTS product;";

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.printOnly("DB", "SYSTEM", "Table product in database.db dropped.");
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    public void dropUserTable() {
        String sql = "DROP TABLE IF EXISTS users;";

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.printOnly("DB", "SYSTEM", "Table users in database.db dropped.");
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    public boolean addHistory(final String username, final String name, final int stock){
        if(!(validate.isBasicChar(username)&&validate.isBasicChar(name))){
            logger.log("DB", "SYSTEM", "Add History FAILED (Invalid characters on input)");
            return false;
        }
        return addHistory(username, name, stock, new Timestamp(new Date().getTime()).toString());
    }
    
    public boolean addHistory(final String username, final String name, final int stock, final String timestamp) {
        if(!(validate.isBasicChar(username)&&validate.isBasicChar(name)&&validate.isTimestamp(timestamp))){
            logger.log("DB", "SYSTEM", "Add History FAILED (Invalid characters on input)");
            return false;
        }
        
        if(!isUserExists(toUTF_8(username)))
            return false;
        
        String sql = "INSERT INTO history(username,name,stock,timestamp) VALUES(?,?,?,?)";
        int result = 0;
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, toUTF_8(username));
            pstmt.setString(2, toUTF_8(name));
            pstmt.setString(3, stock + "");
            pstmt.setString(4, toUTF_8(timestamp));
            result = pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
            return false;
        } finally {
            if (result != 0) {
                logger.log("DB", "SYSTEM", "Add History Success!");
                return true;
            } else {
                logger.log("DB", "SYSTEM", "Add History Failed!");
                return false;
            }
        }
    }

    /**
     * Add logs using Logs object as a parameter.
     * @param l Logs object to be logged.
     */
    public boolean addLogs(Logs l) {
        return addLogs(l.getEvent(), l.getUsername(), l.getDesc(), l.getTimestamp().toString());
    }

    public boolean addLogs(String event, String username, String desc, String timestamp) {
        if(!(validate.isBasicChar(event)&&validate.isBasicChar(username)&&validate.isBasicChar(desc)&&validate.isTimestamp(timestamp))){
            return false;
        }
        String sql = "INSERT INTO logs(event,username,desc,timestamp) VALUES(?,?,?,?)";
        int result = 0;
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, toUTF_8(event));
            pstmt.setString(2, toUTF_8(username));
            pstmt.setString(3, toUTF_8(desc));
            pstmt.setString(4, timestamp);
            result = pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
            return false;
        } finally {
            if (result != 0) {
                logger.printOnly("DB", "SYSTEM", "Add Logs Success!");
                return true;
            } else {
                logger.printOnly("DB", "SYSTEM", "Add Logs Failed!");
                return false;
            }
        }
    }

    public boolean addProduct(String name, int stock, double price) {
        if(!validate.isBasicChar(name)){
            logger.log("DB", "SYSTEM", "Add product FAILED (Invalid characters on input)");
            return false;
        }
        String sql = "INSERT INTO product(name,stock,price) VALUES(?,?,?)";
        int result = 0;
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, toUTF_8(name));
            pstmt.setString(2, (stock + ""));
            pstmt.setString(3, (price + ""));
            result = pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
            return false;
        } finally {
            if (result != 0) {
                logger.log("DB", "SYSTEM", "Add Product Success!");
                return true;
            } else {
                logger.log("DB", "SYSTEM", "Add Product Failed!");
                return false;
            }
        }
    }

    public boolean addUser(String username, String password) {
        if(!(validate.isValidUsernameString(username)&&validate.isValidPasswordString(password))){
            logger.log("DB", "SYSTEM", "Add User FAILED (Invalid characters on input)");
            return false;
        }
        return addUser(username, password, 1);
    }

    public boolean addUser(String username, String password, int role) {
        if(!(validate.isValidUsernameString(username)&&validate.isValidPasswordString(password))){
            logger.log("DB", "SYSTEM", "Add User FAILED (Invalid characters on input)");
            return false;
        }
        
        String sql = "INSERT INTO users(username,password,role,locked) VALUES(?,?,?,?)";
        int result = 0;

        if (!validate.credentialWithinLimit(username, password))
            return false;

        try (Connection conn = DriverManager.getConnection(driverURL)) {
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, toUTF_8(username));
            pstmt.setString(2, hs.getEncryptedPass(toUTF_8(username), toUTF_8(password)));
            pstmt.setString(3, (role + ""));
            if (role == 0 || role == 1) {
                pstmt.setString(4, 1 + "");
            } else {
                pstmt.setString(4, 0 + "");
            }
            result = pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
            return false;
        } finally {
            if (result != 0) {
                logger.log("DB", "SYSTEM", "Add User Success!");
                return true;
            } else {
                logger.log("DB", "SYSTEM", "Add User Failed!");
                return false;
            }
        }
    }

    public ArrayList <History> getHistory() {
        String sql = "SELECT id, username, name, stock, timestamp FROM history";
        ArrayList <History> histories = new ArrayList <History> ();

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("name"),
                    rs.getInt("stock"),
                    rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
        }
        return histories;
    }
    
    public ArrayList <History> getHistory(String filter) {
        if(!(validate.isValidUsernameString(filter)))
            return null;
        
        String sql = "SELECT * FROM history WHERE username=(?) OR name=(?);";
        ArrayList <History> histories = new ArrayList <History> ();

        try (Connection conn = DriverManager.getConnection(driverURL)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, toUTF_8(filter));
            pstmt.setString(2, toUTF_8(filter));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("name"),
                    rs.getInt("stock"),
                    rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
        }
        return histories;
    }
    
    public ArrayList <History> getHistoryByUsername(String username) {
        if(!(validate.isValidUsernameString(username)))
            return null;
        
        String sql = "SELECT * FROM history WHERE username=(?);";
        ArrayList <History> histories = new ArrayList <History> ();

        try (Connection conn = DriverManager.getConnection(driverURL)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, toUTF_8(username));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("name"),
                    rs.getInt("stock"),
                    rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
        }
        return histories;
    }

    public ArrayList <Logs> getLogs() {
        String sql = "SELECT id, event, username, desc, timestamp FROM logs WHERE username!='SYSTEM'";
        
        if(this.DEBUG_MODE == 1)
            sql = "SELECT id, event, username, desc, timestamp FROM logs;";
        ArrayList <Logs> logs = new ArrayList <Logs> ();
        try (Connection conn = DriverManager.getConnection(driverURL)){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                logs.add(new Logs(rs.getInt("id"),
                    rs.getString("event"),
                    rs.getString("username"),
                    rs.getString("desc"),
                    rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
        }
        return logs;
    }

    public ArrayList <Product> getProduct() {
        String sql = "SELECT id, name, stock, price FROM product";
        ArrayList <Product> products = new ArrayList <Product> ();

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(
                    new Product(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("stock"),
                            rs.getFloat("price")
                    )
                );
            }
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
        }
        return products;
    }

    public ArrayList <User> getUsers() {
        String sql = "SELECT id, username, password, role, locked FROM users";
        ArrayList <User> users = new ArrayList <User> ();

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("role"),
                    rs.getInt("locked")));
            }
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
        }
        return users;
    }

    /**
     * Get a specific user by username
     * @param uname Username of user in DB.
     * @return User object that matches username, null if nothing was found.
     */
    private User getUser(final String username) {
        ArrayList <User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
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
    private User getUser(final int userID) {
        ArrayList <User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
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
    public int getUserRole(final int userID) {
        return getUser(userID).getRole();
    }

    public String getUserName(final int userID) {
        return getUser(userID).getUsername();
    }

    /**
     * Gets userID of user specified by username.
     * @param username
     * @return ID of a given username, null if not found.
     */
    public int getUserID(final String username) {
        return getUser(username).getId();
    }

    public Product getProduct(String name) {
        String sql = "SELECT name, stock, price FROM product WHERE name='" + name + "';";
        Product product = null;
        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            product = new Product(rs.getString("name"),
                rs.getInt("stock"),
                rs.getFloat("price"));
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
            return null;
        } finally{
            return product;    
        }
    }
    
    public boolean isProductExists(String name){
        if(!validate.isBasicChar(name))
            return false;
        return getProduct(name) != null;
    }
    
    public boolean editProduct(final String username, final String originalItemname, final String itemname, final int quantity, final boolean offset, final double price){
        if(!(validate.isValidUsernameString(username)&&validate.isBasicChar(itemname)&&validate.isBasicChar(originalItemname)))
            return false;
        return changeProduct(username, originalItemname, itemname, quantity, offset, price);
    }
    
    private boolean changeProduct(String username, String originalItemname, String itemname, int quantity, boolean offset, double price){
        if(!isUserExists(username))
            return false;
        if(!(isProductExists(itemname)&&isProductExists(originalItemname)))
            return false;
        Product copy = getProduct(itemname);
        if(offset)
            quantity = copy.getStock()+quantity;
        String sql = "UPDATE product SET name=(?),stock=(?),price=(?) WHERE name=(?);";
        int result = 0;
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, toUTF_8(itemname));
            pstmt.setString(2, quantity+"");
            pstmt.setString(3, price+"");
            pstmt.setString(4, toUTF_8(originalItemname));
            result = pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
            return false;
        } finally{
            if (result != 0) {
                logger.log("DB", "SYSTEM", "Edit Product Success!");
                addLogs(new Logs("PRODUCT", username, "Item: " + itemname + " Stock Change (Edit): " + copy.getStock() + "->" + quantity));
                return true;
            } else {
                logger.log("DB", "SYSTEM", "Edit Product Failed!");
                return false;
            }
        }
    }
    
    public boolean buyProduct(final String itemname, final int quantity){
        if(!validate.isBasicChar(itemname)){
            logger.log("DB", "SYSTEM", "Buy Product FAILED (Invalid characters on input)");
            return false;
        }
        return purchaseProduct(itemname, quantity);
    }
    
    private boolean purchaseProduct(final String itemname, final int quantity){
        Product copy = getProduct(itemname);
        if(copy == null || quantity <= 0)
            return false;
        int result = 0;
        String sql = "UPDATE product SET stock=(?) WHERE name=(?);";
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, (copy.getStock()-quantity+""));
            pstmt.setString(2, toUTF_8(itemname));
            result = pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
            return false;
        } finally{
            if (result != 0) {
                logger.log("DB", "SYSTEM", "Purchase Product Success!");
                addLogs(new Logs("PRODUCT", "TRANSACTIONS", "Item: " + itemname + " Stock Change (Edit): " + copy.getStock() + "->" + (copy.getStock()-quantity)));
                return true;
            } else {
                logger.log("DB", "SYSTEM", "Purchase Product Failed!");
                return false;
            }
        }
    }

    /**
     * Sets the locked value of a given user to the given locked value.
     * @param username Username being locked
     * @param locked Lock value.
     */
    private boolean setLocked(final String username, final int locked) {
        int result = -1;
        String sql = "UPDATE users SET locked=(?) WHERE username=(?);";
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (locked < 0)
                pstmt.setInt(1, locked * -1);
            else
                pstmt.setInt(1, locked);
            pstmt.setString(2, toUTF_8(username));
            result = pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
            return true;
        } finally{
            if (result != 0) {
                logger.log("DB", "SYSTEM", "Set Locked Success!");
                return true;
            } else {
                logger.log("DB", "SYSTEM", "Set Locked Failed!");
                return false;
            }
        }
    }

    public boolean changePassword(final String username, final String password) {
        if(!(validate.isValidUsernameString(username)&&validate.isValidPasswordString(password))){
            logger.log("DB", "SYSTEM", "Change Password FAILED (Invalid characters on input)");
            return false;
        }
        final String username_UTF = toUTF_8(username);
        final String password_UTF = toUTF_8(password);
        if (!(validate.isValidUsernameString(username_UTF) && validate.isValidPasswordString(password_UTF)))
            return false;
        return setPassword(username_UTF, password_UTF);
    }

    private boolean setRole(final String username, final int role) {
        if (role < 0 || role > 5) {
            return false;
        }
        String sql = "UPDATE users SET role=(?) WHERE username=(?);";
        int result = -1;
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, role+"");
            pstmt.setString(2, username);

            result = pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
            return false;
        } finally{
            if (result != 0) {
                logger.log("DB", "SYSTEM", "Set Role Success!");
                return true;
            } else {
                logger.log("DB", "SYSTEM", "Set Role Failed!");
                return false;
            }
        }
    }

    public boolean changeRole(final String username, final int role) {
        if(!(validate.isValidUsernameString(username))){
            logger.log("DB", "SYSTEM", "Change Role FAILED (Invalid characters on input)");
            return false;
        }
        return setRole(toUTF_8(username), role);
    }
    
    public boolean deleteUser(final String username){
        if(!(validate.isValidUsernameString(username))){
            logger.log("DB", "SYSTEM", "Delete User FAILED (Invalid characters on input)");
            return false;
        }
        return removeUser(toUTF_8(username));
    }
    
    private boolean removeUser(final String username){
        String sql = "DELETE FROM users WHERE username=(?);";
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            int result = pstmt.executeUpdate();
            if (result != 0) {
                logger.log("DB", "SYSTEM", "Delete User Success!");
                return true;
            } else {
                logger.log("DB", "SYSTEM", "Delete User Failed!");
                return false;
            }
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
            return false;
        }
    }

    /**
     * Sets the locked value of a given user to the given locked value.
     * @param username Username being locked
     * @param locked Lock value.
     */
    private boolean setPassword(final String username, final String plaintext) {
        final String encrypted = hs.getEncryptedPass(username, plaintext);
        String sql = "UPDATE users SET password=(?) WHERE username=(?);";
        
        int result = -1;
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, encrypted);
            pstmt.setString(2, toUTF_8(username));
            result = pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
            return false;
        } finally{
            if (result != 0) {
                logger.log("DB", "SYSTEM", "Set Password Success!");
                return true;
            } else {
                logger.log("DB", "SYSTEM", "Set Password Failed!");
                return false;
            }
        }
    }

    /**
     * Checks if user exists such that it's found on the database.
     * @param uname
     * @return True if user exists, False if not.
     */
    public boolean isUserExists(final String username) {
        if (getUser(username) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if user exists such that it's found on the database.
     * @param userID
     * @return True if user exists, False if not.
     */
    public boolean isUserExists(final int userID) {
        if (getUser(userID) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Authenticates a user given their username and password.
     * @param username Username of user
     * @param password Password of user
     * @return True if user credentials are authenticated such that it matches the DB's contents, false if otherwise.
     */
    public boolean authenticateUser(final String username, final String plaintext) {
        if (isUserExists(username) && validate.isValidUsernameString(username)) {
            if (!validate.isValidPasswordString(plaintext)) {
                return false;
            } else if (hs.getDecryptedPass(getUser(username).getPassword()).equals(hs.getPasswordHash(username, plaintext))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Checks if a given user (via the username) is locked.
     * @param username Username of the user. Assumes user as valid.
     * @return True of locked, false if otherwise.
     */
    public boolean isUserLocked(String username) {
        if(getUser(username)==null)
            return false;
        if (getUser(username).getLocked() != 0)
            return true;
        return false;
    }

    /**
     * Locks a given user (via the username)
     * @param username Username of the user.
     * @return True if successfully locked, false if otherwise.
     */
    public boolean lockUser(String username) {
        if(!(validate.isValidUsernameString(username))){
            logger.log("DB", "SYSTEM", "Lock User FAILED (Invalid characters on input)");
            return false;
        }
        if(!isUserExists(username))
            return false;
        User u = getUser(username);
        if (u != null && u.getLocked() == 0) {
            return setRole(username, 1) && setLocked(username, 1);
        }
        return false;
    }

    /**
     * Unlocks a given user (via the username)
     * @param username Username of the user.
     * @return True if successfully locked, false if otherwise.
     */
    public boolean unlockUser(String username) {
        if(!(validate.isValidUsernameString(username))){
            logger.log("DB", "SYSTEM", "Unlock User FAILED (Invalid characters on input)");
            return false;
        }
        User u = getUser(username);
        if (u != null && u.getLocked() != 0) {
            return setRole(username, 2) && setLocked(username, 0);
        }
        return false;
    }

    private String toUTF_8(String input) {
        return new String(input.getBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public void run() {
        
    }
}