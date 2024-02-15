package attivita;

// import ..

public class SubEsplora implements Runnable {
    private Esplorazione e;
    private boolean eseguita = false;
    
    public SubEsplora(Esplorazione e) {
        this.e = e;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;
        
        TaskExecutor.getInstance().perform(new AvviaEsplorazione(e));
        IO.attesaFine();
        TaskExecutor.getInstance().perform(new TerminaEsplorazione(e));
    }
}