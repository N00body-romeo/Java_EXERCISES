package app_gioco;

public final class ManagerGioca {
    private final LinkGioca link;
    
    private ManagerGioca(LinkGioca _link) {
        this.link = _link;
    }
    
    public LinkGioca getLink() {
        return link;
    }
    
    public static void addLink(LinkGioca l) {
        if (l != null) {
            ManagerGioca m = new ManagerGioca(l);
            l.getPartita().addLinkGioca(m);
            l.getQuadro().addLinkGioca(m);
        }        
    }
    
    public static void removeLink(LinkGioca l) {
        if (l != null) {
            ManagerGioca m = new ManagerGioca(l);
            l.getPartita().removeLinkGioca(m);
            l.getQuadro().removeLinkGioca(m);
        }
    }
}