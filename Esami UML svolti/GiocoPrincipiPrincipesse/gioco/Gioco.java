package gioco;

// import ..

public class Gioco {
    private Set<TLParteDiGioco> contieneGiocatori;
    
    public Gioco() {
        contieneGiocatori = new HashSet<TLParteDiGioco>();
    }
    
    public int quantiContieneGiocatori() { return contieneGiocatori.size(); }
    public Set<TLParteDiGioco> getContieneGiocatori() { return contieneGiocatori.clone(); }
    
    public void addLinkContieneGiocatore(TLParteDiGioco l) {
        if (l != null && l.getGioco().equals(this))
            ManagerParteDiGioco.addLink(l);
    }
    
    public void removeLinkContieneGiocatore(TLParteDiGioco l) {
        if (l != null && l.getGioco().equals(this))
            ManagerParteDiGioco.removeLink(l);
    }
    
    public void addLinkContieneGiocatorePerManager(ManagerParteDiGioco m) {
        if (m != null)
            contieneGiocatori.add(m.getLink());
    }
    
    public void removeLinkContieneGiocatorePerManager(ManagerParteDiGioco m) {
        if (m != null)
            contieneGiocatori.remove(m.getLink());
    }
}