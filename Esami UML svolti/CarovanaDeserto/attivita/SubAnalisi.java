package attivita;

// import ..

public class SubAnalisi implements Runnable {
    private Carovana c;
    private double perc; 
    private boolean eseguita = false;

    public SubEsecuzione(Carovana c) {
        this.c = c;
    }

    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized double getRis() { return perc; }

    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;

        CalcoloPerc calc = new CalcoloPerc(c);
        TaskExecutor.getInstance().perform(calc);
        perc = calc.getRis();
    }
}