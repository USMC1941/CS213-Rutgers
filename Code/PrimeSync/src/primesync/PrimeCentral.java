package primesync;

/** @author Sesh Venugopal */
public class PrimeCentral {
    private int prime;
    private boolean available = false;

    public synchronized int get() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        available = false;
        notifyAll();
        return prime;
    }

    public synchronized void put(int prime) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        this.prime = prime;
        available = true;
        notifyAll();
    }
}
