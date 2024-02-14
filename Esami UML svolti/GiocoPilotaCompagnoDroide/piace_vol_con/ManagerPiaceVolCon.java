package piace_vol_con;

// import ..

public final class ManagerPiaceVolCon {
    private TLPiaceVolCon link;
    
    private ManagerPiaceVolCon(TLPiaceVolCon l) {
        this.link = l;
    }
    
    public TLPiaceVolCon getLink() { return link; }
    
    public static void addLink(TLPiaceVolCon l) {
        if (l != null) {
            ManagerPiaceVolCon m = new ManagerPiaceVolCon(l);
            l.getPilota().addLinkPiaceVolConPerManager(m);
            l.getDroide().addLinkPiaceVolConPerManager(m);
        }
    }
    
    public static void removeLink(TLPiaceVolCon l) {
        if (l != null) {
            ManagerPiaceVolCon m = new ManagerPiaceVolCon(l);
            l.getPilota().removeLinkPiaceVolConPerManager(m);
            l.getDroide().removeLinkPiaceVolConPerManager(m);
        }
    }
}