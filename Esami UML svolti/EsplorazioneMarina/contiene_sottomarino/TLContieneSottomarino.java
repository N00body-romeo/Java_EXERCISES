package contiene_sottomarino;

// import

public class TLContieneSottomarino {
    private BarcaTrasporto barca;
    private Sottomarino sottomarino;
    
    public TLContieneSottomarino(BarcaTrasporto b, Sottomarino s) throws RuntimeException {
        if (b == null || s == null) throw new RuntimeException("Oggetti null nel link");
        
        this.barca = b;
        this.sottomarino = s;
    }
    
    public BarcaTrasporto getBarca() { return barca; }
    public Sottomarino getSottomarino() { return sottomarino; }
    
    public boolean equals(Object o) {
        if (o != null && o.getClass().equals(this.getClass)) {
            TLContieneSottomarino s = (TLContieneSottomarino) o;
            return (s.barca.equals(this.barca) && s.sottomarino.equals(this.sottomarino));
        }
        
        return false;
    }
    
    public int hashCode() {
        return barca.hashCode() + sottomarino.hashCode();
    }
}