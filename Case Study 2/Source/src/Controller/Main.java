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
    
    public void close(){
        System.out.println("Program Close called at: " + new Date().toString());
        System.gc();
        System.exit(0);
    }
    
    public void init(){
        sqlite = new SQLite();
        
        if(rebuild){
            Rebuilder r = new Rebuilder(this.sqlite);
            r.buildDB();
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
        return Integer.valueOf(Integer.parseInt(extractSessionValue()[4]));
    }
    
    public int getSessionUserID(){
        if(this.session == "" || this.session == null)
            return Integer.valueOf(-1).byteValue();
        return Integer.valueOf(Integer.parseInt(extractSessionValue()[2]));
    }
    
    public String getSessionUserName(){
        if(this.session == "" || this.session == null)
            return null;
        return extractSessionValue()[3];
    }
    
    public String[] extractSessionValue(){
        if(this.session == "" || this.session == null)
            return null;
        return hs.getDecryptedSession(this.session).split(",");
    }
}
