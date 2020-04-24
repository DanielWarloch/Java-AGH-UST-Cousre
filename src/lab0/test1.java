package lab0;

public class test1 {


    public static void main(String[] args) {


        
        
        loop:
        for (int i = 0; i <3 ; i++) {
            for (int j = 0; j <3 ; j++) {
                System.out.print(i+j);
                if (i*j==2) break loop;
            }
        }
        
//
//        var a = 2.32 + "2.38";
//        System.out.println(a);
//
//
//        System.out.println(result(800000000));
    }


    static double result(int n_max){
        double result = 2;
        for (int i = 1; i <= n_max; i++) {
            result = result / (1+result);
        }
        return result;
    }










}
