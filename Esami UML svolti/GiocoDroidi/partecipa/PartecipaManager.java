package partecipa;

// import ..;

public final class PartecipaManager {
    private TLPartecipa link;
    
    private PartecipaManager(TLPartecipa link) {
        this.link = link;
    }
    
    public TLPartecipa getLink() { return link; }
    
    public static void addLink(TLPartecipa link) {
        if (link != null) {
            link.getGioco().addLinkPartecipaViaManager(new PartecipaManager(link));
            link.getGiocatore().addLinkPartecipaViaManager(new PartecipaManager(link));
        }
    }
    
    public static void removeLink(TLPartecipa link) {
        if (link != null) {
            link.getGioco().removeLinkPartecipaViaManager(new PartecipaManager(link));
            link.getGioco().removeLinkPartecipaViaManager(new PartecipaManager(link));
        }
    }
}