package lab1;
import java.util.Scanner;

public class Problem610A {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        float na = n >> 2;
        if (n%2==0) {

            if (na % 1 == 0.0) {
                --na;
            }
            System.out.print((int) na);
        }else {
            System.out.print("0");
        }
    }
}

