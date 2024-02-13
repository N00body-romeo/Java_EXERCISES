package attivita;

// import ..

public class SubEsecuzione implements Runnable {
    private Carovana c;
    private boolean eseguita = false;
    
    public SubEsecuzione(Carovana c) {
        this.c = c;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;
        
        TaskExecutor.getInstance().perform(new AvviaEsec(c));
        IO.attesaFine();
        TaskExecutor.getInstance().perform(new FineEsec(c));
    }
}