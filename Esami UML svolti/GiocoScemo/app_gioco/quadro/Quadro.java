package app_gioco.quadro;

import app_gioco.*;

public class Quadro {
    protected String ambientazione;
    protected final HashSet<LinkGioca> linkGioca;
    
    public Quadro() {
        this.linkGioca = new HashSet<LinkGioca>();
    }
    
    public void setAmbientazione(String amb) {
        this.ambientazione = amb;
    }
    
    public String getAmbientazione() {
        return this.ambientazione;
    }
    
    public Set<LinkGioca> getLinkGioca() {
        return linkGioca.clone();
    }
    
    public void addLinkGioca(ManagerGioca m) {
        if (m != null && !linkGioca.contains(m.getLink())) {
            linkGioca.add(m.getLink());
        }
    }
    
    public void removeLinkGioca(ManagerGioca m) {
        if (m != null) {
            linkGioca.remove(m.getLink());
        }
    }
}