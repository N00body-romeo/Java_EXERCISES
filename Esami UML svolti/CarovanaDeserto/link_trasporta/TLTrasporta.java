package link_trasporta;

// import ..

public class TLTrasporta {
    private Mezzo mezzo;
    private Persona persona;
    
    public TLTrasporta(Mezzo m, Persona p) throws RuntimeException {
        if (m == null || p == null)
            throw new RuntimeException("riferimenti null");
        
        this.mezzo = m;
        this.persona = p;
    }
    
    public Mezzo getMezzo() { return mezzo; }
    public Persona getPersona() { return persona; }
    
    public boolean equals(Object o) {
        if (o == null || !o.getClass().equals(this)) return false;
        
        TLTrasporta l = (TLTrasporta) o;
        return this.mezzo.equals(l.mezzo) && this.persona.equals(l.persona);
    }
    
    public int hashCode() {
        return mezzo.hashCode() + persona.hashCode();
    }
}