import java.net.*;
import java.io.*;
import java.util.*;
public class transposition_server {
     public static void main(String str[]) throws IOException {
        ServerSocket ss = new ServerSocket(9000);
        Socket s = ss.accept();
        System.out.println("[INFO] : Client connected");
        PrintWriter pr=new PrintWriter(s.getOutputStream(),true);    
       
        BufferedReader bw=new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader bi=new BufferedReader(new InputStreamReader(System.in));
        Scanner sc=new Scanner(System.in);
        String cipher=bw.readLine();
        String key=bw.readLine();
        String ak[]=key.split("-",-1);
        //System.
        String ps[]=new String[ak.length];

        int l=cipher.length()/ak.length;
        for(int i=0,j=0;i<cipher.length();i+=l,j++){
            ps[j]=cipher.substring(i, i+l);
            //System.out.println(ps[j]);
        }
      //  System.out.println("came");
        String pt="";
         
        for(int i=0;i<l;i++){
          for(int j=0;j<ak.length;j++){
              int up=Integer.parseInt(ak[j])-1;
            //  System.out.println(up+" "+ps[up]);
              pt+=ps[up].charAt(i);
          }            
        }
        System.out.println("PT: "+pt);
     }
     
}
