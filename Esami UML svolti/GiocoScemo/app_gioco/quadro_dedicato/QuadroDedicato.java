package app_gioco.quadro_dedicato;

import app_gioco.quadro.*;

public class QuadroDedicato extends Quadro {
    private String filmato;
    private LinkDedicatoA dedicatoA;
    
    public QuadroDedicato() {
        dedicatoA = null;
    }
    
    public String getFilmato() {
        return filmato;
    }
    
    public void setFilmato(String f) {
        filmato = f;
    }
    
    public LinkDedicatoA getLinkDedicatoA() throws EccezioneMolteplicita, EccezioneSubset {
        if (dedicatoA != null)
            throw new EccezioneMolteplicita("molteplicità minima violata");
        if (!dedicatoA.getPersonaggio().getPresenteIn().contains(this))
            throw new EccezioneSubset("dedicatoA non è subset di presenteIn");
        
        return dedicatoA;
    }
    
    public void addLinkDedicatoA(ManagerDedicatoA m) {
        if (m != null && dedicatoA == null && m.getLink().getQuadro().equals(this)) {
            dedicatoA = m.getLink();
        }
    }
    
    public void removeLinkDedicatoA(ManagerDedicatoA m) {
        if (m != null && dedicatoA.getPersonaggio().equals(m.getLink().getPersonaggio()) &&
            m.getLink().getQuadro().equals(this)) {
            dedicatoA = null;
        }
    }
}