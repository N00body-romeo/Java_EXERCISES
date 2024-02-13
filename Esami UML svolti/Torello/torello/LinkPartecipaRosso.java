package torello;

import torello.giocatore_rosso.*;

public class LinkPartecipaRosso {
    private final GiocatoreRosso giocatore;
    private final Torello torello;
    
    public LinkPartecipaRosso(GiocatoreRosso r, Torello t) throws RuntimeException {
        if (r == null || t == null) throw new RuntimeException("oggetti non inizializzati");
        
        this.giocatore = r;
        this.torello = t;
    }
    
    public GiocatoreRosso getGiocatore() { return giocatore; }
    public Torello getTorello() { return torello; }
    
    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass().equals(this.getClass())) {
            LinkPartecipaRosso l = (LinkPartecipaRosso) o;
            return l.giocatore == this.giocatore && l.torello == this.torello;
        } else return false;
    }
    
    @Override
    public int hashCode() {
        return giocatore.hashCode() + torello.hashCode();
    }
}