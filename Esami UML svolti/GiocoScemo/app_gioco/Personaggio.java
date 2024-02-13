package app_gioco;

public class Personaggio {
    private String immagine;
    private HashSet<Quadro> presenteIn;
    private HashSet<LinkDedicatoA> dedicatoA;
    
    public Personaggio() {
        presenteIn = new HashSet<Quadro>();
        dedicatoA = new HashSet<LinkDedicatoA>();
    }
    
    public void setImmagine(String im) {
        this.immagine = im;
    }
    
    public String getImmagine() {
        return this.immagine;
    }
    
    public Set<Quadro> getPresenteIn() {
        return presenteIn.clone();
    }

    public void addPresenteIn(Quadro l) {
        if (l != null && !presenteIn.contains(l)) {
            presenteIn.add(l);
        }
    }

    public void removePresenteIn(Quadro l) {
        if (l != null) {
            presenteIn.remove(l);
        }
    }
    
    public Set<LinkDedicatoA> getDedicatoA() {
        return dedicatoA.clone();
    }
    
    public void addDedicatoA(ManagerDedicatoA m) {
        if (m != null && !dedicatoA.contains(m.getLink()) && m.getLink().getPersonaggio().equals(this)) {
            dedicatoA.add(m.getLink());
        }
    }
    
    public void removeDedicatoA(ManagerDedicatoA m) {
        if (m != null && dedicatoA.contains(m.getLink())) {
            dedicatoA.remove(m.getLink());
        }
    }
}