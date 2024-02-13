package torello;

public final class ManagerPartecipaRosso {
    private final LinkPartecipaRosso link;
    
    private ManagerPartecipaRosso(LinkPartecipaRosso l) {
        this.link = l;
    }
    
    public LinkPartecipaRosso getLink() { return link; }
    
    public static void addLink(LinkPartecipaRosso l) {
        if (l != null) {
            ManagerPartecipaRosso m = new ManagerPartecipaRosso(l);
            l.getGiocatore().addLinkPartecipaRosso(m);
            l.getTorello().addLinkPartecipaRosso(m);
        }
    }
    
    public static void removeLink(LinkPartecipaRosso l) {
        if (l != null) {
            ManagerPartecipaRosso m = new ManagerPartecipaRosso(l);
            l.getGiocatore().removeLinkPartecipaRosso(m);
            l.getTorello().removeLinkPartecipaRosso(m);
        }
    }
}