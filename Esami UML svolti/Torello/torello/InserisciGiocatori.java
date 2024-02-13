package torello;

import java.util.*;
import torello.giocatore_rosso.*;
import torello.giocatore_giallo.*;

public class InserisciGiocatori implements Task {
    private boolean eseguita = false;
    private final Torello torello;
    private final Set<GiocatoreRosso> rossi;
    private final Set<GiocatoreGiallo> gialli;
    
    public InserisciGiocatori(Torello t, Set<GiocatoreRosso> r, Set<GiocatoreGiallo> g) {
        this.torello = t;
        this.rossi = r;
        this.gialli = g;
    }
    
    public Torello getTorello() { return torello; }
    public Set<GiocatoreRosso> getRossi() { return rossi.clone(); }
    public Set<GiocatoreGiallo> getGialli() { return gialli.clone(); }
    
    public synchronized void esegui(Executor exec) {
        if (eseguita) return;
        eseguita = true;
        
        for (GiocatoreRosso rosso : rossi)
            ManagerPartecipaRosso.addLink(new LinkPartecipaRosso(rosso, torello));
        
        for (GiocatoreGiallo giallo : gialli)
            ManagerPartecipaGialli.addLink(new LinkPartecipaGialli(giallo, torello));
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
}