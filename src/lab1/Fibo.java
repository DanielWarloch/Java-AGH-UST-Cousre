package lab1;
import java.util.Scanner;


public class Fibo {
    public static void main(String[] args) {

//      System.out.println("Podaj liczbę z zakresu 1-45");
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        if (n>=1 && n<=45){
            int[] tab = new int[n];
            tab[0] = 1;
            if (tab.length>1){
                tab[1] = 1;
                for (int i=2; i<n; ++i){
                    tab[i]=tab[i-1] + tab[i-2];
                }
                for (int value : tab) {
                    System.out.println(value);
                }
            }
        }
    }
}
