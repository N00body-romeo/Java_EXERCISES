package contiene_batiscafo;

// import

public class TLContieneBatiscafo {
    private Sottomarino sottomarino;
    private Batiscafo batiscafo;
    
    public TLContieneBatiscafo(Sottomarino s, Batiscafo b) throws RuntimeException {
        if (s == null || b == null) throw new RuntimeException("Oggetti null nel link");
        this.sottomarino = s;
        this.batiscafo = b;
    }
    
    public Sottomarino getSottomarino() { return sottomarino; }
    public Batiscafo getBatiscafo() { return batiscafo; }
    
    public boolean equals(Object o) {
        if (o == null || !o.getClass().equals(this.getClass())) return false;
        
        TLContieneBatiscafo l = (TLContieneBatiscafo) o;
        return (o.sottomarino.equals(this.sottomarino) && o.batiscafo.equals(this.batiscafo));
    }
    
    public int hashCode() {
        return sottomarino.hashCode() + batiscafo.hashCode();
    }
}