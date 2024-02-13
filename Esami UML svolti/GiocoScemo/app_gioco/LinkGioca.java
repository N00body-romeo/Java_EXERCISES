package app_gioco;

public class LinkGioca {
    private final Partita partita;
    private final Quadro quadro;
    private final int punti;
    
    public LinkGioca(Partita _partita, Quadro _quadro, int _punti) throws EccezionePrecondizioni {
        if (_partita == null || _quadro == null)
            throw new EccezionePrecondizioni("oggetti non inizializzati");
        
        this.partita = _partita;
        this.quadro = _quadro;
        this.punti = _punti;
    }
    
    public Partita getPartita() {
        return partita;
    }
    
    public Quadro getQuadro() {
        return quadro;
    }
    
    public int getPunti() {
        return punti;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o != null && getClass().equals(o.getClass())) {
            LinkGioca b = (LinkGioca) o;
            return partita == b.partita && quadro == b.quadro;
        } else return false;
    }
    
    @Override
    public int hashCode() {
        return partita.hashCode() + quadro.hashCode();
    }
}