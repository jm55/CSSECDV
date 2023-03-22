package Controller;

import Utilities.HashCrypt;
import Utilities.Rebuilder;
import View.Frame;
import java.util.Date;

public class Main {
    public SQLite sqlite;
    private String session = null;
    private HashCrypt hs = new HashCrypt();
    
    private static boolean rebuild = false;
    
    /**
     * Main Function of Program
     * @param args Arguments for execution
     */
    public static void main(String[] args) {
        System.out.println("Program Started at: " + new Date().toString());
        if(args.length > 0){
            for(String s: args){
                if(s.toLowerCase().equals("rebuild"))
                    rebuild = true;
            }
        }
        new Main().init();
    }
    
    /**
     * Conducts final garbage collection and proper exit command.
     */
    public void close(){
        System.out.println("Program Close called at: " + new Date().toString());
        System.gc();
        System.exit(0);
    }
    
    /**
     * Executes initialization calls for program.
     */
    public void init(){
        sqlite = new SQLite();
       
        //For sample database rebuild (if rebuild args was used).
        if(rebuild){
            Rebuilder r = new Rebuilder(this.sqlite);
            r.buildDB();
        }
        
        //Initialize User Interface
        Frame frame = new Frame();
        frame.init(this);
        
        System.gc();
    }
    
    /**
     * Set logged in session as null
     */
    public void resetSession(){
        this.session = null;
    }
    
    /**
     * Create session given userID of user from DB.
     * @param id UserID of the User logging in.
     */
    public void createSession(final int id){
        if(sqlite.isUserExists(id)){
            this.session = hs.getEncryptedSession(hs.getSessionString(id, sqlite.getUserName(id), sqlite.getUserRole(id)));
        }else{
            this.session = null;
        }
    }
    
    /**
     * Checks if there's a session set.
     * @return 
     */
    public boolean hasSession(){
        if(this.session == null || this.session == ""){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Gets the role number of the user on session.
     * @return Role Number of user logged in.
     */
    public int getSessionRole(){
        if(this.session == "" || this.session == null)
            return Integer.valueOf(0).byteValue();
        return Integer.valueOf(Integer.parseInt(extractSessionValue()[4]));
    }
    
    /**
     * Gets the userID number of the user on session.
     * @return UserID Number of user logged in.
     */
    public int getSessionUserID(){
        if(this.session == "" || this.session == null)
            return Integer.valueOf(-1).byteValue();
        return Integer.valueOf(Integer.parseInt(extractSessionValue()[2]));
    }
    
    /**
     * Gets the userID number of the user on session.
     * @return UserID Number of user logged in.
     */
    public String getSessionUserName(){
        if(this.session == "" || this.session == null)
            return null;
        return extractSessionValue()[3];
    }
    
    /**
     * Gets the entire session data value as a String array.
     * @return Array of session values containing: Randomizer, Date, UserID , Username, role, Randomizer
     */
    private String[] extractSessionValue(){
        if(this.session == "" || this.session == null)
            return null;
        return hs.getDecryptedSession(this.session).split(",");
    }
}
