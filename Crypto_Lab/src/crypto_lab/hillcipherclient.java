import java.io.*;
import java.net.*;
import java.util.*;
public class hillcipherclient {
    static int km[][];
    static int kl=0;
      public static int[] linematrix(String k){
        int lm[]=new int[k.length()];
        int m=0;
        for(int i=0;i<k.length();i++){           
                lm[i]=k.charAt(i)-97;         
        }
        return lm;  }
    public static void keymatrix(String k,int kl){
        km=new int[kl][kl];
        int m=0;
        for(int i=0;i<kl;i++){
            for(int j=0;j<kl;j++){
                km[i][j]=k.charAt(m)-97;
                m++;  }}}
    public static int determinant(int a[][],int n){
        switch(n){
            case 1: return a[0][0];
            case 2: return a[0][0]*a[1][1]-a[0][1]*a[1][0];
            default:
              int deter=0;
              for(int i=0;i<n;i++){
                  int b[][]=new int[n][n];
                  int k=0;
                  for(int p=1;p<n;p++){
                      for(int q=0;q<n;q++){
                          b[p][q]=0;
                          if(q!=i){
                              b[p][q]=a[p][k];
                              k++; }}}
                  deter+=Math.pow(-1.0, (double)i)*a[0][i]*determinant(b,n-1); }              
              return deter; }}
    public static boolean check(String k){
        kl=k.length();
        int klm=(int)Math.sqrt(k.length());
        keymatrix(k,klm);
        int d=determinant(km,klm);
        System.out.println(d);
        if(d%26==0) return false;
        if(d%2==0 || d%13==0) return false;
        return true; }
    public static String multiplykey(int l[]){
        int b[]=new int[l.length];
        String ans="";
        int n=l.length;
        for(int i=0;i<n;i++)
        {   for(int j=0;j<n;j++){
                int p=km[i][j]*l[j];
                b[i]=(b[i]+p)%26; }}
        for(int i=0;i<l.length;i++){
            ans+=(char)(b[i]+97);}
        return ans;}
    public static String encrypt(String l,int p){
        int n=l.length();
        String enp="";
        while(l.length()>p){
            String l1=l.substring(0,p);
            l=l.substring(p,l.length());
            int lm[]=linematrix(l1);
            enp+=multiplykey(lm); }
        if(l.length()==p){
            int lm[]=linematrix(l);
            enp+=multiplykey(lm); }
        else if(l.length()<p){
            while(l.length()==p){
                l+='x';}
            int lm[]=linematrix(l);
            enp+=multiplykey(lm);   
        }
        return enp;  }
    public static void main(String str[]) throws IOException{
        Socket st=new Socket("localhost",9000);
        PrintWriter pw=new PrintWriter(st.getOutputStream(),true);
        BufferedReader br=new BufferedReader(new InputStreamReader(st.getInputStream()));
        String line;
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the line: ");
        line=sc.nextLine();
        System.out.println("Enter the key: ");
        String key=sc.nextLine();
        double p=Math.sqrt(key.length());
        System.out.println(p);
         if (p!= (long) p){
            System.out.println("Cannot Form a square matrix");
            return;}
        if(check(key)){
            String ans=encrypt(line ,(int)p);
            System.out.println("Encrypted: "+ans);
             pw.println(ans);
             pw.println(key);
        }    }}
