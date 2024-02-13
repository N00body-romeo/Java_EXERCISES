package giocatore;

// import ..

public abstract class Giocatore {
    protected String nome;
    protected TLParteDiGioco parteDi;
    public static final int PARTEDI_MOL_MIN = 1;
    
    public Giocatore(String n) {
        this.nome = n;
    }
    
    public String getNome() { return nome; }
    
    public int quantiParteDi() { return (parteDi == null) ? 1 : 0; }
    
    public TLParteDiGioco getParteDiGioco() throws RuntimeException {
        if (quantiParteDi() < PARTEDI_MOL_MIN) throw new RuntimeException("errore molteplicità minima");
        else return parteDi;
    }
    
    public void addLinkParteDiGioco(TLParteDiGioco l) {
        if (l != null && l.getGiocatore().equals(this) && quantiParteDi() < 1)
            ManagerParteDiGioco.addLink(l);
    }
    
    public void removeLinkParteDiGioco(TLParteDiGioco l) {
        if (l != null && parteDi.equals(l))
            ManagerParteDiGioco.removeLink(l);
    }
    
    public void addLinkParteDiGiocoPerManager(ManagerParteDiGioco m) {
        if (m != null) parteDi = m.getLink();
    }
    
    public void removeLinkParteDiGiocoPerManager(ManagerParteDiGioco m) {
        if (m != null) parteDi = null;
    }
}