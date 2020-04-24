package kolokwium2020;

import java.time.LocalTime;
import java.util.Random;

public class Zad2 extends Thread{
    int[] table;
    int sum;
    Random randomGenerator = new Random();
    int rand = randomGenerator.nextInt(100) + 1;
    public static void main(String[] args) {
        new Zad2().start();
    }

    int countDivisible(int k){
        sum =0;
        for (int a:table) {
            if (a % k == 0) ++sum;
        }
        return sum;
    }
//    int countDivisible(int k, int cnt){
//
//    }
    public void run(){
        LocalTime time = LocalTime.now();
        System.out.printf("%02d:%02d:%02d\n",
                time.getHour(),
                time.getMinute(),
                time.getSecond());
        countDivisible(5);
        LocalTime time1 = LocalTime.now();
        System.out.printf("%02d:%02d:%02d\n",
                time1.getHour(),
                time1.getMinute(),
                time1.getSecond());
        countDivisible(5);
    }

}
