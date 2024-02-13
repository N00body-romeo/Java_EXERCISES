package contiene_sottomarino;

// import

public final class ManagerContieneSottomarino {
    private TLContieneSottomarino link;
    
    private ManagerContieneSottomarino(TLContieneSottomarino l) {
        this.link = l;
    }
    
    public static void addLink(TLContieneSottomarino l) {
        if (l != null) {
            ManagerContieneSottomarino m = new ManagerContieneSottomarino(l);
            l.getBarca().addLinkContieneSottomarinoPerManager(m);
            l.getSottomarino().addLinkContieneSottomarinoPerManager(m);
        }
    }
    
    public static void removeLink(TLContieneSottomarino l) {
        if (l != null) {
            ManagerContieneSottomarino m = new MManagerContieneSottomarino(l);
            l.getBarca().removeLinkContieneSottomarinoPerManager(m);
            l.getSottomarino().removeLinkContieneSottomarinoPerManager(m);
        }
    }
    
    public TLContieneSottomarino getLink() { return link; }
}