package mezzo;

// import ..

public abstract class Mezzo implements Listener {
    protected String tipo;
    protected Set<TLTrasporta> trasporta;
    public static final int TRASPORTA_MOL_MIN = 1;
    
    public Mezzo(String t) {
        this.tipo = t;
        this.trasporta = new HashSet<TLTrasporta>();
    }
    
    public String getTipo() { return tipo; }
    
    public int quantiTrasporta() { return trasporta.size(); }
    
    public Set<TLTrasporta> getLinkTrasporta() throws RuntimeException {
        if (quantiTrasporta() < TRASPORTA_MOL_MIN)
            throw new RuntimeException("vincolo molteplicità minima");
        
        return trasporta.clone();
    }
    
    public void addLinkTrasporta(TLTrasporta l) {
        if (l != null && l.getMezzo().equals(this))
            ManagerTrasporta.addLink(l);
    }
    
    public void removeLinkTrasporta(TLTrasporta l) {
        if (l != null && l.getMezzo().equals(this))
            ManagerTrasporta.removeLink(l);
    }
    
    public void addLinkTrasportaPerManager(ManagerTrasporta m) {
        if (m != null)
            trasporta.add(m.getLink());
    }
    
    public void removeLinkTrasportaPerManager(ManagerTrasporta m) {
        if (m != null)
            trasporta.remove(m.getLink());
    }
    
    // Gestione stati
    enum StatoMezzo { A_RIPOSO, IN_MARCIA, ATTESA, IN_TRASBORDO }
    StatoMezzo statoCorrente = A_RIPOSO;
    Mezzo m;
    
    public void fired(Evento e) {
        TaskExecutor.getInstance().peform(new MezzoFired(this, e));
    }
    
    public StatoMezzo getStato() { return statoCorrente; }
}