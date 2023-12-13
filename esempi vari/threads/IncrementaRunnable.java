package threads;

public class IncrementaRunnable implements Runnable {
    private final int id;
    private final Contatore contatore;
    private static int count = 0;

    public IncrementaRunnable(Contatore contatore) {
        id = count++;
        this.contatore = contatore;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            contatore.incrementa();
            System.out.println(id + ": Contatore uguale a " + contatore.getContatore());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {

            }
        }
    }
}
