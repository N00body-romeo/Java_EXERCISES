package attivita;

// import ..

public class SubGioco implements Runnable {
    private Gioco g;
    private boolean eseguita = false;
    
    public SubGioco(Gioco g) {
        this.g = g;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;
        
        TaskExecutor.getInstance().perform(new AvviaGioco(g));
        IO.attesaFine();
        TaskExecutor.getInstance().perform(new TerminaGioco(g));
    }
}