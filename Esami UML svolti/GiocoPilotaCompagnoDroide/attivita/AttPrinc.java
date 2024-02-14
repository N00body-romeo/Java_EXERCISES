package attivita;

// import ..

public class AttPrinc implements Runnable {
    private Gioco g;
    private boolean eseguita = false;
    
    public AttPrinc(Gioco g) {
        this.g = g;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;
        
        Verifica attVer = new Verifica(g);
        TaskExecutor.getInstance().perform(attVer);
        boolean ver = attVer.getRis();
        
        if (ver) {
            Thread tGioco = new Thread(new SubGioco(g)),
                   tAnalisi = new Thread(new SubAnalisi(g));
            
            tGioco.start(); tAnalisi.start();
            
            try {
                tGioco.join(); tAnalisi.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            IO.stampaSaluto();
            
        } else {
            IO.stampaErr();
        }
    }
}