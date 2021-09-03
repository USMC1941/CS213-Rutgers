package primesync;

/** @author Sesh Venugopal */
public class PrimeAcceptor implements Runnable {
    private final PrimeCentral primeCentral;
    private final int bound;
    private final int numIntervals;

    public PrimeAcceptor(PrimeCentral primeCentral, int bound, int numIntervals) {
        this.primeCentral = primeCentral;
        this.numIntervals = numIntervals;
        this.bound = bound;
        new Thread(this).start();
    }

    public void run() {
        int range = bound / numIntervals;
        int lo = bound - (bound % numIntervals + range - 1);
        int rangeCount = 0, totalCount = 0, hi = bound;
        while (true) {
            int prime = primeCentral.get();
            if (prime == 2) {
                rangeCount++;
                break;
            }
            if (prime < lo) {
                System.out.println("#Primes in range [" + lo + "," + hi + "] :\t" + rangeCount);
                totalCount += rangeCount;
                rangeCount = 0;
                hi = lo - 1;
                lo = hi - range + 1;
            }
            rangeCount++;
        }
        System.out.println("#Primes in range [2," + hi + "] :\t" + rangeCount);
        totalCount += rangeCount;
        System.out.println("Total number of primes <= " + bound + " : " + totalCount);
    }
}
