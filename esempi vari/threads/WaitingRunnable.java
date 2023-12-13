package threads;

public class WaitingRunnable extends MyRunnable {

    private final Thread thread;

    public WaitingRunnable(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        try {
            thread.join();
            super.run();
        } catch (InterruptedException e) {

        }
    }
}
