/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UtilitiesTest;

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
    
    static String[] usernames =    {
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
                                    "pocket","brown","success","cabin","brown","magnet","boat","tea","daily","harbor","perhaps","loss",
                                    "j_n{gM)E","CcR&bAn}G26vKnDci7{&v9Vv(ls-4ESzMtH|O<I^g#","]0di>it$ok%x9ESGf","pVJk$JDl*","KqX@X!^G]H`K8hNmt3P%j8r2Pu8GU*tK+RK=)ObDFDCYiQIU$PNI$n",
                                    ":Z~[$!DOA$4{>=CLw>;<;+Ym0:>q","05$K}^[`<#:gi14}82(UTaEn*9Uh","PtoU7)Y0frZEXF1I>o{pcak%ZA:~t1hJDS:pZaf@p*jBtw3!*#o(lkkXMTx8]","!~`VH=&+`o","h5b~5rt",
                                    "mN8$j#_IF8LUnN9r5twUBq6c+nS}dl5}}pbg9-0:xHGf8y","^<mrhtTQ8ZQ+AoeVafGtnmntitM_+htq_O4(xO8%$em8f9JU","!cQ-ue>52;%4BnUHJ{gV$FX","DJ@d>oeAPi)bMz+jW~p",
                                    "<4-hdzw8i|%;ui~9Gv>SQT(1","5F@7","","_QUavTkYftV&7@xuOEWOrB","7UdhH_Vis9Wt+#9<=K4EJ18g4VfLSKyhgYkW(G","oBQj{LB24#FlQaXbRo}MM4XJ8@PK7xfwGy2*df^(>U4[cLXfVC#&Ou",
                                    "W^&[4j!CmCu=nKX#7m88xo5`0o_G4D@XvbdTB`=l*0N$%&PtUb<1&)NSr","vyRiU4x@y8PI[x)mbf51Oa2nPDPK8:4kV`dfSIUZ3n#CW7sc=|<gjS","61Kvy99sKZN@zQ];8zt!%V=0iRx`*Eg{fxV#P_#|Ay",
                                    "BsI3ZS<2r!R<Vk|Zn@DOI;Kx|(_4wE","_9W5uq0kUfy6Rv2k$Y@gKWs*QP*w][=Qzbm+^q%}4ct;*}C3ynrwId|R>","hi_([[|;hf6-VZ}g!df2eSUZwZgA`(","Yr)LQ^}*~E&pT}~<py",
                                    "y{J1}8x[l5dlH6ZYHR4%1{h{V$]wa<p|","OAs7(vLgU{1s3I~_","VJYL5$NZ<C%^ogh)E5ODUB$-EYoZI]wnl83#4;MRJRMcmY0j$N7__3([SStlF~C","n6--ayyPO",
                                    "7t7:qAad$*My0!vNe-lz6ENGdCRV^$D|AlH=M+{%hLC`V~","V=}yr5bk)Wh;~NS5rp9zEF*2Bht6k8A95c1bJ|BRFvjNk#V7(~<Des(Yx!YVQ;$Q","#)olRsLlQqQJemyv0yLop<v@>92h!mi~s)A~U0",
                                    "^>n;=JnFm_1|=UE","R#:$L9T5)OkHZ>~L7}i6+","flf@xd9)$az&@`#VMf85J8**!U<&`$$@(QtreFZ9@@uuSmMB)}S*@HHC",
                                    "7q!n}wKe","uV69E5!pCN;d^32>&B]nTVDEVO1}FVz]o9sk(9J{j|Se7@l&I-5vYAbB0o>","N{o1}h}C3E=|34[chE&RwY+;O_002b}oU5314GI~y[kfgNaqoFbj[G{~4]R!O",
                                    "Xs9$JW%<huUw6}Y7Q=Xr@U;Fxdt>8#x-se*x03e_qW}dmyYO`n","(Jmf}=bmjtwZsj90k1WtCV24V_dIiYg5;)j","JSdu38uqZ8%B#0!ZzZ0B0Sf@E",
                                    ":SU@:Cv2Iqi<w@A0ZFZ5OecZ-Z:~f&<i<I0g+1!ftB!h3Uy%`:kVg1;>rhy#O","A]}SLgK3Gsc+soW{&q$TR}1RUZ~@vM)VP1t|;LZY4T2Gyo+9hWLb=",
                                    "6{X|+25iCy","=^=2_vF*-{E;x","XM~Uh_wIzB{;e~+MhC52w7h*Pmgy|vu;K+%Q>5cCh2dsXZh!~LeMd","|k1cD=mLMk<9>]VYoO}2vdX","ZTuW{^Fa-&MRTPvf}GK4*>IY4KF67(t#$S$o",
                                    "FfEbtukef0W5Cjt*IqDM%u<_[Mm4$x%n29|kKL<P<y$5==G@[P*F-DOK5__gF",")8$cwYvIs9M*n<ut87`a`>!mOg`vBx5oNc{","nw[)yaGM{:{--2MOFkC)S_P=L[D<YYIMvju`!d`C#|4u[nYi+f",
                                    "buk>a2-","~m~OYa3n^[!4zVpL<:SD>gUP:_u#p","jVaB","v$iD[q_oNCk{j>_j*_YV]`O_q3lvV{pngQUr|Vvb~n~qf|","^nf_N!#S5i7ka!V<Uj6TDz1Z_Y-i>%RL-+(c5B1m^$;3;4[X8kt{<X]oF;14)]@",
                                    "}i1^w7TXoQ5K*(","gn*4bv]Tev08NE[Rk:m#o(#","1&#@eC1;N;$I",";XUmU-aotMR8A!XVG7q:d:]df#M:_)CNN`]iB}Z[tMD%fZJK5B8LWg0`RX","zDjPy=t^<n0m[an={{Y+#MX:Lwn^:()y(~[^_8]",
                                    "nM!rQN3IR&bw]h{mBh~1q7{f-B-rrt1*C7MEztJFaNr;PK>","u9b4A+9Ho=aj","Ku^5G","l$","U>n@<x0c=_=aQpPufhYj:8rx{}x`;4h]nR5qZ","8D(hx7`+VYh^+hd<*ZNQlNnDBJ4M=qd_<TJOpXnS",
                                    "GYZT|07k3^5wSk7K~rQB)hIS5@[%$mN-$A)<pz6b#7mL]$9lE","5r3DAr0P)|}B!$^Hcc$3kR9m:m}Op)d=4%7Ne_]","1ne","[HRK5nB`KSl*nO1vu!BV;mp","7lc&59*hmu(d@^ofN6zIILKp",
                                    "wb-wM2)RmydqSEJ*eT-m<hKtdZzI9tV4OWi7+9-}5AlT7g9<yE{H`%@)g","&","#gfJ[buOsL0jYmUd2uDY1d%(G+8-","L|c86|UavP#|>4kXt3PY2mK8jz(]1adK|e4fzzQqZez*v}~RW{kQW)r",
                                    "kOlN$LCW8{5tTATdkUK~niZV(Y(4:i:iydy}z7Q&WY7v*N_eOC75","T[v`z6","Uf1M%;dmfi","M~M51(&9IfDHy[[","U4%bi=zzn:w-V^bQ9<h)hsdmpfRVc+}zD)3Abdt|c~[1tp{$vDftJ82!:0v5):",
                                    "dIU7{o3~Ilr#en*I","ojW;R3!rDNt+Cwen:VIVxc!A!sS(V_]K`F00FVd#T6R]6","3&-ywOU+YhTJ*AG3Ra@","r{kZ^W","B1iDNk_hHujqmU;xMk0JRkBcB8Wz7W5`(ox`","8>_~Efe|c)",
                                    "G)-@vlY=4J+ED7V6X}2bvL03l=IFi|#)^2h343","WR(mPz&7LS2y>mU&[bR$!vmrz+WBtYj:>Z)mXQhkJz^r1t|>K","N{h-G(DUWVO)ASP|%%~n:c$XEeqIU-`y__$#","%zuev=K8MG&NB6XW6]D{ok6&y53f`",
                                    "_>Mb0^aCGcmeWxT(&wqf&&ZKV>j%H^_s2%u>W+JTD@H>r","E^gnwBth5ifAlKdp;IYF+Y$v(}HGV{U$p;XyOd","=9x-~yC&^r+feB(p!^WUfy5xex00v1m;o@l!s!59<4W6X$`zT^i*)cH[xQB",
                                    "z3k$LmM>!VoEcJBr4d!=cSu^7tQq=2wxhte+jC|afs","f1Y;[|n&kZ+$ZpVnI[~Zd-!3Km->Nol"
                                };
    static String[] passwords =    {
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
                                    "tip","doubt",
                                    "3Vf|:YMCKBqgP(9q>DRxS3GX]@AgoEBh5jzD]Mg>Vl","TN@|j+E;#^YI@khG!Pzuj`n7L]G2[kDAQ~i#E1#","[wro[>&LiV{uvv=<sc7gi|bAdYrq|m3kRP*umm6c2tq6qe2C~mLJ^dhI9>*",
                                    ">amQA7yI^jo7FACq@hC^Q1$Oyvh`Hbf-8J_%`t-J*0SqV+hy*2HE+V4x]=}dRa","KFd4R8O|kp$xp_-D_i$:)DZOC>8JDrgDOmaq-6%@j8q9$7B)]62jk`4Dg","0zApGWGFw;kY63!rymmFk^3L4a}22_R2s",
                                    "Bs+n2eYx7Th@!$[~`J{xpVyFcuXLJ)h(>]","","oqy1iiiaq$b4z!vcbakOt:)~%i{]iuCa","d^(s]el1lkJULAdff&p!N%;itwP+9AoCH1Q!ri3~[|(YVkrnHK~KcDm6*To:",
                                    "at4%Ynrn(J~UM0+ap{D6LGP","zh#0","YS;yMy#i2>(C0u1<*%s;`8(H~y#3z3&MH4w{yrqr=","!V:KZT}z*~5q2H$7&&BF!y}C2ngPj|!ebMC@LoW[pBB8T3N@^C57km{",
                                    "8NKijdAp(DjMYE(ny>GMG5X3-","[:1#X-DEFl+J((g4",")gQ&2:7!*SM>oFt`uNB0l1u<S9^EKmU","xg*S=GXwG:}U`Ns=y(_:Z#!PX%]jCeP$FyKlQ+",
                                    "bzGidovvkD&4k(a47M`}`QQZyOGfK5s`:xLB+VLKbV","it*[)N%s-J","k;lB25f","@KMaDj[E&#eK(CBr}hio@2d>","$hF6s]y:SJ>[zt:pD^<gGSHITp(rNp`(~EM6MnyIUCGFI}aC!s2n8UxXWzs^",
                                    "E1g}XH&Y!3=^##W%7:h0Bh6yKZ=juAA|DbHI!pAu&pKfa^","e%ppH(k^R_=%l~wc5OUJcZZO&ye","jh1J)S;4Ea{wb>[q8ZWuEsu^lE(=<&diuIGZmNx&1gw8",
                                    "i{z`{hpO`Ykd3jMJ1~}lq]JfC*xjkad]t2]PIE3`GLiSXbMg[~^<]","","fwbUY^)}iYW>)","kh(>cQo+U{`f1^AY3fr&kh<vH>NehlOx!zP;}Eq:n|",
                                    "PV~|Oy+WT(=s[X`y8vG);UntC8@;ZWMaXcv*F$5mi_^)B1WUsKt{S(*Fb7","U*2JK8]jAgI6G2ExOER#C!fir+_LV3R{ISR^7","","n}d(tQla","xax8bSp%r-q+@Wyrs8%oTHh_j]{c&4+MOmyCFg5%",
                                    "=[FJ%iXbZ2*YRZhGP0]3eI8v|B1b7TIC26i-0S+atDY3aP-(1R!f:","s5aMkww7qY4r$YC92|&%m*OED[4","J%A#9>(4Icw)8sm=!-aHaL)$t0([)d4THfrb`m5H7me3]Bl]|6q",
                                    "6NWn}=_KRh$W_`Azu@WH[X$Y)Q;Iwx%v3*fu4aD","OK0iOe2K![fEH=23-Sq_GLi;$9B","dx`3~BLSCo(<]zo%&@q8b`SedPX=GH^18BWUwIX&lI%X[a]uw%8Z","0L$D3kA{B7{7t+4SU",
                                    "O!g{E{SDQ5cEb<7lO%V-j5nv;<6SI2Ds2OYMJSp","}Jko9Wg27%S2","(yT7j","5]_E@R","Qxn29>yB]49G4<zW]JTIQ=v%%}*>u@x:1w#|>Rb<yP^BM","]7=K-e(1e(","g4<Taj6|C|%I3m;O*:uvOOWtklz$H8e",
                                    "9upraopgaorpog","}18e","b)C|Yze^;W$Mn$@9T2^v","l=K|qp=jOGX","(ZWb_<onxj&","++;XfbA%2!<3pvHy-LQkY;gA","C4^Y0FF^=^M~!9`Do#{BIQE{gzPEH~^|:{;iOt+0xRb","9G6y)bWxh",
                                    "dlD-!;Ond6Xq>lamyl]}1^H|w","hLH!hU","|~9RzDC0E(Srx3gnK#w*<KXt","|MtVL~xdA<AphhOw-s&d2jeb7UA4$59Om2{lGlg${(","sf>0!(3BBibn+`r{2QH=X9D%Ghhh5@jD`",
                                    "Ox=Lw6<$d`AY|OfAPExhger7xtu_lc3WIoG&zrJ_beng5}~h","SDYl","sMRm5_CJcuWO>cW@B1v}Fi3C|g(2*","ZFKgXk@uJkzigY3F`M[Y|Jn(o[71hQ","ckMD6I<Vi[&x%s(G}4k_pD=xDg2y)*Hvy:",
                                    "rvl=|{JXDZ[GH1-GDf:6L)PXH%1IP4vj+5<*3juB7CoF@t&Z","bC&xauzh<R80qi%Bt+=ECidIa3t","BhPKHSQxx@X2g&",")YH@@5CS|c9vI@u)","O#jJxV#0<zh4mrNGO2(","+043jy0w34ynhoenhesnhrsp",
                                    "bh<A+9vV*KmCgaCMt+q&cf!X;k;",">w}im%axf2R7gL#:j","`35|]<xmBfelnE9d8P&x","7FU]A}w[~$V2BHn`uK)c8%uwm3rhX>Q(F7H{tw#kZzag02zVngLDROAA24`uWZJ",
                                    "qDiLqP$}NYtN~&V`TEcCj`2h<(GlJV&-v*$L5r`1#!4w~F`tH*TJ4C","8zWhnTCh}aq#vC","(A([`GHe=r6II]dN","@LN:R<0fw2KQYT","Z`%2wRUvauX<ZqVo3!HG^[w`U3_l++)o>JYTM*",
                                    "I~NH##%SJ3BT~t+$Wo@0*Tt","5CKcV*Xk2lbC)o*OXq0:_b`x|6dOl1*[}~1dF5c#oeT0$kBeFQ!&@^[D7$TJQrV4",">ietdT>Mv`TI`*O@GnsP&326{3^[h$=@|:=l<Q[fC7Eq7pI*6z2UqX]dvPe4",
                                    "<1_f@VdTzF_~8Hl>Y$-^n|@*oLdU71]Y]jxYPXFrYA4K1Wy",")P0^&a$","[CRp)lug5x#DP^z+yPa:244mbYwU!A;oUxzxw}HMh)!1+a0{1KR={j6}:NcVX:3S",
                                    ":2LsG~u@(>AJ@Bz7]CG(>&IDJroKtVZVV[$k+@rcsIvmuQ`w","d)|N5MYqO!aVZdy8trL34bD%n%Zt8RJB3EeJp4n<1A8Ik(0Pbric|PP","PjUd70WH23","Cm]2$L6%egwGp8Nq:T_02%pc_+6>VC",
                                    "J7a3Dk{%r","@doKY7|dvWVwtH[Lj4r1MY=J5iui|9;h","BKLHm%QEcX_L+)3hY89VYTM){WNYiy-w`aaLS-m_bJ@yWIZl7OQ:x%Y&","frl1*",":0f_LOgUddm~kIz","173pXh}Qa-!d-@%B5%Uzg$)$K]62RTAw8SU3A"
                                };
    
    private static boolean isStable(ArrayList<Boolean> status){
        for(Boolean s: status){
            if(s == false)
                return false;
        }
        return true;
    }
    
    private static long hash256(String[] val){
        long start = new Date().getTime();
        for(String u : val)
            hs.getSHA256(u);
        System.gc();
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
        for(String u : uname)
            for(String p : pw)
                status.add(hs.getPasswordHash(u,p).equals(hs.getDecryptedPass(hs.getEncryptedPass(u,p))));
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
    
    private static void cycleTest(){
        System.out.println("<<< HASHCRYPT TEST >>>");
        
        
        int iteration = (usernames.length*passwords.length);
        System.out.println("Usernames Size: " + usernames.length);
        System.out.println("Passwords Size: " + passwords.length);
        System.out.println("Iteration Size: " + iteration);
        System.out.println("============================================");
        
        long[] times = new long[4];
        times[0] = hash256(usernames);
        times[1] = hash384(usernames);
        times[2] = passwordTest(usernames, passwords);
        times[3] = sessionTest(usernames);
        System.out.println("============================================");
        
        System.out.println("Hash256: " + times[0] + "ms");
        System.out.println("Hash384: " + times[1] + "ms");
        System.out.println("Account Encryption: " + ((double)times[2]/1000) + "s");
        System.out.println("Session Encryption: " + ((double)times[3]/1000) + "s");
        System.out.println("Account Encryption: " + ((double)times[2]/(double)iteration) + "ms per call");
        System.out.println("Session Encryption: " + ((double)times[3]/(double)iteration) + "ms per call");
        
        times = null;
        System.gc();
    }
    
    public static void main(String[] args){
        hs = new HashCrypt();
        String db = "jdbc:sqlite:" + "database.db";
        String encrypted = hs.getGenericEncryption(db);
        System.out.println(encrypted);
        System.out.println(hs.getGenericDecryption(encrypted));
        /*
        {
            int cycles = 20;
            
            long start = new Date().getTime();
            for(int i = 0; i < cycles; i++){
                System.out.println("TestID: " + i);
                cycleTest();
                System.gc();
                System.out.println("");
            }
            System.out.println("Cycle Test Completed: " + ((new Date().getTime()-start)/1000) + "s");

            cycles = 0;
            start = 0;
            System.gc();
            System.exit(0);
        }
        */
    }
}
