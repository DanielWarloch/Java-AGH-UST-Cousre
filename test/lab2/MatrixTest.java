package lab2;

import java.awt.event.MouseAdapter;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class MatrixTest {


    public Matrix generateMatrix(boolean symmetric){ //Generate random size matrix. Size under 20.
        int r = 20;
        int c = 20;
        Random rand = new Random();
        if(symmetric){
            return new Matrix(rand.nextInt(r), rand.nextInt(c));
        } else {
            int randS = rand.nextInt(r);
            return new Matrix(randS, randS);
        }
    }
    public Matrix generateMatrix(){return generateMatrix(false);}
    public double[][] randomFilled2DArray(int rows, int cols){
        double[][] arr = new double[rows][cols];
        Random rand = new Random();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                arr[r][c] = rand.nextDouble();
            }
        }
        return arr;
    }



    @org.junit.Test
    public void main() {
    }

    @org.junit.Test
    public void asArray() {
        int colsMax = 20;
        int rowsMax = 20;
        Random rand = new Random();
        double[][] arrayOriginal = new double[rand.nextInt(rowsMax)][rand.nextInt(colsMax)];
        for (double[] rows: arrayOriginal) Arrays.fill(rows, rand.nextDouble());
        Matrix m = new Matrix(arrayOriginal);
        if (!Arrays.deepEquals(arrayOriginal, m.asArray())) fail("To nie działa");

    }

    @org.junit.Test
    public void getRows() {
        int rowsMax = 200;
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int rows = rand.nextInt(rowsMax);
            int cols = 1;
            Matrix m = new Matrix(rows, cols);
            if (m.getRows() != rows){
                fail("To nie działa");
            }
        }
    }

    @org.junit.Test
    public void getCols() {
        int colsMax = 200;
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int rows = 1;
            int cols = rand.nextInt(colsMax);
            Matrix m = new Matrix(rows, cols);
            if (m.getCols() != cols){
                fail("To nie działa");
            }
        }



    }

    @org.junit.Test
    public void getValue() {

    }

    @org.junit.Test
    public void setValue() {

    }

    @org.junit.Test
    public void testToString() {
        String s= "[[1.0,2.3,4.56], [12.3,  45, 21.8]]";
        s= s.replaceAll("(\\[|]|\\s)+","");
        String[] t = s.split("(,)+");
        for(String x:t){
            System.out.println(String.format("\'%s\'",x ));
        }

        double[]d=new double[t.length];
        for(int i=0;i<t.length;i++) {
            d[i] = Double.parseDouble(t[i]);
        }

        double[][] arr = new double[1][];
        arr[0] = d;

        for (double[] doubles : arr) {
            for (double aDouble : doubles) {
                System.out.println(aDouble);
            }
        }
    }

    @org.junit.Test
    public void reshape() {
    }

    @org.junit.Test
    public void shape() {
        int rowsMax = 200;
        int colsMax = 200;
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int rows = rand.nextInt(rowsMax);
            int cols = rand.nextInt(colsMax);
            Matrix m = new Matrix(rows, cols);
            if (m.shape()[0] != rows && m.shape()[1] != cols){
                fail("To nie działa");
            }
        }
    }


    @org.junit.Test
    public void random() {
    }

    @org.junit.Test
    public void add() {
        int rowsMax = 20;
        int colsMax = 20;
        Random rand = new Random();
        int rows = rand.nextInt(rowsMax);
        int cols = rand.nextInt(colsMax);

        double[][] arrA = new double[rows][cols];
        double[][] arrB = randomFilled2DArray(rows,cols);
        Matrix mA = new Matrix(arrA);
        Matrix mB = new Matrix(arrB);
        mA = mA.add(mB);
        if (!Arrays.deepEquals(arrB, mA.asArray())) fail("To nie działa");


    }

    @org.junit.Test
    public void sub() {
    }

    @org.junit.Test
    public void mul() {
    }

    @org.junit.Test
    public void div() {
    }

    @org.junit.Test
    public void testAdd() {
    }

    @org.junit.Test
    public void testSub() {
    }

    @org.junit.Test
    public void testMul() {
    }

    @org.junit.Test
    public void testDiv() {
    }
}