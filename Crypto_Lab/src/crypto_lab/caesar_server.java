
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class caesar_server {
    public static String decrypt(String cipher, int key){
        StringBuilder newplain= new StringBuilder();
            for(int i= 0; i<cipher.length(); i++){
                int ch= cipher.charAt(i)-'a';
                ch= (ch-key)%26;
                if(ch<0) ch+= 26;
                newplain.append((char)(ch+'a'));
            }
        return newplain.toString();
    }
    public static void main(String[] args) throws Exception{
        Scanner inp= new Scanner(System.in);
        ServerSocket ss= new ServerSocket(9999);
        Socket s= ss.accept();
        DataInputStream din= new DataInputStream(s.getInputStream());
        int key= (int)din.readInt();
        String cipher= din.readUTF();

        System.out.println("Received encrypted text: "+ cipher);
        String newplain= decrypt(cipher, key);
        System.out.println("The decrypted string is: "+ newplain);

    }
}
