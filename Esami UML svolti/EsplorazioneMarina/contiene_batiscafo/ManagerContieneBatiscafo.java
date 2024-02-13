package contiene_batiscafo;

// import

public final class ManagerContieneBatiscafo {
    private TLContieneBatiscafo link;
    
    private ManagerContieneBatiscafo(TLContieneBatiscafo l) {
        this.link = l;
    }
    
    public static void addLink(TLContieneBatiscafo l) {
        if (l != null) {
            ManagerContieneBatiscafo m = new ManagerContieneBatiscafo(l);
            l.getSottomarino().addLinkContieneBatiscafoPerManager(m);
            l.getBatiscafo().addLinkContieneBatiscafoPerManager(m);
        }
    }
    
    public static void removeLink(TLContieneBatiscafo l) {
        if (l != null) {
            ManagerContieneBatiscafo m = new ManagerContieneBatiscafo(l);
            l.getSottomarino().removeLinkContieneBatiscafoPerManager(m);
            l.getBatiscafo().removeLinkContieneBatiscafoPerManager(m);
        }
    }
    
    public TLContieneBatiscafo getLink() { return link; }
}