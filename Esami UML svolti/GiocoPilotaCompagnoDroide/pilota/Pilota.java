package pilota;

// import ..

public class Pilota extends Giocatore implements Listener {
    private int oreVolo;
    private Set<Astronave> puoPilotare;
    private Set<TLPiaceVolCon> piaceVolCon;
    private TLCompagnoDiVolo compagnoDiVolo;
    
    public static final int PUOPILOTARE_MOL_MIN = 1;
    public static final int PIACEVOLCON_MOL_MIN = 1;
    public static final int COMPAGNOVOLO_MOL_MIN = 1;
    
    public Pilota(String n, int o) {
        super(n);
        this.oreVolo = o;
        this.puoPilotare = new HashSet<>();
        this.piaceVolCon = new HashSet<>();
    }
    
    public int getOreDiVolo() { return oreVolo; }
    
    public int quantiPuoPilotare() { return puoPilotare.size(); }
    
    public Set<Astronave> getPuoPilotare() throws RuntimeException {
        if (quantiPuoPilotare() < PUOPILOTARE_MOL_MIN)
            throw new RuntimeException("vincolo molteplicità minima");
        
        return puoPilotare.clone();
    }
    
    public void addPuoPilotare(Astronave a) {
        if (a != null)
            puoPilotare.add(a);
    }
    
    public void removePuoPilotare(Astronave a) {
        if (a != null)
            puoPilotare.remove(a);
    }
    
    public int quantiPiaceVolCon() { return piaceVolCon.size(); }
    
    public Set<TLPiaceVolCon> getLinkPiaceVolCon() throws RuntimeException {
        if (quantiPiaceVolCon() < PIACEVOLCON_MOL_MIN)
            throw new RuntimeException("vincolo molteplicità minima");
        
        return piaceVolCon.clone();
    }
    
    public void addLinkPiaceVolCon(TLPiaceVolCon l) {
        if (l != null && l.getPilota().equals(this)) 
            ManagerPiaceVolCon.addLink(l);
    }
    
    public void removeLinkPiaceVolCon(TLPiaceVolCon l) {
        if (l != null && l.getPilota().equals(this))
            ManagerPiaceVolCon.removeLink(l);
    }
    
    public void addLinkPiaceVolConPerManager(ManagerPiaceVolCon m) {
        if (m != null)
            piaceVolCon.add(m.getLink());
    }
    
    public void removeLinkPiaceVolConPerManager(ManagerPiaceVolCon m) {
        if (m != null)
            piaceVolCon.remove(m.getLink());
    }
    
    public int quantiCompagnoDiVolo() { return (compagnoDiVolo == null) ? 0 : 1; }
    
    public TLCompagnoDiVolo getLinkCompagnoDiVolo() throws RuntimeException {
        if (quantiCompagnoDiVolo() < COMPAGNOVOLO_MOL_MIN)
            throw new RuntimeException("vincolo molteplicità minima");
        
        if (!piaceVolCon.contains(new TLPiaceVolCon(compagnoDiVolo.getPilota(), compagnoDiVolo.getDroide())))
            throw new RuntimeException("vincolo subset");
        
        return compagnoDiVolo;
    }
    
    public void addLinkCompagnoDiVolo(TLCompagnoDiVolo l) {
        if (compagnoDiVolo == null && l != null && l.getPilota().equals(this))
            ManagerCompagnoDiVolo.addLink(l);
    }
    
    public void removeLinkCompagnoDiVolo(TLCompagnoDiVolo l) {
        if (l != null && compagnoDiVolo.equals(l))
            ManagerCompagnoDiVolo.removeLink(l);
    }
    
    public void addLinkCompagnoDiVoloPerManager(ManagerCompagnoDiVolo m) {
        if (m != null)
            compagnoDiVolo = m.getLink();
    }
    
    public void removeLinkCompagnoDiVoloPerManager(ManagerCompagnoDiVolo m) {
        if (m != null && m.getLink().equals(compagnoDiVolo))
            compagnoDiVolo = null;
    }
    
    // Gestione eventi
    enum StatoPilota { BASE, ATTESA_DROIDE, ATTESA_PASSEGGERO, VOLO };
    StatoPilota stato = BASE;
    Astronave a;
    Passeggero p;
    
    public void fired(Evento e) {
        TaskExecutor.getInstance().perform(new PilotaFired(this, e));
    }
    
    public StatoPilota getStato() { return stato; }
}