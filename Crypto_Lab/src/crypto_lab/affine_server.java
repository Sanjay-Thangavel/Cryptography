import java.util.*;
import java.io.DataInputStream;
import java.net.*;

public class affine_server {
    public static int a_inverse(int a){
        for(int i= 0; i<26; i++){
            if((a*i)%26== 1) return i;
        }
        return -1;
    }
    public static String decrypt(String cipher, int a, int b){
        StringBuilder plain= new StringBuilder();
        for(int i= 0; i<cipher.length(); i++){
            int ch= cipher.charAt(i)-'a';
            ch= a_inverse(a)*(ch-b)%26;
            if(ch<0) ch+= 26;
            plain.append((char)(ch+'a'));
        }
        return plain.toString();
    }
    public static void main(String[] args) throws Exception{
        Scanner inp= new Scanner(System.in);
        ServerSocket ss= new ServerSocket(9999);
        Socket s= ss.accept();
        DataInputStream din= new DataInputStream(s.getInputStream());
        int a= din.readInt();
        int b= din.readInt();
        String cipher= din.readUTF();
        System.out.println("the recived encrypted msg: "+ cipher);
        System.out.println("The decrypted msg is: "+ decrypt(cipher, a, b));
    }
}
// 17 20