package attivita;

// import ..

public class AttPrinc implements Runnable {
    private Esplorazione e;
    private boolean eseguita = false;
    
    public AttPrinc(Esplorazione e) {
        this.e = e;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;
        
        Thread tEsp = new Thread(new SubEsplora(e)),
               tVer = new Thread(new SubVerifica(e));
        tEsp.start(); tVer.start();
        
        try {
            tEsp.join(); tVer.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        IO.stampaFine();
    }
}