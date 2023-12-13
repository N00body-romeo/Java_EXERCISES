package threads;

public class SleepingRunnable implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("t1: prima dello sleep");
            Thread.sleep(2000);
            System.out.println("t1: dopo lo sleep");
        } catch (InterruptedException e) {
            System.out.println("t1: segnalato interrupt");
        }

        /*// avvio download

        while (true) {
            if (Thread.interrupted()) {
                // annulla download
            }

            // gestione download
        }*/

        System.out.println("t1: fine");
    }
}
