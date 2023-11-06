// package crypt_attacks;
import java.util.*;
public class caesar_known {
    public static void main(String[] args){
        Scanner inp= new Scanner(System.in);
        System.out.println("Enter the plaintext: ");
        String plaintext= inp.nextLine();
        System.out.println("Enter the ciphertext: ");
        String ciphertext= inp.nextLine();
        int key= -1;
        boolean result= true;
        if(plaintext.length() != ciphertext.length()) result= false;
        else{
            for(int i= 0; i<plaintext.length(); i++){
                int temp= ciphertext.charAt(i)- plaintext.charAt(i);
                temp= (temp+26)%26;
                if(key== -1) key= temp;
                else {
                    if(temp!= key){
                        result= false;
                        break;
                    }
                }
            }
        }
        if(result) System.out.println("The key found is: "+ key);
        else System.out.println("The texts do not match");
    }
}
