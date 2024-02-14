package compagno_di_volo;

// import ..

public final class ManagerCompagnoDiVolo {
    private TLCompagnoDiVolo link;
    
    private ManagerCompagnoDiVolo(TLCompagnoDiVolo l) {
        this.link = l;
    }
    
    public TLCompagnoDiVolo getLink() { return link; }
    
    public static void addLink(TLCompagnoDiVolo l) {
        if (l != null/* &&
            l.getPilota().getLinkCompagnoDiVolo() == null &&
            l.getDroide().getLinkCompagnoDiVolo() == null*/) {
            ManagerCompagnoDiVolo m = new ManagerCompagnoDiVolo(l);
            l.getPilota().addLinkCompagnoDiVoloPerManager(m);
            l.getDroide().addLinkCompagnoDiVoloPerManager(m);
        }
    }
    
    public static void removeLink(TLCompagnoDiVolo l) {
        if (l != null) {
            ManagerCompagnoDiVolo m = new ManagerCompagnoDiVolo(l);
            l.getPilota().removeLinkCompagnoDiVoloPerManager(m);
            l.getDroide().removeLinkCompagnoDiVoloPerManager(m);
        }
    }
}