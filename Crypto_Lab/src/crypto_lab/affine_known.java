package crypt_attacks;

import java.util.Scanner;

public class affine_known {
    public static boolean check_pair(String c,String p,int a,int b){
        for(int i=0;i<p.length();i++){
            int pv=p.charAt(i)-97;
            int v=(pv*a+b)%26;
            char cv=(char)(v+97);
            if(c.charAt(i)!=cv) return false;
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the Plaintext:");
        String p=sc.nextLine();
        System.out.println("Enter the Ciphertext:");
        String c=sc.nextLine();
        int pl=p.length();
        int cl=c.length();
        if(pl!=cl) {
            System.out.println("Length not matching");
            return;
        }
        int a[]={1,3,5,7,9,11,15,17,19,21,23,25};
        for(int i=0;i<12;i++){
            for(int j=1;j<=26;j++){
                if(check_pair(c,p,i,j)){
                    System.out.println("Keys found: a="+i+" b="+j);
                }
            }
        }
    }
}
