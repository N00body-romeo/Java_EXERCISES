package link_trasporta;

public final class ManagerTrasporta {
    private TLTrasporta link;
    
    private Manager(TLTrasporta l) {
        this.link = l;
    }
    
    public TLTrasporta getLink() { return link; }
    
    public static addLink(TLTrasporta l) {
        if (l != null && l.getPersona().getLinkTrasporta() == null) {
            ManagerTrasporta m = new ManagerTrasporta(l);
            l.getMezzo().addLinkTrasportaPerManager(m);
            l.getPersona().addLinkTrasportaPerManager(m);
        }
    }
    
    public static removeLink(TLTrasporta l) {
        if (l != null &&
            l.getMezzo().getLinkTrasporta().contains(l) &&
            l.getPersona().getLinkTrasporta().equals(l)) {
            
            ManagerTrasporta m = new ManagerTrasporta(l);
            l.getMezzo().removeLinkTrasportaPerManager(m);
            l.getPersona().removeLinkTrasportaPerManager(m);
        }
    }
}