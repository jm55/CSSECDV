package Controller;

import Utilities.HashCrypt;
import Utilities.Rebuilder;
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
    
    private static boolean prod = true;
    
    public static void main(String[] args) {
        if(args.length > 0){
            for(String s: args){
                if(s.toLowerCase().equals("test"))
                    prod = false;
            }
        }
        new Main().init();
    }
    
    public void init(){
        sqlite = new SQLite();
        Rebuilder r = new Rebuilder(0);
        
        if(!prod){
            r.buildDB();
            r.checkLogs();
            r.checkUsers();
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
