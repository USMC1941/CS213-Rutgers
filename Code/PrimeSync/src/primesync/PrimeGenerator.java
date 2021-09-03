package primesync;

/** @author Sesh Venugopal */
public class PrimeGenerator implements Runnable {

    private final PrimeCentral primeCentral;
    private final int bound;

    public PrimeGenerator(PrimeCentral primeCentral, int bound) {
        this.primeCentral = primeCentral;
        this.bound = bound;
        new Thread(this).start();
    }

    public void run() {
        int n = bound;
        while (n > 1) {
            int d;
            for (d = 2; d <= n / 2; d++) {
                if ((n % d) == 0) {
                    break;
                }
            }
            if (d > n / 2) {
                primeCentral.put(n);
            }
            n--;
        }
    }
}
