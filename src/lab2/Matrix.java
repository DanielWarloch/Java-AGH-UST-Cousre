package lab2;

import java.util.Arrays;
import java.util.Random;

public class Matrix {
    public static void main(String[] args) {
        /*Matrix x = new Matrix(5,6);
        Matrix m = new Matrix(new double[][]{{1,2,3,4},{5,6},{7,8},{9}});
        Matrix n = new Matrix(new double[][]{{1,2,3,4},{5,6},{7,8},{9}});
        System.out.println(m.toString());
        Matrix s = m.div(n);
        double forbs = s.frobenius();
        System.out.println(forbs);
//        System.out.println(s.toString());
*/
//        Matrix m = new Matrix(new double[][]{{1,2,3},{4,5,6},{7,8,9}});
//        Matrix a = new Matrix(new double[][]{{10},{11},{12}});
//        Matrix b = m.hStack(a);
//        Matrix b1 = new Matrix(new double[][]{{1,2,3,10},{4,5,6,11},{7,8,9,12}});
//        System.out.println(b.toString());
//
//        System.out.println(Arrays.deepEquals(b.asArray(),b1.asArray()));
//

        /*Matrix s = m.add(n);
        System.out.println(s.toString());
        s.reshape(8,2);
        System.out.println(s.toString());

        Matrix r = Matrix.random(2,3);
        System.out.println(r.toString());
        Matrix s1 = r.add(s);
        System.out.println(s1.toString());
*/

    }
    private double[] data;
    private int rows;
    private int cols;

    public Matrix(int rows, int cols){
        if (rows > 0 && cols > 0) {
            this.rows = rows;
            this.cols = cols;
            this.data = new double[rows * cols];
        }
    }
    public Matrix(double[][] d){
        this.rows = d.length;
        this.cols = Arrays.stream(d).map(row -> row.length).max(Integer::compare).get();
        this.data = new double[rows*cols];
        for (int r = 0; r<rows; ++r) {
            System.arraycopy(d[r], 0, this.data, r * cols, d[r].length);
        }
    }
    public double[][] asArray(){
        double[][] data = new double[this.rows][this.cols];
        for (int r = 0; r < this.rows; r++) {
            if (this.cols >= 0) System.arraycopy(this.data, r * cols, data[r], 0, this.cols);
        }
        return data;
    }

    int getRows() {
        return rows;
    }

    int getCols() {
        return cols;
    }
    double getValue(int row, int col){
        return this.data[row*this.cols + col];
    }

    void setValue(int row, int col, double value){
        this.data[row*this.cols + col] = value;
    }

    public String toString(){

        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (double[] rows: asArray()) {
            buf.append(Arrays.toString(rows)).append(", ");
        }
        buf.delete(buf.length() - 2, buf.length()).append("]");
        return Arrays.deepToString(asArray());
    }

    void reshape(int newRows,int newCols){
        if(rows*cols != newRows*newCols) {
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d",rows,cols,newRows,newCols));
        } else {
            this.rows = newRows;
            this.cols = newCols;
        }

    }
    int[] shape(){
        return new int[]{this.rows, this.cols};
    }
    public static Matrix random(int rows, int cols){
        Matrix m = new Matrix(rows, cols);
        Random random = new Random();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                m.setValue(r, c, random.nextDouble());
            }
        }
        return m;
    }
    public static Matrix eye(int n){
        Matrix solution = new Matrix(n,n);
        for (int i = 0; i < n; i++) {
            solution.setValue(i,i,1);
        }
        return solution;
    }
    Matrix add(Matrix m){

        if (Arrays.equals(this.shape(), m.shape())){
            Matrix solution = new Matrix(this.rows, this.cols);
            for (int r = 0; r < this.rows; r++) {
                for (int c = 0; c < this.cols; c++) {
                    solution.setValue(r, c, this.getValue(r, c)+m.getValue(r, c));
                }
            }
            return solution;
        }
        return null;
    }
    Matrix sub(Matrix m){
        if (Arrays.equals(this.shape(), m.shape())){
            Matrix solution = new Matrix(this.rows, this.cols);
            for (int r = 0; r < this.rows; r++) {
                for (int c = 0; c < this.cols; c++) {
                    solution.setValue(r, c, this.getValue(r, c)-m.getValue(r, c));
                }
            }
            return solution;
        }
        return null;
    }
    Matrix mul(Matrix m){
        if (Arrays.equals(this.shape(), m.shape())){
            Matrix solution = new Matrix(this.rows, this.cols);
            for (int r = 0; r < this.rows; r++) {
                for (int c = 0; c < this.cols; c++) {
                    solution.setValue(r, c, this.getValue(r, c)*m.getValue(r, c));
                }
            }
            return solution;
        }
        return null;
    }
    Matrix div(Matrix m){
        if (Arrays.equals(this.shape(), m.shape())){
            Matrix solution = new Matrix(this.rows, this.cols);
            for (int r = 0; r < this.rows; r++) {
                for (int c = 0; c < this.cols; c++) {
                    if (m.getValue(r, c)!=0){
                        solution.setValue(r, c, this.getValue(r, c)/m.getValue(r, c));
                    }
                }
            }
            return solution;
        }
        return null;
    }
    Matrix add(double w){
        Matrix solution = new Matrix(this.rows, this.cols);
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                solution.setValue(r, c, this.getValue(r, c)+w);
            }
        }
        return solution;
    }
    Matrix sub(double w){
        Matrix solution = new Matrix(this.rows, this.cols);
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                    solution.setValue(r, c, this.getValue(r, c)-w);
            }
        }
        return solution;
    }
    Matrix mul(double w){
        Matrix solution = new Matrix(this.rows, this.cols);
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                solution.setValue(r, c, this.getValue(r, c)*w);
            }
        }
        return solution;
    }
    Matrix div(double w){
        Matrix solution = new Matrix(this.rows, this.cols);
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                if (w!=0){
                    solution.setValue(r, c, this.getValue(r, c)/w);
                }
            }
        }
        return solution;
    }
    Matrix dot(Matrix m){
        Matrix solution = new Matrix(this.rows, m.getCols());
        for (int i = 0; i < this.rows ; i++) {
            for (int j = 0; j < this.cols ; j++) {
                double value = 0;
                for (int k = 0; k < this.rows; k++) {
                        value += this.getValue(i, k) * m.getValue(k, j);
                    }
                solution.setValue(i,j,value);
                }
            }
        return solution;
    }
    double frobenius(){
        double solution = 0;
        for (double value: data)
            solution += value * value;

        return solution;
    }

    Matrix hStack(Matrix a){
        if(this.rows != a.rows)
            throw new RuntimeException("fail");
        Matrix result = new Matrix(this.rows, this.cols + a.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.setValue(i,j,this.getValue(i,j));
            }
            for (int j1 = 0; j1 < a.cols; j1++) {
                result.setValue(i,this.cols+j1,a.getValue(i,j1));
            }
        }

        return result;

    }

    boolean isSquare(){
        return this.rows == this.cols;
    }



    void swapRows(int i1,int i2) {
        for(int j=0;j<this.getRows();j++){
            double tmp=this.getValue(i1,j);
            this.setValue(i1,j,this.getValue(i2,j));
            this.setValue(i2,j,tmp);
        }
    }

    Matrix invert(Matrix m1){
        if (!m1.isSquare()) {
            throw new RuntimeException("fail");
        }
        Matrix result = eye(m1.rows);






        return result;

    }








}


