import java.util.*;
import java.net.*;

public class hill_server {
    public static int n;
    public static int[][] keyMatrix;

    public static String encrypt(String plaintext){
        int rows= plaintext.length()/n;
        int[][] plainMatrix= new int[rows][n];
        int ind= 0;
        for(int i= 0; i<rows; i++){
            for(int j= 0; j<n; j++){
                plainMatrix[i][j]= plaintext.charAt(ind)-'a';
                ind+= 1;
            }
        }

        //multiply the matrices
        int[][] cipherMatrix= new int[rows][n];
        for(int i= 0; i<rows; i++){
            for(int j= 0; j<n; j++){
                cipherMatrix[i][j]= 0;
                for(int k= 0; k<n; k++){
                    cipherMatrix[i][j] += plainMatrix[i][k] * keyMatrix[k][j];
                }
                cipherMatrix[i][j] %= 26;
            }
        }

        StringBuilder cipherText= new StringBuilder();
        for(int i= 0; i<rows; i++){
            for(int j= 0; j<n; j++){
                cipherText.append((char)(cipherMatrix[i][j] + 'a'));
            }
        }

        return cipherText.toString();

    }

    public static String decrypt(String cipherText){
        int rows= cipherText.length()/n;
        int[][] cipherMatrix= new int[rows][n];
        int ind= 0;
        for(int i= 0; i<rows; i++){
            for(int j= 0; j<n; j++){
                cipherMatrix[i][j]= cipherText.charAt(ind)-'a';
                ind+= 1;
            }
        }

        //multiply the matrices
        int[][] plainMatrix= new int[rows][n];
        int[][] adjoint_k= new int[n][n];
        adjoint(keyMatrix, adjoint_k);
        System.out.println("The adjoint matrix is: ");
        for(int i= 0; i<n; i++){
            for(int j= 0; j<n; j++){
                System.out.print(adjoint_k[i][j]+ " ");
            }
            System.out.println();
        }
        int det_k= determinant(keyMatrix, n);
        boolean negative= false;
        if(det_k < 0){
            negative= true;
            det_k = -1*det_k;
        }
        int k_inverse_num= k_inverse(det_k);
        System.out.println("The determinant inverse of k is: "+ k_inverse_num);
        for(int i= 0; i<rows; i++){
            for(int j= 0; j<n; j++){
                plainMatrix[i][j]= 0;
                for(int k= 0; k<n; k++){
                    plainMatrix[i][j] += cipherMatrix[i][k] * adjoint_k[k][j];
                }
                plainMatrix[i][j] *= k_inverse_num;
                if(negative) plainMatrix[i][j]*= 25;
                plainMatrix[i][j] %= 26;
                if(plainMatrix[i][j] < 0) plainMatrix[i][j]+= 26;
            }
        }

        StringBuilder plainText= new StringBuilder();
        for(int i= 0; i<rows; i++){
            for(int j= 0; j<n; j++){
                plainText.append((char)(plainMatrix[i][j] + 'a'));
            }
        }

        return plainText.toString();
    }

    public static void getCofactor(int A[][], int temp[][], int p, int q, int n){
        int i = 0, j = 0;
    
        // Looping for each element of the matrix
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                // Copying into temporary matrix only those element
                // which are not in given row and column
                if (row != p && col != q)
                {
                    temp[i][j++] = A[row][col];
    
                    // Row is filled, so increase row index and
                    // reset col index
                    if (j == n - 1)
                    {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }
 

    public static int determinant(int A[][], int size){
        int D = 0; // Initialize result
    
        // Base case : if matrix contains single element
        if (size == 1)
            return A[0][0];
    
        int [][]temp = new int[n][n]; // To store cofactors
    
        int sign = 1; // To store sign multiplier
    
        // Iterate for each element of first row
        for (int f = 0; f < size; f++)
        {
            // Getting Cofactor of A[0][f]
            getCofactor(A, temp, 0, f, size);
            D += sign * A[0][f] * determinant(temp, size - 1);
    
            // terms are to be added with alternate sign
            sign = -sign;
        }
    
        System.out.println("The determinant value of k is: "+ D);
        return D;
    }

    static void adjoint(int A[][],int [][]adj){
        if (n == 1)
        {
            adj[0][0] = 1;
            return;
        }
    
        // temp is used to store cofactors of A[][]
        int sign = 1;
        int [][]temp = new int[n][n];
    
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                // Get cofactor of A[i][j]
                getCofactor(A, temp, i, j, n);
    
                // sign of adj[j][i] positive if sum of row
                // and column indexes is even.
                sign = ((i + j) % 2 == 0)? 1: -1;
    
                // Interchanging rows and columns to get the
                // transpose of the cofactor matrix
                adj[j][i] = (sign)*(determinant(temp, n-1));
            }
        }
    }
    public static int k_inverse(int a){
    
        for(int i= 0; i<26; i++){
            if((a*i)%26== 1) return i;
        }
        return -1;
    }
    public static void main(String[] args){
        Scanner inp= new Scanner(System.in);

        System.out.println("Enter the plaintext: ");
        String plaintext= inp.nextLine();

        System.out.println("Enter the size of key matrix: ");
        n= inp.nextInt();
        keyMatrix= new int[n][n];

        
        while (plaintext.length() % n != 0) {
            plaintext += 'x';
        }

        
        System.out.println("Enter the key matrix: ");
        for(int i= 0; i<n; i++){
            for(int j= 0; j<n; j++){
                keyMatrix[i][j]= inp.nextInt();
            }
        }
        
        // key  : 3 1 6 5
        
        String cipherText= encrypt(plaintext);
        String  str1 = new String();
        str1= cipherText;
        System.out.println("The encrypted text is: "+ str1);

        String decryptedText= decrypt(cipherText);
        System.out.println("The decrypted message is: "+ decryptedText);
    }
}
