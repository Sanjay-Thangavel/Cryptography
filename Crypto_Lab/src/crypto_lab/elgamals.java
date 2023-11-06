package crypto_lab;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package des;

import java.util.*;
import java.io.*;
import java.net.*;
public class  elgamals  {
    public static int power(int a, int b, int q) {
        int ans = 1;
        for (int i = 0; i < b; i++) {
            ans = (ans * a) % q; // Use modular multiplication
        }
        return ans;
    }
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(9000);
        Socket s = ss.accept();
        PrintWriter pr = new PrintWriter(s.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        Scanner inp = new Scanner(System.in);
        int alpha = Integer.parseInt(br.readLine());
        int q = Integer.parseInt(br.readLine());
        int ya = Integer.parseInt(br.readLine());
       System.out.println("Enter the plaintext msg: (0 <= M <= q-1)");
        int m = inp.nextInt();
        System.out.println("Enter value for k: (0 to " + (q - 1) + ")");
        int k = inp.nextInt();
        int K = power(ya, k, q);
        System.out.println("K:" + K);
        int c1 = power(alpha, k, q);
        System.out.println("c1:" + c1);
        int c2 = (K * m) % q;
        pr.println(c1);
        pr.println(c2);
        inp.close();
        pr.close();
        br.close();
        s.close();
        ss.close();
    }
}

//7 23 