/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crypto_lab;
import java.util.*;
import java.io.PrintWriter;
import java.net.*;

public class rsa_client {
    public static int gcd(int a, int b){
        if(b== 0) return a;
        return gcd(b, a%b);
    }

    public static int find_e(int phi){
        for(int e= 2; e<phi; e++){
            if(gcd(phi, e)== 1) return e;
        }
        return -1;
    }

    public static int encrypt(int m, int N, int e, int phi){
        
        System.out.println("The chosen e is: "+ e);
        int c= (int)Math.pow(m, e);
        c= c%N;
        return c;
    }
    public static void main(String[] args) throws Exception{
        Scanner inp= new Scanner(System.in);
        Socket s= new Socket("localhost", 8888);
        PrintWriter pr= new PrintWriter(s.getOutputStream(), true);
        System.out.println("Enter tht value of p: ");
        int p= inp.nextInt();
        System.out.println("Enter the value of q: ");
        int q= inp.nextInt();
        System.out.println("Enter the plain text: ");
        int m= inp.nextInt();
        int N= p*q;
        int phi= (p-1)*(q-1);
        int e= find_e(phi);
        int enc= encrypt(m, N, e, phi);
        System.out.println("The encrypted text is: "+ enc);
        pr.println(p);
        pr.println(q);
        pr.println(e);
        pr.println(enc);
    }    
}
