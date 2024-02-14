package compagno_di_volo;

// import ..

public class TLCompagnoDiVolo {
    private Pilota pilota;
    private Droide droide;
    
    public TLCompagnoDiVolo(Pilota p, Droide d) throws RuntimeException {
        if (p == null || d == null)
            throw new RuntimeException("riferimenti null");
        
        this.pilota = p;
        this.droide = d;
    }
    
    public Pilota getPilota() { return pilota; }
    public Droide getDroide() { return droide; }
    
    public boolean equals(Object o) {
        if (o == null || !o.getClass().equals(this.getClass())) return false;
        
        TLCompagnoDiVolo l = (TLTLCompagnoDiVolo) o;
        return (l.pilota == pilota && l.droide == droide);
    }
    
    public int hashCode() {
        return pilota.hashCode() + droide.hashCode();
    }
}