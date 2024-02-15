package dispositivo_robotico;

// import ..

public abstract class DispositivoRobotico {
    protected String nome;
    protected double lat, lon;
    protected int raggio;
    protected TLContiene linkContiene;
    
    private static final int CONTIENE_MOL_MIN = 1;
    
    public DispositivoRobotico(String n, double la, double lo, int r) {
        this.nome = n;
        this.lat = la;
        this.lon = lo;
        this.raggio = r;
    }
    
    public String getNome() { return nome; }
    public double getLat() { return lat; }
    public double getLong() { return lon; }
    public int getRaggio() { return raggio; }
    
    public int quantiLinkContiene() { return (linkContiene == null) ? 0 : 1; }
    
    public TLContiene getLinkContiene() throws RuntimeException {
        if (quantiLinkContiene() < CONTIENE_MOL_MIN)
            throw new RuntimeException("vincolo molteplicità minima");
        
        return linkContiene;
    }
    
    public void addLinkContiene(TLContiene l) {
        if (l != null)
            ManagerContiene.addLink(l);
    }
    
    public void removeLinkContiene(TLContiene l) {
        if (l != null)
            ManagerContiene.removeLink(l);
    }
    
    public void addLinkContienePerManager(ManagerContiene m) {
        if (m != null)
            linkContiene = m.getLink();
    }
    
    public void removeLinkContienePerManager(ManagerContiene m) {
        if (m != null)
            linkContiene = null;
    }
}