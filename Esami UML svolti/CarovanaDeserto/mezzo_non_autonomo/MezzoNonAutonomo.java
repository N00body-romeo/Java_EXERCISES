package mezzo_non_autonomo;

// import ..

public class MezzoNonAutonomo extends Mezzo {
    private TLPilota linkPilota;
    public static final int PILOTA_MOL_MIN = 1;
    
    public MezzoNonAutonomo(String t) {
        super(t);
    }
    
    public int quantiLinkPilota() {
        return (linkPilota != null) ? 1 : 0;
    }
    
    public TLPilota getLinkPilota() throws RuntimeException {
        if (quantiLinkPilota() < PILOTA_MOL_MIN)
            throw new RuntimeException("vincolo molteplicità minima");
        
        if (linkPilota != null &&
            !this.trasporta.contains(new TLTrasporta(this, linkPilota.getPersona())))
            throw new RuntimeException("vincolo subset");
        
        return linkPilota;
    }
    
    public void addLinkPilota(TLPilota l) {
        if (l != null && linkPilota == null && l.getMezzoNA().equals(this))
            linkPilota = l;
    }
    
    public void removeLinkPilota(TLPilota l) {
        if (linkPilota.equals(l))
            linkPilota = null;
    }
}