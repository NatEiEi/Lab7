import java.util.Arrays;
/* 1.6 x value is 3
from main x value is 7 */

public class Lab_MatrixMul {
    public static void main(String[] args) {
        int[][] inputA = { { 5, 6, 7 }, { 4, 8 , 9 } };
        int[][] inputB = { { 6, 4 }, { 5, 7 }, { 1, 1 } };
        MyData matA = new MyData(inputA);
        MyData matB = new MyData(inputB);
        int matC_r = matA.data.length;
        int matC_c = matB.data[0].length;
        MyData matC = new MyData(matC_r, matC_c);

        Thread[] Thread_arr = new Thread[matC_r * matC_c];
        int cnt = 0;
        for(int i = 0 ; i < matC_r ; i ++) {
            for(int j = 0 ; j < matC_c ; j++){
                Thread mat = new Thread(new MatrixMulThread(i, j, matA, matB, matC));
                mat.start();
                Thread_arr[cnt++] = mat;
            }
        }

        for (Thread t : Thread_arr){
            try {        
                t.join();
            } catch (Exception e) { System.out.println(e); }
        }

        matC.show();
    }
}

class MatrixMulThread implements Runnable {
    int processing_row;
    int processing_col;
    MyData datA;
    MyData datB;
    MyData datC;
    
    MatrixMulThread(int tRow, int tCol, MyData a, MyData b, MyData c){
        processing_row = tRow;
        processing_col = tCol;
        datA = a;
        datB = b;
        datC = c;
    }

    public void run(){
        System.out.println(processing_row+" " +processing_col);
        for(int i = 0; i < datA.data[0].length; i++) {
            datC.data[processing_row][processing_col] += datA.data[processing_row][i] * datB.data[i][processing_col];
        }
    }
}   

class MyData{
    int[][] data;

    MyData(int[][] m) {
        data = m;
    }

    MyData(int r, int c) {
        data = new int[r][c];
        for(int[] aRow : data){
            Arrays.fill(aRow, 0);
        }
    }

    void show() {
        System.out.println(Arrays.deepToString(data));
    }
}