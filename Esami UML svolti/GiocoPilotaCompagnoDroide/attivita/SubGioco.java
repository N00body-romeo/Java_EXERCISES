package attivita;

// import ..

public class SubGioco implements Runnable {
    private Gioco g;
    private boolean eseguita = false;

    public AttPrinc(Gioco g) {
        this.g = g;
    }

    public synchronized boolean estEseguita() { return eseguita; }

    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;

        TaskExecutor.getInstance().perform(new AvviaGioco(g));
        IO.attesaFine();
        TaTaskExecutor.getInstance().perform(new FineGioco(g));
    }
}