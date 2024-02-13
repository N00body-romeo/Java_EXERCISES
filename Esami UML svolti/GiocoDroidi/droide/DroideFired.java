package droide;

// import ..;

public class DroideFired implements Task {
    private Evento e;
    private Droide d;
    private boolean eseguita = false;
    
    public DroideFired(Droide d, Evento e) {
        this.d = d;
        this.e = e;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void esegui() {
        if (eseguita) return;
        eseguita = true;
        
        // controlla broadcast o destinatario corretto
        if (e.getDest() != null && !e.getDest().equals(d)) return;
        
        switch (d.statoCorrente) {
            case StatoDroide.BASE:
                if (e.getClass() == Volare.class) {
                    Volare ev = (Volare) e;
                    Astronave a = ev.getAstronave();
                    if (a.getLinkPilota().getEquipaggio().getDroide().equals(d)) {
                        d.astronave = a;
                        d.umano = ev.getUmano();
                        Environment.aggiungiEvento(new RichiestaSalita(d, d.u));
                        d.statoCorrente = StatoDroide.ATTESA_CAPITANO;
                    }
                }
                break;
                
            case StatoDroide.ATTESA_CAPITANO:
                if (e.getClass() == CapitanoSalito.class) {
                    CapitanoSalito ev = (CapitanoSalito) e;
                    if (ev.getMitt().equals(d.umano)) {
                        d.umano.addLinkPilota(d.astronave);
                        d.statoCorrente = StatoDroide.IN_VOLO;
                    }
                }
                break;
            
            case StatoDroide.IN_VOLO:
                if (e.getClass() == Rientro.class) {
                    d.astronave = null;
                    d.umano = null;
                    d.statoCorrente = StatoDroide.BASE;
                }
                break;
                
            default:
                throw new RuntimeException("Stato non riconosciuto");
        }
    }
}