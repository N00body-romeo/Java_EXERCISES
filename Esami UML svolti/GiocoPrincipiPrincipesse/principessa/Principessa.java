package principessa;

// import ..

public class Principessa extends Giocatore {
    private Set<Principe> piaceIl;
    private Principe innamorataDi;
    private Set<TLHaAccesso> haAccesso;
    
    public Principessa(String n) {
        super(n);
        piaceIl = new HashSet<Principe>();
        haAccesso = new HashSet<TLHaAccesso>();
    }
    
    public Set<Principe> getPiaceIl() { return piaceIl.clone(); }
    
    public Principe getInnamorataDi() throws RuntimeException {
        if (!piaceIl.contains(innamorataDi)) throw new RuntimeException("vincolo subset");
        return innamorataDi;
    }
    
    public addLinkPiaceIl(Principe p) {
        if (p != null) piaceIl.add(p);
    }
    
    public removeLinkPiaceIl(Principe p) {
        if (p != null) piaceIl.remove(p);
    }
    
    public addLinkInnamorataDi(Principe p) {
        if (p != null) innamorataDi = p;
    }
    
    public removeLinkInnamorataDi(Principe p) {
        if (innamorataDi == p) innamorataDi = null; 
    }
    
    public Set<TLHaAccesso> getHaAccesso() { return haAccesso.clone(); }
    
    public void addLinkHaAccesso(TLHaAccesso l) {
        if (l != null) ManagerHaAccesso.addLink(l);
    }
    
    public void removeLinkHaAccesso(TLHaAccesso l) {
        if (l != null) ManagerHaAccesso.removeLink(l);
    }
    
    public void addLinkHaAccessoPerManager(ManagerHaAccesso m) {
        if (m != null) haAccesso.add(m.getLink());
    }
    
    public void removeLinkHaAccessoPerManager(ManagerHaAccesso m) {
        if (m != null) haAccesso.remove(m.getLink());
    }
}