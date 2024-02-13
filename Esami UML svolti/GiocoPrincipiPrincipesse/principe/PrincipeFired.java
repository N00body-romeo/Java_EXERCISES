package principe;

// import ..

class PrincipeFired implements Task {
    private Principe p;
    private Evento e;
    private boolean eseguita = false;
    
    PrincipeFired(Principe p, Evento e) {
        this.p = p;
        this.e = e;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void esegui() throws RuntimeException {
        if (eseguita) return;
        eseguita = true;
        
        if (e.getMitt() != null && !e.getMitt().equals(this)) return;
        
        switch (principe.statoCorrente) {
            case CORTE:
                if (e.getClass() == Bacio.class) {
                    Bacio ev = (Bacio) e;
                    
                    // precondizioni
                    if (ev.getMitt().getClass() != Principessa.class) return;
                    Principessa mitt = (Principessa) ev.getMitt();
                    
                    // condizione
                    if (p.getPiaceLa().contains(mitt) && p.getInnamoratoDi() == null) {
                        p.addLinkInnamoratoDi(mitt);
                    }
                    
                } else if (e.getClass() == ComandoImpresa.class) {
                    ComandoImpresa ev = (ComandoImpresa) e;
                    
                    // precondizioni
                    if (ev.getMitt().getClass() != Principessa.class) return;
                    Principessa mitt = (Principessa) ev.getMitt();
                    TLHaAccesso linkPiSpada = new TLHaAccesso(mitt, ev.getSpada());
                    
                    // condizione
                    if (mitt.getHaAccesso().contains(linkPiSpada)) {
                        mitt.removeLinkHaAccesso(linkPiSpada);
                        
                        p.pi = mitt;
                        p.d = ev.getDrago();
                        p.s = ev.getSpada();
                        p.statoCorrente = StatoPrincipe.IMPRESA;
                    }
                }
                break;
                
            case IMPRESA:
                if (e.getClass() == Attacco.class) {
                    Attacco ev = (Attacco) e;
                    
                    // precondizioni
                    if (!ev.getMitt().equals(p.d)) return;
                    boolean successo = false;
                    
                    if (p.d.quantiControllatoDa() > 0) {
                        for (TLControlla controlla : p.d.getControllatoDa()) {
                            if (p.s.getRompeIncantesimi().contains(controlla.getMago())) {
                                successo = true;
                                break;
                            }
                        }
                    } else successo = true;
                    
                    if (successo) {
                        p.pi.addLinkHaAccesso(new TLHaAccesso(p.pi, p.s));
                        Environment.aggiungiEvento(new Bacio(this, p.pi));
                        p.statoCorrente = StatoPrincipe.CORTE;
                    } else {
                        p.statoCorrente = StatoPrincipe.RANOCCHIO;
                    }
                    
                    p.d = null; p.s = null; p.pi = null;
                }
                break;
            
            case RANOCCHIO:
                if (e.getClass() == Bacio.class) {
                    Bacio ev = (Bacio) e;
                    
                    // precondizioni
                    if (ev.getMitt().getClass() != Principessa.class) return;
                    Principessa mitt = (Principessa) ev.getMitt();
                    
                    p.addLinkPiaceLa(mitt);
                    p.addLinkInnamoratoDi(mitt);
                    p.statoCorrente = StatoPrincipe.CORTE;
                }
                break;
            
            default:
                throw new RuntimeException("stato non riconosciuto");
        }
    }
}