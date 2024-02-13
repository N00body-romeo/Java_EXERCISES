package attivita;

// import

public class AttivitaPrincipale implements Runnable {
    private Esplorazione e;
    private boolean eseguita = false;
    
    public AttivitaPrincipale(Esplorazione e) {
        this.e = e;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;
        
        Thread tEsplora = new Thread(new SubEsplora(e));
        Thread tAnalisi = new Thread(new SubAnalisi(e));
        
        tEsplora.start(); t.Analisi.start();
        
        try {
            tEsplora.join(); t.Analisi.join();
        } catch (InterruptException ex) {
            ex.printStackTrace();
        }
        
        IO.stampaFine();
    }
}