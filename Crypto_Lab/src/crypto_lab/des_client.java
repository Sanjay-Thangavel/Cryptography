import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class des_client {
    static String char_bin(char t){
        String ans="";
        int val=(int)t;
        //System.out.println(val);
        while(val!=0){
            int d=val%2;
            ans+=(char)(d+'0');
            val/=2;
        }
        String a="";
        for(int i=ans.length()-1;i>=0;i--){
            a+=ans.charAt(i);
        }
        while(a.length()!=8){
            a=('0'+a);
        }
        //System.out.println(a);
        return a;
    }
    public static void main(String[] args) throws Exception{
        Scanner inp= new Scanner(System.in);
        
        Socket s=new Socket("localhost",9000);
        System.out.println("Client started!");
        PrintWriter pr=new PrintWriter(s.getOutputStream(),true);
        BufferedReader bw=new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader bi=new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Enter the string: ");
        String plaintext= inp.nextLine();
        System.out.println("Enter the key: ");
        String key= inp.nextLine();

        System.out.println("The plaintext is: "+ plaintext);
        System.out.println("the key is: "+ key);

        int n= plaintext.length();
        
        // StringBuilder bpt= new StringBuilder();
        // StringBuilder bkey= new StringBuilder();
        // for(int i= 0; i<n; i++) bpt.append(char_bin(plaintext.charAt(i)));
        // for(int i= 0; i<n; i++) bkey.append(char_bin(key.charAt(i)));

        String bpt="";
        for(int i=0;i<plaintext.length();i++){
            String binaryChar= char_bin(plaintext.charAt(i));
            // System.out.println("The corresponding binary char is: "+ binaryChar);
            bpt= bpt+ binaryChar;
        }

        System.out.println("The size of bpt: "+ bpt.length());

        String bkey="";
        for(int i=0;i<key.length();i++){
           bkey+=char_bin(key.charAt(i));
        }

        String ipt= permute(bpt.toString(), IP);
        String kpt= permute(bkey.toString(), K56);
        generate_key(kpt);
        String enc=encrypt(ipt,kpt);
         enc=permute(enc,InP);
         String chk=enc;
         enc=bin_string(enc);
         System.out.println("Encrypted: "+enc);   
         
        pr.println(chk);
          pr.println(key);

    }

    static String encrypt(String pt, String key){
        String L= pt.substring(0, 32);
        String R= pt.substring(32);

        for(int i= 0; i<16; i++){
            String k= keys[i];
            String ER= permute(R, EP);
            ER= xorfun(ER, k);
            ER= useSbox(ER);
            // System.out.println(ER);
            ER= permute(ER, P32);
            String xored= xorfun(L, ER);
            L=R;
            R= xored;
            System.out.println("Round "+ (i+1)+": L="+ L+ " R="+ R);
        }
        return R+L;
    }


    static String useSbox(String E){
        StringBuilder ans= new StringBuilder();
        int s= 0;
        int e= 6;

        for(int i= 0; i<8; i++){
            String b= E.substring(s, e);
            int row= Integer.parseInt(""+ b.charAt(0) + b.charAt(5), 2);
            int col= Integer.parseInt(""+ b.charAt(1) + b.charAt(5), 2);

            int st= S[i][row][col]; 
            String sb= Integer.toBinaryString(st);

            while(sb.length()<4) sb= "0"+sb;
            ans.append(sb);
            s+= 6;
            e+= 6;
        }
        return ans.toString();
    }

    static int bin_dec(String k){
        int ans=0;
        int p=0;
        for(int i=k.length()-1;i>=0;i--){
            if(k.charAt(i)=='1'){
            ans+=Math.pow(2,p);}
            p++;
        }
        //System.out.println("K: "+k+ "Dec: "+ans);
        return ans;
    }

    static String bin_string(String b){
        String ans="";
        for(int i= 0; i<b.length(); i+= 8){
            int s= bin_dec(b.substring(i, i+8));
            ans+= (char)s;
        }
        return ans;
    }

    static String xorfun(String a, String b){
        String ans="";
        for(int i= 0; i<a.length(); i++){
            if(a.charAt(i)== b.charAt(i)) ans+= '0';
            else ans+= '1';
        }
        return ans;
    }
    static String keys[]=new String[16];
    static void generate_key(String key){
        String c= key.substring(0, 28);
        String d= key.substring(28);
        
        for(int i= 0; i<16; i++){
            c= c.substring(keyshifts[i]) + c.substring(0, keyshifts[i]);
            d= d.substring(keyshifts[i]) + d.substring(0, keyshifts[i]);

            String form_key= c+d;
            form_key= permute(form_key, P48);
            keys[i]= form_key;
        }
    }

    static String permute(String a, int[] b){
        String ans="";
        for(int i= 0; i<b.length; i++) ans+= a.charAt(b[i]-1);
        return ans;
    }

    static int[] keyshifts = {
        1, 1, 2, 2, 2, 2, 2, 2,
        1, 2, 2, 2, 2, 2, 2, 1
    };
        static int[][][] S = {
        {
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
        },
        {
            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
        },
        {
            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
        },
        {
            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
        },
        {
            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
        },
        {
            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
        },
        {
            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
        },
        {
            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
        }
    };
        static int IP[]={
             58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7};  

        static int IK[]={
            1,2,3,4,5,6,7,
            9,10,11,12,13,14,15,
            17,18,19,20,21,22,23,
            25,26,27,28,29,30,31,
            33,34,35,36,37,38,39,
            41,42,43,44,45,46,47,
            49,50,51,52,53,54,55,
            57,58,59,60,61,62,63
        };
        static int K56[]={
        57, 49, 41, 33, 25, 17, 9,
        1, 58, 50, 42, 34, 26, 18,
        10, 2, 59, 51, 43, 35, 27,
        19, 11, 3, 60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7, 62, 54, 46, 38, 30, 22,
        14, 6, 61, 53, 45, 37, 29,
        21, 13, 5, 28, 20, 12, 4
        };
        static int [] InP={
            40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
        };    
         static int [] EP={
             32, 1, 2, 3, 4, 5,
        4, 5, 6, 7, 8, 9,
        8, 9, 10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1
         };
         static int [] P48={
             14, 17, 11, 24, 1, 5,
        3, 28, 15, 6, 21, 10,
        23, 19, 12, 4, 26, 8,
        16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32
         };
         static int [] P32={
        16, 7, 20, 21, 29, 12, 28, 17,
        1, 15, 23, 26,5, 18, 31, 10,
        2, 8, 24, 14,32, 27, 3, 9,
        19, 13, 30, 6,22, 11, 4, 25
         };
}
