package app_gioco;

import java.util.*;

public class Partita {
    private final String codice;
    private final LinkedList<LinkGioca> linkGioca;
    
    public static int MOLT_MIN = 1;
    
    public Partita(String _codice) {
        this.codice = _codice;
        this.linkGioca = new LinkedList<LinkGioca>();
    }
    
    public String getCodice() { return this.codice; }
    
    public List<LinkGioca> getLinkGioca() throws EccezioneMolteplicita {
        if (linkGioca.size() < MOLT_MIN)
            throw new EccezioneMolteplicita("molteplcità minima 1");
        
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