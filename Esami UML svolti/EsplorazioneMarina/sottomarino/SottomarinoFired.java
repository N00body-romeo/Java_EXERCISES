package sottomarino;

// import

public class SottomarinoFired implements Task {
    private Sottomarino s;
    private Evento e;
    private boolean eseguita = false;
    
    public SottomarinoFired(Sottomarino s, Evento e) {
        this.s = s;
        this.e = e;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void esegui() throws RuntimeException {
        if (eseguita) return;
        eseguita = true;
        
        if (e.getDest() != null && !e.getDest().equals(s)) return;
        
        switch (s.statoCorrente) {
            case StatoSottomarino.A_BORDO:
                if (e.getClass() == Immersione.class) {
                    Immersione ev = (Immersione) e;
                    
                    // precondizione
                    if (!ev.getMitt().equals(s.getContieneSottomarino().getBarca())) return;
                    
                    s.p = ev.profondita;
                    
                    if (ev.profondita <= b.getProfMax()) {
                        s.immergi(s.p);
                        s.avviaRegistrazione();
                        s.statoCorrente = StatoSottomarino.IMMERSO;
                        
                    } else {
                        s.b = s.scegliBatiscafoCasuale();
                        s.immergi(s.getProfMax());
                        s.b.immergi(p);
                        s.b.avviaRegistrazione();
                        s.statoCorrente = StatoSottomarino.IMMERSO_BATISCAFO;
                    }
                }
                break;
                
            case StatoSottomarino.IMMERSO:
                if (e.getClass() == Risalita.class) {
                    Risalita ev = (Risalita) e;
                    
                    s.p = 0;
                    s.statoCorrente = StatoSottomarino.A_BORDO;
                }
                break;
                
            case StatoSottomarino.IMMERSO_BATISCAFO:
                if (e.getClass() == Risalita.class) {
                    Risalita ev = (Risalita) e;
                    
                    Environment.aggiungiEvento(new Rientro(this, s.b));
                    s.statoCorrente = StatoSottomarino.ATTESA_RIENTRO;
                }
                break;
                
            case StatoSottomarino.ATTESA_RIENTRO:
                if (e.getClass() == RientroEffettuato.class) {
                    RientroEffettuato ev = (RientroEffettuato) e;
                    
                    if (!ev.getMitt().equals(s.b)) return;
                    
                    s.p = 0;
                    s.b = null;
                    s.statoCorrente = StatoSottomarino.A_BORDO;
                }
                break;
                
            default:
                throw new RuntimeException("Stato non riconosciuto");
        }
    }
}