package torello.giocatore;

public abstract class Giocatore implements Listener {
    protected final String nome;
    protected String avatar;
    protected StatoGiocatore statoCorrente;
    
    public static enum StatoGiocatore {
        NON_IN_GIOCO,
        IN_GIOCO
    }
    
    protected Giocatore(String n) {
        this.nome = n;
        this.statoCorrente = StatoGiocatore.NON_IN_GIOCO;
    }
    
    public String getNome() { return nome; }
    public String getAvatar() { return avatar; }
    public StatoGiocatore getStato() { return statoCorrente; }
    
    public void setAvatar(String a) throws RuntimeException {
        if (statoCorrente != NON_IN_GIOCO)
            throw new RuntimeException("stato diverso da NON_IN_GIOCO durante la modifica");
        
        avatar = a;
    }
    
    public void fired(Evento e) {
        Executor.perform(new GiocatoreFired(this, e));
    }
}