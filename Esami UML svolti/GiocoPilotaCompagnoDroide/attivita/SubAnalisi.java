package attivita;

// import ..

public class SubAnalisi implements Runnable {
    private Gioco g;
    private boolean eseguita = false;

    public AttPrinc(Gioco g) {
        this.g = g;
    }

    public synchronized boolean estEseguita() { return eseguita; }

    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;

        Calcolo calc = new Calcolo(g);
        TaskExecutor.getInstance().perform(calc);
        
        String res = calc.getRis();
        IO.stampaCal(res);
    }
}