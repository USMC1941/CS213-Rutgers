package primes;

import java.util.Scanner;

public class PrimesThread extends Thread {

    static int count, p;
    int n;

    public PrimesThread(int n) {
        this.n = n;

        // launches a new thread, which executes the run method. DO NOT CALL run()
        // directly!!
        start();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = Integer.parseInt(sc.nextLine());

        new PrimesThread(n);

        while (true) {
            System.out.print("? ");
            String line = sc.nextLine();
            if (line.equals("quit")) {
                break;
            }
            System.out.println("At " + (p - 1) + ", number of primes: " + count);
        }
        System.out.println("At " + (p - 1) + ", number of primes: " + count);
    }

    public void run() {
        count = 0;
        p = 2;
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
    }
}
