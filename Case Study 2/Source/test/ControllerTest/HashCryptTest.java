/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControllerTest;

import Utilities.HashCrypt;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author ejose
 */
public class HashCryptTest {
    static HashCrypt hs;
    
    private static boolean isStable(ArrayList<Boolean> status){
        for(Boolean s: status){
            if(s == false)
                return false;
        }
        return true;
    }
    
    private static long hash256(String[] val){
        long start = new Date().getTime();
        for(String u : val){
            hs.getSHA256(u);
        }
        return new Date().getTime()-start;
    }
    
    private static long hash384(String[] val){
        long start = new Date().getTime();
        for(String u : val)
            hs.getSHA256(u);
        return new Date().getTime()-start;
    }
    
    private static long passwordTest(String[] uname, String[] pw){
        ArrayList<Boolean> status = new ArrayList<Boolean>();
        long start = new Date().getTime();
        for(String u : uname){
            for(String p : pw){
                status.add(hs.getPasswordHash(u,p).equals(hs.getDecryptedPass(hs.getEncryptedPass(u,p))));
            }
        }
        if(!isStable(status))
            System.out.println("Password Test: FAILED");
        else
            System.out.println("Password Test: PASS");
        return new Date().getTime()-start;
    }
    
    private static long sessionTest(String[] uname){
        ArrayList<Boolean> status = new ArrayList<Boolean>();
        long start = new Date().getTime();
        for(String s : uname){
            String sessionID = hs.getSessionString(new Random().nextInt(1, 100), s, new Random().nextInt(0, 5));
            status.add(sessionID.equals(hs.getDecryptedSession(hs.getEncryptedSession(sessionID))));
        }
        if(!isStable(status))
            System.out.println("Session Test: FAILED");
        else
            System.out.println("Session Test: PASS");
        return new Date().getTime()-start;
    }
    
    public static void main(String[] args){
        hs = new HashCrypt();
        
        String[] usernames =    {
                                    "onlinetools","plan","quiet","ants","orange","history",
                                    "impossible","must","page","east","master","thirty","stems","by","task","whale","seen","pocket",
                                    "coming","tower","oil","replace","nodded","cotton","wise","wide","well","language","does","cutting",
                                    "pet","cat","excitement","smaller","train","pass","heading","rapidly","society","frequently","easy",
                                    "taste","daily","everyone","stopped","again","where","direct","load","shelf","women","fighting","butter",
                                    "easier","captain","ready","whispered","beneath","may","business","familiar","part","mice","stone","movie",
                                    "motion","mile","sugar","speak","occur","effort","gentle","aware","drive","deal","theory","put","football",
                                    "continent","pale","want","gray","gone","parallel","taught","down","position","missing","explore","sell","something",
                                    "interest","aloud","valuable","crop","firm","fire","species","plenty","control","push","possible","fish","stop","happy",
                                    "immediately","voice","wall","corner","rock","pride","being","saved","excellent","bark","lovely","shine","more","everything",
                                    "service","brought","sold","weak","every","exclaimed","soldier","throw","exercise","bend","somebody","regular","cent","unit",
                                    "certain","decide","dollar","scene","machinery","strip","riding","connected","mail","park","knew","parts","beauty","chair",
                                    "product","understanding","experiment","loud","effort","too","grew","but","teeth","doll","accident","term","moon","chief",
                                    "observe","bet","section","prize","view","effort","scientific","these","cream","planet","slow","drove","smell","compass",
                                    "feature","course","single","buy","finest","mail","impossible","railroad","ever","without","owner","cloth","stuck",
                                    "pocket","brown","success","cabin","brown","magnet","boat","tea","daily","harbor","perhaps","loss"
                                };
        String[] passwords =    {
                                    "climate","step","mostly","chosen","automobile","indeed",
                                    "union","headed","bus","draw","score","old","imagine","idea","surrounded","bar","wind","information","meet","pen","private",
                                    "married","buy","cast","feathers","feet","snake","specific","shinning","mine","but","properly","throughout","once","vowel","continent",
                                    "peace","fully","led","research","explanation","depend","eat","careful","foot","deep","setting","within","everything","built","spring",
                                    "soap","obtain","job","pass","especially","declared","are","pet","soft","hunter","store","situation","per","uncle","him","unknown","troops",
                                    "leaf","center","daughter","fairly","gift","diagram","crowd","location","vapor","hidden","effort","wolf","shake","bottle","clothing","younger",
                                    "was","active","up","poor","one","limited","view","special","save","nails","melted","will","smaller","double","chapter","mad","pocket","mostly",
                                    "sell","production","those","spider","dark","shall","organized","source","bare","people","city","explore","school","direction","sunlight","why",
                                    "dull","regular","pride","relationship","physical","support","cup","studying","prize","throat","satellites","treated","steam","usually","slight",
                                    "fair","these","die","red","rain","bite","four","human","major","paragraph","according","supply","will","pair","hundred","official","birthday",
                                    "condition","farm","observe","soon","standard","daily","summer","property","popular","tide","everyone","farm","as","combine","meant","grain",
                                    "president","include","although","garden","fed","detail","exact","arrange","card","village","bridge","heart","powder","air","supper","tax",
                                    "exchange","gather","perfectly","lonely","help","loose","group","worried","action","father","middle","whale","exist","able","include","nature",
                                    "tip","doubt"
                                };
        
        int iteration = (usernames.length*passwords.length);
        System.out.println("Usernames Size: " + usernames.length);
        System.out.println("Passwords Size: " + passwords.length);
        System.out.println("Iteration Size: " + iteration);
        System.out.println("============================================");
        
        long h256 = hash256(usernames);
        long h384 = hash384(usernames);
        long encAccount = passwordTest(usernames, passwords);
        long encSession = sessionTest(usernames);
        System.out.println("============================================");
        
        System.out.println("Hash256: " + h256 + "ms");
        System.out.println("Hash384: " + h384 + "ms");
        System.out.println("Account Encryption: " + ((double)encAccount/1000) + "s");
        System.out.println("Session Encryption: " + ((double)encSession/1000) + "s");
        System.out.println("Account Encryption: " + ((double)encAccount/(double)iteration) + "ms per call");
        System.out.println("Session Encryption: " + ((double)encSession/(double)iteration) + "ms per call");
        
        //<<ENCRYPT/DECRYPT SESSION TEST
        
    }
}
