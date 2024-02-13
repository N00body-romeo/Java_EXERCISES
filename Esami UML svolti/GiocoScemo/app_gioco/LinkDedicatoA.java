package app_gioco;

import app_gioco.quadro_dedicato.*;

public class LinkDedicatoA {
    private final QuadroDedicato quadro;
    private final Personaggio personaggio;
    
    public LinkDedicatoA(QuadroDedicato q, Personaggio p) {
        this.quadro = q;
        this.personaggio = p;
    }
    
    public QuadroDedicato getQuadro() {
        return quadro;
    }
    
    public Personaggio getPersonaggio() {
        return personaggio;
    }
}