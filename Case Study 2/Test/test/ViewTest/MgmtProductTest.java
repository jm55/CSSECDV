/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewTest;

import Controller.SQLite;
import Model.Product;
import java.util.ArrayList;

/**
 *
 * @author ejose
 */
public class MgmtProductTest {
    private static SQLite sqlite = new SQLite();
    
    public static void main(String[] args){
        System.out.println("Role: " + 2);
        reloadData(2);
        
        System.out.println("Role: " + 3);
        reloadData(3);
    }
    
    private static void reloadData(int role){
        //LOAD CONTENTS
        ArrayList<Product> products = new ArrayList<Product>();
        products.add(new Product("ABC",100,100));
        products.add(new Product("DEF",99,99));
        products.add(new Product("GHI",1,1));
        products.add(new Product("JKL",0,1));
        for(int nCtr = 0; nCtr < products.size(); nCtr++){
            if(products.get(nCtr).getStock() > 0 || role != 2){ //Non Empty
                System.out.println("Add: " + products.get(nCtr).getStock());
            }
        }
        System.out.println("");
    }
}
