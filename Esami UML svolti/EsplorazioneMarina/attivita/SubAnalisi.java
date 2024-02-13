package attivita;

// import

public class SubAnalisi implements Runnable {
    private Esplorazione e;
    private boolean eseguita = false;
    
    public SubAnalisi(Esplorazione e) {
        this.e = e;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;
        
        Calcolo c = new Calcolo(e);
        TaskExecutor.getInstance().perform(c);
        
        IO.stampaCalcolo(c.getRis());
    }
}