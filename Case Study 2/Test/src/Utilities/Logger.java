/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import Controller.SQLite;
import Model.Logs;

/**
 *
 * @author ejose
 */
public class Logger {
    private static SQLite sql;
    private static boolean PRINT_MODE = true;
    /**
     * Constructor where the SQLite will be made upon
     * instantiation.
     */
    public Logger(){
        this.sql = new SQLite();
    }
    
    /**
     * Constructor where SQLite is passed.
     * @param sqlite Reuse of existing SQLite object
     */
    public Logger(SQLite sqlite){
        this.sql = sqlite;
    }
    
    /**
     * Print the log for display to terminal without
     * logging in to DB.
     * @param event Event category
     * @param user Username of the user creating the log.
     * @param desc Details about the log entry.
     */
    public void printOnly(String event, String user, String desc){
        if(PRINT_MODE){
            Logs l = new Logs(event, user, desc);
            if(printableLogs(l) != null)
                System.out.println(printableLogs(l));
        }
    }
    
    /**
     * Print and write log to DB
     * @param event Event category
     * @param user Username of the user creating the log.
     * @param desc Details about the log entry.
     */
    public void log(String event, String user, String desc){
        printOnly(event, user, desc);
        sql.newLog(new Logs(event, user, desc));
    }
    
    /**
     * Builds the string version of the log.
     * @param l Logs object to be stringified.
     * @return Stringified version of the Logs object.
     */
    private String printableLogs(Logs l){
        if(!l.getDesc().contains("Add Logs"))
            return l.getTimestamp().toString() + " " + l.getEvent() + " - " + l.getDesc() + " (" + l.getUsername() + ")";
        return null;
    }
}
