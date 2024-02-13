package sottomarino;

// import ..

public class Sottomarino extends DispositivoRobotico implements Listener {
    private double profMax;
    private TLContieneSottomarino contieneS;
    private Set<TLContieneBatiscafo> contieneB;
    
    private static final int MIN_MOL_CONTIENES = 1;
    private static final int MAX_MOL_CONTIENES = 1;
    private static final int MIN_MOL_CONTIENEB = 1;
    
    public Sottomarino(String n, double p) {
        super(n);
        this.profMax = p;
        this.contieneB = new HashSet<TLContieneBatiscafo>();
    }
    
    public double getProfMax() { return profMax; }
    
    public int quantiContieneS() { return (contieneS != null) ? 1 : 0; }
    public int quantiContieneB() { return contieneB.size(); }
    
    public TLContieneSottomarino getContieneSottomarino() throws RuntimeException {
        if (quantiContieneS() < MIN_MOL_CONTIENES)
            throw new RuntimeException("Errore molteplicità minima");
        
        return contieneS;
    }
    
    public Set<TLContieneBatiscafo> getContieneBatiscafo() throws RuntimeException {
        if (quantiContieneB() < MIN_MOL_CONTIENEB)
            throw new RuntimeException("Errore molteplicità minima");
        
        return contieneB.clone();
    }
    
    public void addLinkContieneSottomarino(TLContieneSottomarino link) throws RuntimeException {
        if (quantiContieneS() == MAX_MOL_CONTIENES)
            throw new RuntimeException("Errore molteplicità massima");
        
        if (link != null && link.getSottomarino().equals(this))
            ManagerContieneSottomarino.addLink(link);
    }
    
    public void addLinkContieneBatiscafo(TLContieneBatiscafo link) {
        if (link != null && link.getSottomarino().equals(this))
            ManagerContieneBatiscafo.addLink(link);
    }
    
    public void removeLinkContieneSottomarino(TLContieneSottomarino link) {
        if (link != null && link.getSottomarino().equals(this))
            ManagerContieneSottomarino.removeLink(link);
    }
    
    public void removeLinkContieneBatiscafo(TLContieneBatiscafo link) {
        if (link != null && link.getSottomarino().equals(this))
            ManagerContieneBatiscafo.removeLink(link);
    }
    
    public void addLinkContieneSottomarinoPerManager(ManagerContieneSottomarino m) {
        if (m != null) contieneS = m.getLink();
    }
    
    public void addLinkContieneBatiscafoPerManager(ManagerContieneBatiscafo m) {
        if (m != null) contieneB.add(m.getLink());
    }
    
    public void removeLinkContieneSottomarinoPerManager(ManagerContieneSottomarino m) {
        if (m != null && m.getLink().equals(contieneS))
            contieneS = null;
    }
    
    public void removeLinkContieneBatiscafoPerManager(ManagerContieneBatiscafo m) {
        if (m != null && contieneB.contains(m.getLink()))
            contieneB = null;
    }
    
    // Gestione eventi
    enum StatoSottomarino {
        A_BORDO, IMMERSO, IMMERSO_BATISCAFO, ATTESA_RIENTRO
    }
    
    StatoSottomarino statoCorrente = A_BORDO;
    double p = 0;
    Batiscafo b = null;
    
    public void fired(Evento e) {
        TaskExecutor.getInstance().perform(new SottomarinoFired(this, e));
    }
    
    public StatoSottomarino getStatoCorrente() { return statoCorrente; }
}