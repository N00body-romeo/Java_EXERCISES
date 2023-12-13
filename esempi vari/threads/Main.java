package threads;

public class Main {
    public static void main(String[] args) {
        // subMain();

        Contatore contatore = new Contatore();
        Thread t1 = new Thread(new IncrementaRunnable(contatore));
        Thread t2 = new Thread(new IncrementaRunnable(contatore));

        t1.start();
        t2.start();

        synchronized (contatore) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static void subMain() {
        Thread thread0 = new Thread(new MyRunnable());
        Thread thread1 = new Thread(new WaitingRunnable(thread0));
        Thread thread2 = new Thread(new WaitingRunnable(thread1));

        Thread t1 = new Thread(new SleepingRunnable());

        // runnable.run(); // esegue il codice di runnable sul thread 0
        // thread.run();   // esegue il codice del "thread"

        /*thread0.start(); // avvia un nuovo thread
        thread1.start();
        thread2.start();

        try {
            thread0.join();
            System.out.println("thread ha terminato");

            thread1.join();
            System.out.println("thread1 ha terminato");

            thread2.join();
            System.out.println("thread2 ha terminato");
        } catch (InterruptedException e) {

        }*/

        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        t1.interrupt();
    }
}
