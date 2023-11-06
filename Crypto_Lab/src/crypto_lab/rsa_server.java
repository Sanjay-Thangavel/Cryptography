/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crypto_lab;

import java.util.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class rsa_server {
    public static int gcd(int a, int b){
        if(b== 0) return a;
        return gcd(b, a%b);
    }

    public static int find_d(int e, int phi){
        for(int i= 0; i<e*phi; i++){
            if(e*i % phi == 1) return i;
        }
        return -1;
    }

    public static int decrypt(int enc, int N, int d, int phi){
        
        System.out.println("The calculated d is: "+ d);
        int dec= (int)Math.pow(enc, d);
        dec= dec%N;
        return dec;
    }
    public static void main(String[] args) throws Exception{
        Scanner inp= new Scanner(System.in);
        ServerSocket ss= new ServerSocket(8888);
        Socket s= ss.accept();
        System.out.println("[INFO] : Client connected");
        PrintWriter pr= new PrintWriter(s.getOutputStream(), true);
        BufferedReader br= new BufferedReader(new InputStreamReader(s.getInputStream()));
        // System.out.println("Enter tht value of p: ");
        int p= Integer.parseInt(br.readLine());
        int q= Integer.parseInt(br.readLine());
        int e= Integer.parseInt(br.readLine());
        int enc= Integer.parseInt(br.readLine());

        int N= p*q;
        int phi= (p-1)*(q-1);
        int d= find_d(e, phi);
        int dec= decrypt(enc, N, d, phi);
        System.out.println("The decrypted text is: "+ dec);

    }    
}
