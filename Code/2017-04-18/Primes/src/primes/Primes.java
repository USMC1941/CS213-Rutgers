package primes;

import java.util.Scanner;

public class Primes {

   public static int countPrimes(int n) {
      int count = 0, p = 2;
      while (p <= n) {
         int d;
         for (d = 2; d <= p / 2; d++) {
            if ((p % d) == 0) {
               break;
            }
         }
         if (d > p / 2) {
            count++;
         }
         p++;
      }
      return count;
   }

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter n: ");
      int n = Integer.parseInt(sc.nextLine());
      System.out.println("Number of primes <= " + n + " : " + countPrimes(n));
   }
}
