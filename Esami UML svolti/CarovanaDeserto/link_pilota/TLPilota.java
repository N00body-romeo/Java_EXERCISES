package link_pilota;

// import ..

public class TLPilota {
    private Persona persona;
    private MezzoNonAutonomo mezzoNA;
    
    public TLPilota(Persona p, MezzoNonAutonomo mna) throws RuntimeException {
        if (p == null || mna == null)
            throw new RuntimeException("riferimenti null");
        
        this.persona = p;
        this.mezzoNA = mna;
    }
    
    public Persona getPersona() { return persona; }
    public MezzoNonAutonomo getMezzoNA() { return mezzoNA; }
    
    public boolean equals(Object o) {
        if (o == null || !o.getClass().equals(this)) return false;
        
        TLPilota l = (TLPilota) o;
        return l.persona.equals(this.persona) && l.mezzoNA.equals(this.mezzoNA);
    }
    
    public int hashCode() {
        return persona.hashCode() + mezzoNA.hashCode();
    }
}