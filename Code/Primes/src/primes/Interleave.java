package primes;

public class Interleave implements Runnable {

    String name;

    public Interleave(String name) {
        this.name = name;
        new Thread(this, name).start();
    }

    public static void main(String[] args) {
        new Interleave("java");
        new Interleave("sumatra");
    }

    public void run() {
        for (int i = 0; i < 4; i++) {
            System.out.println(name);
            try {
                Thread.sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
            }
        }
    }
}
