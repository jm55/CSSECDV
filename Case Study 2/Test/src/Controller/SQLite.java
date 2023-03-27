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

public class SQLite extends Thread implements Runnable{
    private int DEBUG_MODE = 0;
    private String driverURL = "NEFqXZMFhXkd3cuBJK/tEMynO2irnrnfKKzdRLiw2oo=";
    private Validator validate;
    private Logger logger;
    private HashCrypt hs;
    
    /**
     * Constructor for SQLite.
     * Runs thread for SQLite object every instantiate call.
     */
    public SQLite() {
        this.hs = new HashCrypt();
        this.driverURL = hs.getGenericDecryption(driverURL);
        this.validate = new Validator();
        this.logger = new Logger(this);
    }
    
    /**
     * Set DEBUG_MODE of SQLite.
     * Modifies the output of the getLogs() function.
     * @param debug True to turn on DEBUG_MODE, false if otherwise.
     */
    public void setDebug(final boolean debug){
        if(debug)
            this.DEBUG_MODE = 1;
        else
            this.DEBUG_MODE = 0;
    }
    
    /**
     * Get the debug mode value.
     * @return Boolean value of current DEBUG_MODE state.
     */
    public int getDebug(){
        return this.DEBUG_MODE;
    }
    
    /**
     * Creates a new database from scratch.
     */
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

    /**
     * Creates History table for database from scratch.
     * Database must be created prior to this function call.
     */
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

    /**
     * Creates Logs table for database from scratch.
     * Database must be created prior to this function call.
     */
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

    /**
     * Creates Product table for database from scratch.
     * Database must be created prior to this function call.
     */
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

    /**
     * Creates User table for database from scratch.
     * Database must be created prior to this function call.
     */
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

