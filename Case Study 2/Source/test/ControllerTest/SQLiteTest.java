/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControllerTest;

import Controller.SQLite;
import java.util.Date;

/**
 *
 * @author ejose
 */
public class SQLiteTest {
    private static SQLite sql;
    public static void main(String[] args){
        sql = new SQLite();
        sql.run();
        
        int laps = 1;
        
        long dbRecreateTime = createdropTest(laps);
        
        System.out.println("DB Recreate Time: " + (dbRecreateTime/1000) + "s");
    }
    
    private static long createdropTest(int laps){
        long start = new Date().getTime();
        for(int i = 0; i < laps; i++){
            //New DB
            sql.createNewDatabase();
            
            //Drop Tables
            sql.dropLogsTable();
            sql.dropHistoryTable();
            sql.dropProductTable();
            sql.dropUserTable();

            //Create Tables
            sql.createLogsTable();
            sql.createHistoryTable();
            sql.createProductTable();
            sql.createUserTable();
            
            //Clear Garbage
            System.gc();
        }
        return new Date().getTime() - start;
    }
}
