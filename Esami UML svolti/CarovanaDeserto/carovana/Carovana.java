package carovana;

// import ..

public class Carovana {
    private String nome;
    private Set<Mezzo> costituita;
    public static final int COSTITUITA_MOL_MIN = 1;
    
    public Carovana(String n) {
        this.nome = n;
        this.costituita = new HashSet<>();
    }
    
    public String getNome() { return nome; }
    
    public int quantiLinkCostituita() { return costituita.size(); }
    
    public Set<Mezzo> getLinkCostituita() throws RuntimeException {
        if (quantiLinkCostituita() < COSTITUITA_MOL_MIN)
            throw new RuntimeException("vincolo molteplicità minima");
        
        return costituita.clone();
    }
    
    public void addLinkCostituita(Mezzo m) {
        if (m != null)
            costituita.add(m);
    }
    
    public void removeLinkCostituita(Mezzo m) {
        if (m != null)
            costituita.remove(m);
    }
}