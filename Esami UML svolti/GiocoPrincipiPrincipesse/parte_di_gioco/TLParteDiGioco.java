package parte_di_gioco;

// import ..

public class TLParteDiGioco {
    private Gioco gioco;
    private Giocatore giocatore;
    
    public TLParteDiGioco(Gioco g, Giocatore ga) throws RuntimeException {
        if (g == null || ga == null) throw new RuntimeException("valori null");
        this.gioco = g;
        this.giocatore = ga;
    }
    
    public Gioco getGioco() { return gioco; }
    public Giocatore getGiocatore() { return giocatore; }
    
    public boolean equals(Object o) {
        if (o == null || !o.getClass().equals(this.getClass())) return false;
        
        TLParteDiGioco l = (TLParteDiGioco) o;
        return l.gioco == this.gioco && l.giocatore == this.giocatore;
    }
    
    public int hashCode() {
        return gioco.hashCode() + giocatore.hashCode();
    }
}