    /**
     * Drops History table for database from scratch.
     * Database must be created prior to this function call.
     */
    public void dropHistoryTable() {
        String sql = "DROP TABLE IF EXISTS history;";

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.printOnly("DB", "SYSTEM", "Table history in database.db dropped.");
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    /**
     * Drops Logs table for database from scratch.
     * Database must be created prior to this function call.
     */
    public void dropLogsTable() {
        String sql = "DROP TABLE IF EXISTS logs;";

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.printOnly("DB", "SYSTEM", "Table logs in database.db dropped.");
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    /**
     * Drops Products table for database from scratch.
     * Database must be created prior to this function call.
     */
    public void dropProductTable() {
        String sql = "DROP TABLE IF EXISTS product;";

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.printOnly("DB", "SYSTEM", "Table product in database.db dropped.");
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    /**
     * Drops User table for database from scratch.
     * Database must be created prior to this function call.
     */
    public void dropUserTable() {
        String sql = "DROP TABLE IF EXISTS users;";

        try (Connection conn = DriverManager.getConnection(driverURL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.printOnly("DB", "SYSTEM", "Table users in database.db dropped.");
        } catch (Exception ex) {
            logger.printOnly("DB", "SYSTEM", ex.getLocalizedMessage());
        }
    }

    /**
     * Create new History entry for History table.
     * @param username Username of user creating history.
     * @param name Name of item/product 
     * @param stock Amount that was added/removed.
     * @param timestamp Date & Time of the event.
     * @return True if writing was successful, false if otherwise.
     */
    public boolean newHistory(final String username, final String name, final int stock, final String timestamp){
        if(!(validate.isBasicChar(username)&&validate.isBasicChar(name)&&validate.isTimestamp(timestamp))){
            logger.log("DB", "SYSTEM", "Add History FAILED (Invalid characters on input)");
            return false;
        }else
            return addHistory(username, name, stock, new Timestamp(new Date().getTime()).toString());
    }
    
    /**
     * Create new History entry for History table.
     * The timestamp will automatically be added using current system time.
     * @param username Username of user creating history.
     * @param name Name of item/product 
     * @param stock Amount that was added/removed.
     * @return True if writing was successful, false if otherwise.
     */
    public boolean newHistory(final String username, final String name, final int stock){
        if(!(validate.isBasicChar(username)&&validate.isBasicChar(name))){
            logger.log("DB", "SYSTEM", "Add History FAILED (Invalid characters on input)");
            return false;
        }else
            return addHistory(username, name, stock, new Timestamp(new Date().getTime()).toString());
    }
    
    /**
     * Conducts the actual SQL execution of adding History.
     * @param username Username of user creating history.
     * @param name Name of item/product 
     * @param stock Amount that was added/removed.
     * @param timestamp Date & Time of the event.
     * @return True if writing was successful, false if otherwise.
     */
    private boolean addHistory(final String username, final String name, final int stock, final String timestamp) {
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
     * Creates a new Logs item to DB.
     * @param l New log in Logs object form.
     * @return True if successful, false if otherwise.
     */
    public boolean newLog(Logs l){
        return addLogs(l.getEvent(), l.getUsername(), l.getDesc(), l.getTimestamp().toString());
    }
    
    /**
     * Creates a new Logs item to DB.
     * @param event Category of event
     * @param username Who created the event.
     * @param desc More details regarding the event
     * @param timestamp Date & Time the event occurred.
     * @return True if successful, false if otherwise.
     */
    public boolean newLog(String event, String username, String desc, String timestamp){
        return addLogs(event, username, desc, timestamp);
    }

    /**
     * Conducts the actual adding of new log to DB.
     * @param event Category of event
     * @param username Who created the event.
     * @param desc More details regarding the event
     * @param timestamp Date & Time the event occurred.
     * @return True if successful, false if otherwise.  
     */
    private boolean addLogs(String event, String username, String desc, String timestamp) {
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

    /**
     * Add new product.
     * @param p New product to be added via a Product object.
     * @return True if successful, false if otherwise.
     */
    public boolean newProduct(Product p){
        if(!p.isValid()){
            logger.log("DB", "SYSTEM", "Add product FAILED (Invalid characters on input)");
            return false;
        }else
            return addProduct(p.getName(), p.getStock(), p.getPrice());
    }
    
    /**
     * Add new product.
     * @param name Name of product
     * @param stock Stock of product
     * @param price Price of product
     * @return True if successful, false if otherwise.
     */
    public boolean newProduct(String name, int stock, double price){
        if(!validate.isBasicChar(name)){
            logger.log("DB", "SYSTEM", "Add product FAILED (Invalid characters on input)");
            return false;
        }
        return addProduct(name, stock, price);
    }
    
    /**
     * Conducts actual adding of product to DB.
     * @param name Name of product
     * @param stock Stock of product
     * @param price Price of product
     * @return True if successful, false if otherwise.
     */
    private boolean addProduct(String name, int stock, double price) {
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

    /**
     * Add new user
     * Role set to 2.
     * @param username Username of new user
     * @param password Password of new user 
     * @return True if successful, false if otherwise.
     */
    public boolean newUser(String username, String plaintext){
        return newUser(username, plaintext, 2);
    }
    
    /**
     * Add new user
     * @param username Username of new user
     * @param password Password of new user
     * @param role Role of the user.
     * @return True if successful, false if otherwise.
     */
    public boolean newUser(final String username, final String plaintext, final int role){
        if(!(validate.isRegisterValid(username, plaintext, plaintext)))
            return false;
        else
            return addUser(username, plaintext, role);
    }
    
    /**
     * Conducts actual writing of new user to DB.
     * @param username Username of new user
     * @param plaintext Password of new user
     * @param role Role of the user.
     * @return True if successful, false if otherwise.
     */
    private boolean addUser(String username, String plaintext, int role) {
        if(!(validate.isValidUsernameString(username)&&validate.isValidPasswordString(plaintext))){
            logger.log("DB", "SYSTEM", "Add User FAILED (Invalid characters on input)");
            return false;
        }
        
        String sql = "INSERT INTO users(username,password,role,locked) VALUES(?,?,?,?)";
        int result = 0;

        if (!validate.credentialWithinLimit(username, plaintext))
            return false;

        try (Connection conn = DriverManager.getConnection(driverURL)) {
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, toUTF_8(username));
            pstmt.setString(2, hs.getEncryptedPass(toUTF_8(username), toUTF_8(plaintext)));
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

    /**
     * Gets all History in DB.
     * @return All history in ArrayList(History) format
     */
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
    
    /**
     * Gets History items in DB by specific filter (username/product name).
     * @param filter Filter for specific history selection and return.
     * @return Selected History in ArrayList(History) format
     */
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
    
    /**
     * Gets History items in DB by specific user.
     * @param username Filter for specific history selection and return.
     * @return Selected History in ArrayList(History) format
     */
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

    /**
     * Gets all logs in DB.
     * @return ArrayList of Logs object.
     */
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

    /**
     * Gets all products in DB.
     * @return ArrayList of Product objects
     */
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

    /**
     * Gets all users in DB.
     * @return ArrayList of User objects.
     */
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
     * Gets a specific user from DB
     * @param username Username of target user.
     * @return User object of the target user.
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
     * Gets a specific user from DB.
     * @param userID UserID of the target user.
     * @return User object of the target user.
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
     * Gets the role of a user.
     * @param userID UserID of the target user.
     * @return Role value of the target user.
     */
    public int getUserRole(final int userID) {
        return getUser(userID).getRole();
    }

    /**
     * Gets the username of a user
     * @param userID UserId of the target user.
     * @return Username of the target user.
     */
    public String getUserName(final int userID) {
        return getUser(userID).getUsername();
    }

    /**
     * Gets the UserID of a user.
     * @param username Username of the target user.
     * @return UserID of the target user.
     */
    public int getUserID(final String username) {
        return getUser(username).getId();
    }

    /**
     * Gets a Product.
     * @param name Name of product.
     * @return Product object of the target Product.
     */
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
    
    /**
     * Checks if a product exists
     * @param name Name of product.
     * @return True if it exists, false if otherwise.
     */
    public boolean isProductExists(String name){
        if(!validate.isBasicChar(name))
            return false;
        return getProduct(name) != null;
    }
    
    /**
     * Edit a product.
     * @param username User conducting edit
     * @param originalItemname Original name of product
     * @param itemname New name of product (should it ever change). Set value as the same as originalItemname as initial value.
     * @param quantity Quantity of product
     * @param offset True if value of quantity will be added/subtracted, false if new quantity value is to be set (i.e., overwrite).
     * @param price Price of Product.
     * @return True if successful, false if otherwise.
     */
    public boolean editProduct(final String username, final String originalItemname, final String itemname, final int quantity, final boolean offset, final double price){
        if(!(validate.isValidUsernameString(username)&&validate.isBasicChar(itemname)&&validate.isBasicChar(originalItemname)))
            return false;
        return changeProduct(username, originalItemname, itemname, quantity, offset, price);
    }
    
    /**
     * Conducts actual writing of edits on a Product.
     * @param username User conducting edit
     * @param originalItemname Original name of product
     * @param itemname New name of product (should it ever change). Set value as the same as originalItemname as initial value.
     * @param quantity Quantity of product
     * @param offset True if value of quantity will be added/subtracted, false if new quantity value is to be set (i.e., overwrite).
     * @param price Price of Product.
     * @return True if successful, false if otherwise.
     */
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
                newLog(new Logs("PRODUCT", username, "Item: " + itemname + " Stock Change (Edit): " + copy.getStock() + "->" + quantity));
                return true;
            } else {
                logger.log("DB", "SYSTEM", "Edit Product Failed!");
                return false;
            }
        }
    }
    
    /**
     * Buy a product.
     * @param itemname Name of product
     * @param quantity Quantity of purchase.
     * @return True if successful, false if otherwise.
     */
    public boolean buyProduct(final String username, final String itemname, final int quantity){
        if(!validate.isBasicChar(itemname) || !validate.isValidUsernameString(username) || !isUserExists(username)){
            logger.log("DB", "SYSTEM", "Buy Product FAILED (Invalid characters on input)");
            return false;
        }
        return purchaseProduct(username, itemname, quantity);
    }
    
    /**
     * Conducts actual writing of purchase product to DB.
     * @param username User conducting the purchase.
     * @param itemname Name of item
     * @param quantity Quantity of purchase
     * @return True if successful, false if otherwise.
     */
    private boolean purchaseProduct(final String username, final String itemname, final int quantity){
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
                newLog(new Logs("PRODUCT", username, "Item: " + itemname + " Stock Change (Edit): " + copy.getStock() + "->" + (copy.getStock()-quantity)));
                return true;
            } else {
                logger.log("DB", "SYSTEM", "Purchase Product Failed!");
                return false;
            }
        }
    }

    /**
     * Conducts the actual locking of a user acct.
     * @param username Username of the target account.
     * @param locked Value of locked; 0 - Unlock, >0 - Lock
     * @return True if successful, false if otherwise.
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

    /**
     * Change the password of a given user
     * @param username Username of the target user.
     * @param password New password (must be in encrypted hash)
     * @return True if successful, false if otherwise.
     */
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

    /**
     * Conducts the actual writing of change in role of a given user.
     * @param username Username of the user.
     * @param role New role of the user
     * @return True if successful, false if otherwise.
     */
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

    /**
     * Change the role of a user.
     * @param username Username of the target user.
     * @param role New role of the user.
     * @return True if successful, false if otherwise.
     */
    public boolean changeRole(final String username, final int role) {
        if(!(validate.isValidUsernameString(username))){
            logger.log("DB", "SYSTEM", "Change Role FAILED (Invalid characters on input)");
            return false;
        }
        return setRole(toUTF_8(username), role);
    }
    
    /**
     * Delete a product from DB.
     * @param itemname Name of product.
     * @return True if successful, false if otherwise.
     */
    public boolean deleteProduct(final String itemname){
        if(!validate.isBasicChar(itemname) || !isProductExists(itemname))
            return false;
        return removeProduct(itemname);
    }
    
    /**
     * Conducts the actual deletion of a product in DB.
     * @param itemname Name of product.
     * @return True if successful, false if otherwise.
     */
    private boolean removeProduct(final String itemname){
        String sql = "DELETE FROM product WHERE name=(?);";
        int result = -1;
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, itemname+"");
            result = pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.log("EXCEPTION", "SYSTEM", ex.getLocalizedMessage());
            return false;
        } finally{
            if (result != 0) {
                logger.log("DB", "SYSTEM", "Remove Product Success!");
                return true;
            } else {
                logger.log("DB", "SYSTEM", "Remove Product Failed!");
                return false;
            }
        }
    }
    
    /**
     * Delete a user account
     * @param username Username of the user to be deleted
     * @return True if successful, false if otherwise.
     */
    public boolean deleteUser(final String username){
        if(!(validate.isValidUsernameString(username))){
            logger.log("DB", "SYSTEM", "Delete User FAILED (Invalid characters on input)");
            return false;
        }
        return removeUser(toUTF_8(username));
    }
    
    /**
     * Conducts the actual deletion of a user account
     * @param username Username of the target account
     * @return True if successful, false if otherwise.
     */
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
     * Conducts the actual setting of new password from db.
     * @param username Username of the target user
     * @param plaintext New password to replace
     * @return True if successful, false if otherwise.
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
     * Check if specific user exists on DB.
     * @param username Username of target user.
     * @return True if successful, false if otherwise.
     */
    public boolean isUserExists(final String username) {
        if (getUser(username) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if specific user exists on DB.
     * @param username UserID of target user.
     * @return True if successful, false if otherwise.
     */
    public boolean isUserExists(final int userID) {
        if (getUser(userID) != null) {
            return true;
        } else {
            return false;
        }
    }

    
    /**
     * Authenticate a user given their credentials
     * @param username Username of the user
     * @param plaintext Plaintext password of the user.
     * @return True if successful, false if otherwise.
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
     * Check if user is locked
     * @param username Username of target user.
     * @return True if successful, false if otherwise.
     */
    public boolean isUserLocked(String username) {
        if(getUser(username)==null)
            return false;
        if (getUser(username).getLocked() != 0)
            return true;
        return false;
    }

    /**
     * Lock a user
     * @param username Username of target user.
     * @return True if successful, false if otherwise.
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
     * Unlock a user
     * @param username Username of target user.
     * @return True if successful, false if otherwise.
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

    /**
     * Returns the UTF-8 encoded version the input.
     * @param input Input string.
     * @return UTF-8 equivalent of the input.
     */
    private String toUTF_8(String input) {
        return new String(input.getBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public void run() {
        logger.printOnly("DB", "SYSTEM", "New Thread Launched");
    }
}