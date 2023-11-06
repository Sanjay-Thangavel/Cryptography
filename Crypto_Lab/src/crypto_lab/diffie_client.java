import java.util.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class diffie_client {
    public static void main(String[] args) throws Exception{
        Scanner inp= new Scanner(System.in);
        Socket s= new Socket("localhost", 9090);
        PrintWriter pr= new PrintWriter(s.getOutputStream(),true);
        BufferedReader br= new BufferedReader(new InputStreamReader(s.getInputStream()));
        System.out.println("Enter value of alpha and q: ");
        int alpha= inp.nextInt();
        int q= inp.nextInt();
        System.out.println("Choose Xa< q: ");
        int xa= inp.nextInt();
        int ya= (int)(Math.pow(alpha, xa)) % q;
        pr.println(alpha);
        pr.println(q);
        pr.println(ya);
        int yb= Integer.parseInt(br.readLine());
        int key= (int)(Math.pow(yb, xa)) % q;
        System.out.println("The key is: "+ key);
    }
}
