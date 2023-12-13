package threads;

public class MyRunnable implements Runnable {

    private static int count = 0;
    private final int id;

    public MyRunnable() {
        id = count++;
    }

    @Override
    public void run() {
        for (int i = 0; i<100; i++) {
            System.out.print(id);
            try {
                Thread.sleep((long)id*50);
            } catch (InterruptedException e) {

            }
        }
    }
}
