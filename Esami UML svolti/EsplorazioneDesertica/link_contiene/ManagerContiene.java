package link_contiene;

// import ..

public final class ManagerContiene {
    private TLContiene link;
    
    private ManagerContiene(TLContiene l) {
        this.link = l;
    }
    
    public TLContiene getLink() { return link; }
    
    public static void addLink(TLContiene l) {
        if (l != null &&
            l.getDispositivoRobotico().getLinkContiene() == null) {
            
            ManagerContiene m = new ManagerContiene(l);
            l.getBase().addLinkContienePerManager(m);
            l.getDispositivoRobotico().addLinkContienePerManager(m);
        }
    }
    
    public static void removeLink(TLContiene l) {
        if (l != null &&
            l.getDispositivoRobotico().getLinkContiene().equals(l)) {
            
            ManagerContiene m = new ManagerContiene(l);
            l.getBase().removeLinkContienePerManager(m);
            l.getDispositivoRobotico().removeLinkContienePerManager(m);
        }
    }
}