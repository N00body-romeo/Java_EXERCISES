package droide;

// imports ..;

public class Droide extends Giocatore implements Listener {
    private String modello;
    
    public Droide(String n, String mod) {
        super(n);
        this.modello = mod;
    }
    
    public String getModello() { return modello; }
    
    // Gestione stati
    enum StatoDroide {
        BASE, ATTESA_CAPITANO, IN_VOLO
    }
    
    StatoDroide statoCorrente = StatoDroide.BASE;
    Umano umano = null;
    Astronave astronave = null;
    
    public void fired(Evento e) {
        TaskExecutor.getInstance().perform(new DroideFired(this, e));
    }
    
    public StatoDroide getStato() { return statoCorrente; }
}