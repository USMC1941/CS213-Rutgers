package primes;

import java.util.Scanner;

public class PrimesRunnable implements Runnable {

    static int count, p;
    static Thread primesThread;
    int n;

    public PrimesRunnable(int n) {
        this.n = n;
        primesThread = new Thread(this);
        primesThread.start();
        // this instance is a target for a thread, which will execute this
        // instance's run method in a separate thread
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = Integer.parseInt(sc.nextLine());

        new PrimesRunnable(n);

        while (primesThread.getState() != Thread.State.TERMINATED) {
            System.out.print("? ");
            String line = sc.nextLine();
            if (line.equals("quit")) {
                // interrupt the other thread
                primesThread.interrupt();
                break;
            }
            System.out.println("At " + (p - 1) + ", number of primes: " + count);
        }
        System.out.println("At " + (p - 1) + ", number of primes: " + count);
    }

    public void run() {
        count = 0;
        p = 2;
        while (!Thread.interrupted() && p <= n) {
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
