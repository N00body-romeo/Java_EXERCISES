package attivita;

// import ..

public class AttivitaPrincipale implements Runnable {
    private Carovana c;
    private int n;
    private boolean eseguita = false;
    
    public AttivitaPrincipale(Carovana c, int n) throws RuntimeException {
        if (n <= 0) throw new RuntimeException("n non positivo");
        this.c = c;
        this.n = n;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void run() {
        if (eseguita) return;
        eseguita = true;
        
        VerificaNum verifica = new VerificaNum(c, n);
        TaskExecutor.getInstance().perform(verifica);
        boolean res = verifica.getRis();
        
        if (res) {
            SubAnalisi subAnalisi = new SubAnalisi(c);
            Thread tEsec = new Thread(new SubEsecuzione(c)),
                   tAnalisi = new Thread(subAnalisi);
            
            tEsec.start(); tAnalisi.start();
            
            try {
                tEsec.join(); tAnalisi.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            double perc = subAnalisi.getRis();
            IO.stampaPerc(perc);
            
        } else {
            IO.stampaErr();
        }
    }
}