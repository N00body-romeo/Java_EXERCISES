package partecipa;

// import ..;

public class TLPartecipa {
    private Gioco gioco;
    private Giocatore giocatore;
    
    public TLPartecipa(Gioco g, Giocatore gi) throws RuntimeException {
        if (g == null || gi == null) throw new RuntimeException("Riferimento a null");
        this.gioco = g;
        this.giocatore = gi;
    }
    
    public Gioco getGioco() { return gioco; }
    public Giocator getGiocatore() { return Giocatore; }
    
    public boolean equals(Object o) {
        if (o == null || !o.getClass().equals(getClass())) return false;
        
        TLPartecipa p = (TLPartecipa) o;
        return gioco.equals(p.gioco) && giocatore.equals(p.giocatore);
    }
    
    public int hashCode() {
        return gioco.hashCode() + giocatore.hashCode();
    }
}