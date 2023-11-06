
import java.util.*;
import java.io.DataOutputStream;
import java.net.*;

public class caesar_client{
    public static String encrypt(String plain, int key){
        StringBuilder cipher= new StringBuilder();
            for(int i= 0; i<plain.length(); i++){
                int ch= plain.charAt(i)-'a';
                ch= (ch+key)%26;
                cipher.append((char)(ch+'a'));
            }
        
            return cipher.toString();
    }
    public static void main(String[] args) throws Exception{
        Scanner inp= new Scanner(System.in);
        Socket s= new Socket("localhost", 9999);
        DataOutputStream dout= new DataOutputStream(s.getOutputStream());
        System.out.println("Enter the plaintext: ");
        String plain= inp.nextLine();
        System.out.println("Enter the key: ");
        int key= inp.nextInt();

        dout.writeInt(key);
        dout.writeUTF(encrypt(plain, key));
        s.close();
    }
}