package torello.giocatore_rosso;

import torello.giocatore.*;

public class GiocatoreRosso extends Giocatore {
    private LinkPartecipaRosso partecipaRosso;
    
    public GiocatoreRosso(String nome) {
        super(nome);
        this.partecipaRosso = null;
    }
    
    public LinkPartecipaRosso getLinkPartecipaRosso() {
        return partecipaRosso;
    }
    
    public void addLinkPartecipaRosso(ManagerPartecipaRosso m) {
        if (m != null && this.partecipaRosso == null && m.getLink().getGiocatore().equals(this) && statoCorrente == StatoCorrente.NON_IN_GIOCO) {
            partecipaRosso = m.getLink();
        }
    }
    
    public void removeLinkPartecipaRosso(ManagerPartecipaRosso m) {
        if (m != null && m.getLink().getGiocatore().equals(this) && statoCorrente == StatoCorrente.NON_IN_GIOCO) {
            partecipaRosso = null;
        }
    }
    
    public void addLinkPartecipaRosso(LinkPartecipaRosso l) {
        ManagerPartecipaRosso.addLink(l);
    }
    
    public void removeLinkPartecipaRosso(LinkPartecipaRosso l) {
        ManagerPartecipaRosso.removeLink(l);
    }
}