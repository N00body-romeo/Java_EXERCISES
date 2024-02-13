package parte_di_gioco;

// import ..

public final class ManagerParteDiGioco {
    private TLParteDiGioco link;
    
    private ManagerParteDiGioco(TLParteDiGioco l) {
        this.link = l;
    }
    
    public TLParteDiGioco getLink() { return link; }
    
    public static void addLink(TLParteDiGioco l) {
        if (l != null) {
            ManagerParteDiGioco m = new ManagerParteDiGioco(l);
            l.getGioco().addLinkContieneGiocatorePerManager(m);
            l.getGiocatore().addLinkParteDiGiocoPerManager(m);
        }
    }
    
    public static void removeLink(TLParteDiGioco l) {
        if (l != null) {
            ManagerParteDiGioco m = new ManagerParteDiGioco(l);
            l.getGioco().removeLinkContieneGiocatorePerManager(m);
            l.getGiocatore().removeLinkParteDiGiocoPerManager(m);
        }
    }
}