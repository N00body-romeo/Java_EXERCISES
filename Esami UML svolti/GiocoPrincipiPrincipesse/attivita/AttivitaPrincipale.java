package attivita;

// import ..

public class AttivitaPrincipale implements Runnable {
    private Gioco g;
    private boolean eseguita = false;
    
    public AttivitaPrincipale(Gioco g) {
        this.g = g;
    }
    
    public synchronized boolean getEseguita() { return eseguita; }
    
    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;
        
        Thread tGioco = new Thread(new SubGioco(g)),
               tAnalisi = new Thread(new SubAnalisi(g));
        
        tGioco.start(); tAnalisi.start();
        
        try {
            tGioco.join(); tAnalisi.join();
        } catch (InterruptException e) {
            e.printStackTrace();
        }
        
        CalcoloInn taskCalcoloInn = new CalcoloInn(g);
        TaskExecutor.getInstance().perform(taskCalcoloInn);
        String resInn = taskCalcoloInn.getRis();
        
        IO.stampaInn(resInn);
    }
}