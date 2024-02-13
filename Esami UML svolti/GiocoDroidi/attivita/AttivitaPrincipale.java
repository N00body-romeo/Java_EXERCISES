package attivita;

public class AttivitaPrincipale implements Runnable {
    private Gioco gioco;
    private boolean eseguita = false;
    
    public AttivitaPrincipale(Gioco g) {
        this.gioco = g;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;
        
        Verifica ver = new Verifica(gioco);
        TaskExecutor.getInstance().perform(ver);
        boolean resV = ver.getRis();
        
        if (resV) {
            Thread attGioco = new Thread(new SottoattivitaGioco(gioco));
            SottoattivitaAnalisi analisi = new SottoattivitaAnalisi(gioco);
            Thread attAnalisi = new Thread(analisi);
            
            attGioco.start();
            attAnalisi.start();
            
            try {
                attGioco.join(); attAnalisi.join();
            } catch (InterruptException e) {
                e.printStackTrace();
            }
            
            IO.stampaReport(analisi.getRis());
        } else {
            IO.stampErrore();
        }
    }
}