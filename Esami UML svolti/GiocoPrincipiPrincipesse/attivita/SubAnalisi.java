package attivita;

// import ..

public class SubAnalisi implements Runnable {
    private Gioco g;
    private boolean eseguita = false;
    
    public SubAnalisi(Gioco g) {
        this.g = g;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;
        
        CalcoloGiocatori calc = new CalcoloGiocatori(g);
        TaskExecutor.getInstance().perform(calc);
        String resCalc = calc.getRis();
        
        IO.stampaGiocatori(resCalc);
    }
}