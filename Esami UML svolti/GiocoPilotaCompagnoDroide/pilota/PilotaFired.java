package pilota;

// import ..

class PilotaFired implements Task {
    private Pilota pilota;
    private Evento e;
    private boolean eseguita = false;
    
    PilotaFired(Pilota p, Evento e) {
        this.pilota = p;
        this.e = e;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void esegui() throws RuntimeException {
        if (eseguita) return;
        eseguita = true;
        
        if (e.getDest() != null && !e.getDest().equals(this)) return;
        
        switch (pilota.stato) {
            case BASE:
                if (e.getClass() == Volare.class) {
                    Volare ev = (Volare) e;
                    Astronave as = ev.getAstronave();
                    Passeggero po = ev.getPasseggero();
                    
                    if (pilota.getPuoPilotare().contains(as)) {
                        pilota.a = as;
                        pilota.p = po;
                        
                        Droide cv = pilota.getLinkCompagnoDiVolo().getDroide();                        
                        Environment.aggiungiEvento(new Salire(pilota, cv, pilota.a));
                        
                        pilota.stato = ATTESA_DROIDE;
                    }
                }
                break;
                
            case ATTESA_DROIDE:
                if (e.getClass() == DroideSalito.class) {
                    if (!e.getMitt().equals(pilota.getLinkCompagnoDiVolo().getDroide())) return;
                    
                    Environment.aggiungiEvento(new Salire(pilota, pilota.p, pilota.a));
                    
                    pilota.stato = ATTESA_PASSEGGERO;
                }
                break;
                
            case ATTESA_PASSEGGERO:
                if (e.getClass() == PasseggeroSalito.class) {
                    if (!e.getMitt().equals(pilota.p)) return;
                    
                    pilota.stato = VOLO;
                }
                break;
                
            case VOLO:
                if (e.getClass() == Rientro.class) {
                    pilota.a = null;
                    pilota.p = null;
                    pilota.stato = BASE;
                }
                break;
                
            default:
                throw new RuntimeException("stato non riconosciuto");
        }
    }
}