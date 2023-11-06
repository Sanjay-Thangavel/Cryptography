import java.util.*;
import java.io.DataOutputStream;
import java.net.*;


public class affine_client {

    public static String encrypt(String plain, int a, int b){
        StringBuilder cipher= new StringBuilder();
        for(int i= 0; i<plain.length(); i++){
            int ch= plain.charAt(i)-'a';
            ch= (ch*a + b)%26;
            cipher.append((char)(ch+'a'));
        }
        return cipher.toString();
    }
    public static void main(String[] args) throws Exception{
        Socket s= new Socket("localhost", 9999);
        DataOutputStream dout= new DataOutputStream(s.getOutputStream());
        Scanner inp= new Scanner(System.in);
        System.out.println("Enter the message to send: ");
        String msg= inp.nextLine();
        System.out.println("Enter value of a: ");
        int a= inp.nextInt();
        System.out.println("Enter value of b: ");
        int b= inp.nextInt();
        String cipher= encrypt(msg, a, b);
        dout.writeInt(a);
        dout.writeInt(b);
        dout.writeUTF(cipher);
    }
}
