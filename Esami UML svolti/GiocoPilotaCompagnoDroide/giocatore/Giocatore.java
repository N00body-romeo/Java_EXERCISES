package giocatore;

// import ..

public abstract class Giocatore {
    protected String nome;
    
    public Giocatore(String n) {
        this.nome = n;
    }
    
    public String getNome() { return nome; }
}