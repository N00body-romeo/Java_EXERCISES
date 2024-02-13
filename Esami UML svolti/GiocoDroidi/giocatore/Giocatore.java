package giocatore;

// import ..;

public abstract class Giocatore {
    protected String nome;
    protected TLPartecipa partecipa;
    private static final int LINK_PARTECIPA_MIN = 1;
    private static final int LINK_PARTECIPA_MAX = 1;
    
    public Giocatore(String n) {
        this.nome = n;
    }
    
    public String getNome() { return nome; }
    
    public int quantiLinkPartecipa() {
        if (partecipa != null) return 1;
        else return 0;
    }
    
    public TLPartecipa getPartecipa() throws RuntimeException {
        if (quantiLinkPartecipa() < LINK_PARTECIPA_MIN) throw new RuntimeException("Errore molteplicità minima");
        return partecipa;
    }
    
    public void addLinkPartecipa(TLPartecipa l) throws RuntimeException {
        if (quantiLinkPartecipa() == LINK_PARTECIPA_MAX) throw new RuntimeException("Errore molteplicità massima");
        if (l != null && l.getGiocatore().equals(this))
            PartecipaManager.addLink(l);
    }
    
    public void removeLinkPartecipa(TLPartecipa l) {
        if (l != null && l.getGiocatore().equals(this))
            PartecipaManager.removeLink(l);
    }
    
    public void addLinkPartecipaViaManager(PartecipaManager m) {
        if (m != null) partecipa = m.getLink();
    }
    
    public void removeLinkPartecipaViaManager(PartecipaManager m) {
        if (m != null && m.getLink().equals(partecipa)) partecipa = null;
    }
}