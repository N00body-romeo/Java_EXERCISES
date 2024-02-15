package attivita;

// import ..

public class SubVerifica implements Runnable {
    private Esplorazione e;
    private boolean eseguita = false;
    
    public SubVerifica(Esplorazione e) {
        this.e = e;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;
        
        Calcolo calc = new Calcolo(e);
        TaskExecutor.getInstance().perform(calc);
        String res = calc.getRis();
        
        IO.stampaCalc(res);
    }
}