// package crypt_attacks;
import java.util.*;
public class hill_known {
    public static int[][] matricify(String text, int n){
        int rows= text.length()/n;
        int[][] resultMatrix= new int[rows][n];
        int ind= 0;
        for(int i= 0; i<rows; i++){
            for(int j= 0; j<n; j++){
                resultMatrix[i][j]= text.charAt(ind)-'a';
                ind+= 1;
            }
        }
        return resultMatrix;
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
 

    public static int determinant(int A[][], int size, int n){
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
            D += sign * A[0][f] * determinant(temp, size - 1, n);
    
            // terms are to be added with alternate sign
            sign = -sign;
        }
    
        // System.out.println("The determinant value of k is: "+ D);
        return D;
    }

    static void adjoint(int A[][],int [][]adj, int n){
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
                adj[j][i] = (sign)*(determinant(temp, n-1, n));
            }
        }
    }
    public static int k_inverse(int a){
    
        for(int i= 0; i<26; i++){
            if((a*i)%26== 1) return i;
        }
        return -1;
    }

    public static String encrypt(String plaintext, int n, int[][] keyMatrix){
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

    public static void main(String[] args){
        Scanner inp=new Scanner(System.in);
        System.out.println("Enter the Plaintext:");
        // String plaintext=inp.nextLine();
        String plaintext= "attackistonight";
        System.out.println("Enter the Ciphertext:");
        // String cipherText=inp.nextLine();
        String cipherText= "fnoagajgbkdlrri";
        boolean found= false;

        for(int k= 2; k<4; k++){

            if(plaintext.length() % k != 0) continue;
            // for each order matrix
            int[][] plainMatrix= matricify(plaintext, k);
            int[][] cipherMatrix= matricify(cipherText, k);

            int[][] plain= Arrays.copyOfRange(plainMatrix, 0, k);
            int[][] cipher= Arrays.copyOfRange(cipherMatrix, 0, k);

            System.out.println("size of plain: "+ plain.length);

            int[][] adjoint_P= new int[k][k];
            int[][] key= new int[k][k];
            adjoint(plain, adjoint_P, k);
            int det_k= determinant(plain, k, k);
            boolean negative= false;
            if(det_k < 0){
                negative= true;
                det_k = -1*det_k;
            }
            int p_inverse_num= k_inverse(det_k);

            for(int i= 0; i<k; i++){
                for(int j= 0; j<k; j++){
                    key[i][j]= 0;
                    for(int ki= 0; ki<k; ki++){
                        key[i][j]+= adjoint_P[i][ki]*cipher[ki][j];
                    }
                    if(negative) key[i][j]*= 25;
                    key[i][j]*= p_inverse_num;
                    key[i][j] %= 26;
                    if(key[i][j] < 0) key[i][j]+= 26; 
                }
            }

            String new_encrypted= encrypt(plaintext, k, key);
            if(new_encrypted.equals(cipherText)){
                found= true;
                System.out.println("The key matrix is: ");
                for(int i= 0; i<k; i++){
                    for(int j= 0; j<k; j++){
                        System.out.print(key[i][j]+ " ");
                    }
                    System.out.println();
                }
                break;
            }
        }

        if(!found) System.out.println("Could not find the key matrix");

    }
}
