// package transposition_cipher;
import java.util.*;
import java.io.*;
import java.net.*;

public class transposition_client {
    public static void main(String[] args) throws Exception{
        Socket s=new Socket("localhost",9000);
        System.out.println("Client started!");
        PrintWriter pr=new PrintWriter(s.getOutputStream(),true);
        BufferedReader bw=new BufferedReader(new InputStreamReader(s.getInputStream()));
        // BufferedReader bi=new BufferedReader(new InputStreamReader(System.in));
        Scanner inp=new Scanner(System.in);

        System.out.println("Enter the Plaintext: ");
        String pt=inp.next();
        int pn=pt.length();
        System.out.println("Enter the key size: ");
        int k=inp.nextInt();
        System.out.println("Enter the key: ");
        String key=inp.next(); 

        String p[]= key.split("-",-1);      
        String as[]=new String[k];

        while(pt.length() % k!= 0) pt+= "x";
        for(int i=0;i<k;i++){
            int up=Integer.parseInt(p[i]);
            as[up-1]="";
            for(int j=i;j<pt.length();j+=k){
                as[up-1]=as[up-1]+pt.charAt(j);
            }    
          
        }
        StringBuilder cipher= new StringBuilder();
        for(int i= 0; i<k; i++){
            cipher.append(as[i]);
        }
        System.out.println("The ciphertext is: "+ cipher.toString());

        pr.println(cipher.toString());
        pr.println(key);  
    }
}
