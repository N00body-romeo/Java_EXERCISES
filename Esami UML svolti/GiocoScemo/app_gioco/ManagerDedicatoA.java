package app_gioco;

public class ManagerDedicatoA {
    private final LinkDedicatoA link;
    
    private ManagerDedicatoA(LinkDedicatoA link) {
        this.link = link;
    }
    
    public LinkDedicatoA getLink() {
        return link;
    }
    
    public static void addLink(LinkDedicatoA l) {
        if (l != null) {
            ManagerDedicatoA m = new ManagerDedicatoA(l);
            l.getPersonaggio().addLinkDedicatoA(m);
            l.getQuadro().addLinkDedicatoA(m);
        }
    }
    
    public static void removeLink(LinkDedicatoA l) {
        if (l != null) {
            ManagerDedicatoA m = new ManagerDedicatoA(l);
            l.getPersonaggio().removeLinkDedicatoA(m);
            l.getQuadro().addLinkDedicatoA(m);
        }
    }
}