package mezzo_autonomo;

// import ..

public class MezzoAutonomo extends Mezzo {
    private int annoColl;
    
    public MezzoAutonomo(String t, int a) {
        super(t);
        this.annoColl = a;
    }
    
    public int getAnnoColl() { return annoColl; }
}