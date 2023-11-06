import java.io.*;
import java.net.*;
import java.util.*;
import java.math.*;
        
public class hillcipherserver {
    static int km[][];
    static int fac[][];
    static int invdet=0;
    public static void keymatrix(String p,int l){
        km=new int[l][l];
        int h=0;
        for(int i=0;i<l;i++){
            for(int j=0;j<l;j++){
                km[i][j]=p.charAt(h)-97;
                h++;}}}
    public static int determinant(int a[][],int l){
        switch (l){
            case 1: return a[0][0];
            case 2: return a[0][0]*a[1][1]-a[1][0]*a[0][1];
            default:{
            int deter=0;
            for(int i=0;i<l;i++){
                int b[][]=new int[l][l];
                int m=0;
                 for(int j=1;j<l;j++){                     
                    for(int k=0;k<l;k++){
                        b[j][k]=0;
                        if(k!=i){
                            b[j][k]=a[j][m];
                            m++; }}}
                 deter+=Math.pow(-1.0,(double)i)*a[0][i]*determinant(b,l-1);}
            return deter;
        }}}
    public static int modinv(int d){
        System.out.println(d);
        BigInteger k=BigInteger.valueOf(d);
        BigInteger p=BigInteger.valueOf(26);
        return k.modInverse(p).intValue();}
    public static boolean check(String key,int kl){
        keymatrix(key,kl);
        int d=determinant(km,kl);
         invdet=modinv(d%26);
        d%=26;      
        if(invdet<0) invdet+=26;
        if(d==0) return false;
        if(d%2==0 || d%13==0) return false;
        return true;}
    public static void transpose(int f[][],int l){
        int b[][]=new int[l][l];
        for(int i=0;i<l;i++){
            for(int j=0;j<l;j++){
                if(f[j][i]<0){
                    f[j][i]+=26;  
                }
                b[i][j]=invdet*f[j][i];
                b[i][j]%=26;}}
        fac=b;}
    public static void cofactor(int k[][],int l){
        fac=new int [l][l];
        int b[][]=new int[l][l];
        for(int i=0;i<l;i++){
            for(int j=0;j<l;j++){
                int n=0;
                int m=0;
                for(int p=0;p<l;p++){
                    for(int q=0;q<l;q++){
                        b[p][q]=0;
                        if(i!=p && j!=q){
                           b[m][n]=k[p][q];
                        if(n<(l-2)){
                            n++;
                        }
                        else{
                            m++;
                            n=0;}}}}
                fac[i][j]+=Math.pow(-1.0,(double)i+j)*determinant(b,l-1);}}
        transpose(fac,l);}
    public static int[] linematrix(String line,int l){
        int lm[]=new int [l];
        for(int i=0;i<l;i++){
            lm[i]=(int)(line.charAt(i)-97);
        }
        return lm; }
    public static String  multiplyinv(int f[][],int l[]){
        int be[]=new int [l.length];
        for(int i=0;i<l.length;i++){
            for(int j=0;j<l.length;j++){
                be[i]+=f[i][j]*l[j];               
            }
            be[i]%=26;
        }
        String a="";
        for(int i=0;i<l.length;i++){
            a+=(char)(be[i]+97);
        }
        return a;
    }
    public static String decrypt(String l,int le){
        String ans="";
        while(l.length()>le){
            String p=l.substring(0,le);
            l=l.substring(le,l.length());
            int lm[]=linematrix(p,le);
            ans+=multiplyinv(fac,lm);
        }
        if(l.length()==le){
             int lm[]=linematrix(l,le);
            ans+=multiplyinv(fac,lm);
        }
        else if(l.length()<le){
            while(l.length()==le){
                l+='x';
            }
              int lm[]=linematrix(l,le);
            ans+=multiplyinv(fac,lm);
        }
        return ans;
    }
    public static void main(String str[]) throws IOException
    {
        ServerSocket ss=new ServerSocket(9000);
        Socket st=ss.accept();
        PrintWriter pw=new PrintWriter(st.getOutputStream(),true);
        BufferedReader br=new BufferedReader(new InputStreamReader(st.getInputStream()));
        String c=br.readLine();
        String key=br.readLine();
        double pk=Math.sqrt(key.length());
        if(pk !=(long)pk) return;
        if(check(key,(int)pk)){
            cofactor(km,(int)pk);
            System.out.println("Decrypted: "+decrypt(c,(int)pk));
        } }}
