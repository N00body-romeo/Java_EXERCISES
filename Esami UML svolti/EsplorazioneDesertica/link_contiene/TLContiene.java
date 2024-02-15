package link_contiene;

// import ..

public class TLContiene {
    private Base base;
    private DispostivoRobotico disp;
    
    public TLContiene(Base b, DispositivoRobotico d) throws RuntimeException {
        if (b == null || d == null)
            throw new RuntimeException("riferimento a null");
        
        this.base = b;
        this.disp = d;
    }
    
    public Base getBase() { return base; }
    public DispositivoRobotico getDispositivoRobotico() { return disp; }
    
    public boolean equals(Object o) {
        if (o == null || !this.getClass().equals(o.getClass())) return false;
        
        TLContiene l = (TLContiene) o;
        return l.base == base && l.disp == disp;
    }
    
    public int hashCode() {
        return base.hashCode() + disp.hashCode();
    }
}