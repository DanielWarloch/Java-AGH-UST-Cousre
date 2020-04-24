package lab1;

import java.util.Scanner;
import java.util.Locale;

public class SimpleIO {
    public static void main(String[] args) {
        System.out.print("...");
        System.out.println("...");
        System.out.printf("String %s int %d double %f","aaa",10,8.5);

        Scanner scan = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Podaj String");
        String s = scan.next();
        System.out.println("Podaj Int");
        int i = scan.nextInt();
        System.out.println("Podaj Double");
        double d = scan.nextDouble();
        System.out.printf(Locale.US,"Wczytano %s , %d, %f",s,i,d);
    }
}
