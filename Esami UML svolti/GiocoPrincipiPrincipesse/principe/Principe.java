package principe;

// import ..

public class Principe extends Giocatore implements Listener {
    private Set<Principessa> piaceLa;
    private Principessa innamoratoDi;
    
    public Principe(String n) {
        super(n);
        piaceLa = new HashSet<Principessa>();
    }
    
    public Set<Principessa> getPiaceLa() { return piaceLa.clone(); }
    
    public Principessa getInnamoratoDi() throws RuntimeException {
        if (!piaceLa.contains(innamoratoDi)) throw new RuntimeException("vincolo subset"); 
        return innamoratoDi;
    }
    
    public addLinkPiaceLa(Principessa p) {
        if (p != null) piaceLa.add(p);
    }
    
    public removeLinkPiaceLa(Principessa p) {
        if (p != null) piaceLa.remove(p);
    }
    
    public addLinkInnamoratoDi(Principessa p) {
        if (p != null) innamoratoDi = p;
    }
    
    public removeLinkInnamoratoDi(Principessa p) {
        if (innamoratoDi == p) innamoratoDi = null;
    }
    
    // Gestione stato
    enum StatoPrincipe { CORTE, IMPRESA, RANOCCHIO };
    StatoPrincipe statoCorrente = StatoPrincipe.CORTE;
    Principessa pi;
    Drago d;
    SpadaIncantata s;
    
    public void fired(Evento e) {
        TaskExecutor.getInstance().perform(new PrincipeFired(this, e));
    }
    
}