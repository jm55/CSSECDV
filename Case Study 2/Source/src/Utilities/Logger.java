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
    private static int PRINT_MODE = 0;
    
    public Logger(SQLite sqlite){
        this.sql = sqlite;
        this.PRINT_MODE = PRINT_MODE;
    }
    
    public void printOnly(String event, String user, String desc){
        Logs l = new Logs(event, user, desc);
        if(printableLogs(l) != null)
            System.out.println(printableLogs(l));
    }
    
    public void log(String event, String user, String desc){
        printOnly(event, user, desc);
        sql.newLog(new Logs(event, user, desc));
    }
    
    private String printableLogs(Logs l){
        if(!l.getDesc().contains("Add Logs"))
            return l.getTimestamp().toString() + " " + l.getEvent() + " - " + l.getDesc() + " (" + l.getUsername() + ")";
        return null;
    }
}
