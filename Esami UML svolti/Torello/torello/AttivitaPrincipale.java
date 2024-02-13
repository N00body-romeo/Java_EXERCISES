package torello;

import torello.giocatore_rosso.*;
import torello.giocatore_giallo.*;

public class AttivitaPrincipale implements Runnable {
    private boolean eseguita = false;
    private final Torello t;
    private HashSet<GiocatoreRosso> giocatoriRossi;
    private HashSet<GiocatoreGiallo> giocatoriGialli;
    
    public AttivitaPrincipale(Torello t) {
        this.t = t;
    }
    
    public synchronized void run() {
        if (eseguita) return;
        
        LeggiGiocatori.perform();
        giocatoriRossi = LeggiGiocatori.getGiocatoriRossi();
        giocatoriGialli = LeggiGiocatori.getGiocatoriGialli();
        
        InserisciGiocatori act1 = new InserisciGiocatori(t, giocatoriRossi, giocatoriGialli);
        Executor.perform(act1);
        
        Thread t1 = new Thread(new Gioca(t));
        Thread t2 = new Thread(new AttendiFine());
        
        t1.start();
        t2.start();
        
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized boolean estEseguita() {
        return eseguita;
    }
}