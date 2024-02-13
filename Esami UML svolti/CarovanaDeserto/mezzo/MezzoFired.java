package mezzo;

// import ..

class MezzoFired implements Task {
    private Mezzo mezzo;
    private Evento e;
    private boolean eseguita;
    
    MezzoFired(Mezzo m, Evento e) {
        this.mezzo = m;
        this.e = e;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void esegui() throws RuntimeException {
        if (eseguita) return;
        eseguita = true;
        
        // precondizione ad ogni evento ricevuto
        if (e.getDest() != null && !e.getDest().equals(mezzo)) return;
        
        switch (mezzo.statoCorrente) {
            case A_RIPOSO:
                /// A_RIPOSO -> IN_MARCIA
                // si controlla che l'evento sia del tipo Partenza per la transizione 
                if (e.getClass() == Partenza.class) {
                    mezzo.statoCorrente = IN_MARCIA;
                }
                break;
            
            case IN_MARCIA:
                /// IN_MARCIA -> IN_MARCIA
                if (e.getClass() == Carica.class) {
                    Carica ev = (Carica) e;     // si casta e: Evento -> ev: Carica per accedere al payload
                    
                    // condizione
                    if (ev.getMitt().getClass() == Carovana.class ||
                        (ev.getMitt().getClass() == Mezzo.class && !ev.getMitt().equals(this))) {
                        Set<Persona> u = ev.getUtenti();
                        
                        // aggiungi le persone a quelle trasportate
                        for (Persona p : u) mezzo.addLinkTrasporta(new TLTrasporta(mezzo, p));
                        
                        // invia il nuovo evento
                        Environment.aggiungiEvento(new Caricate(mezzo, ev.getMitt()));
                        
                        // lo stato non cambia
                    }
                    
                /// IN_MARCIA -> A_RIPOSO
                } else if (e.getClass() == Stop.class) {
                    mezzo.statoCorrente = A_RIPOSO;
                
                /// IN_MARCIA -> ATTESA
                } else if (e.getClass() == Guasto.class) {
                    // invia il nuovo evento in broadcast
                    Environment.aggiungiEvento(new Accogliere(mezzo, null));
                    
                    mezzo.statoCorrente = ATTESA;
                }
                break;
            
            case ATTESA:
                if (e.getClass() == Ok.class) {
                    // condizione
                    if (e.getMitt().getClass() == Mezzo.class && e.getMit() != this) {
                        mezzo.m = e.getMitt();
                        
                        // crea il payload con le persone, rimuovendo il pilota in caso di mezzo non autonomo
                        Set<TLTrasporta> payloadLink = mezzo.getTrasporta();
                        if (mezzo.getClass() == MezzoNonAutonomo.class) {
                            MezzoNonAutonomo mna = (MezzoNonAutonomo) m;
                            TLTrasporta tlt = new TLTrasporta(mezzo, mna.getLinkPilota().getPersona());
                            payloadLink.remove(tlt);
                        }
                        
                        Set<Persona> payload = new HashSet<>();
                        for (TLTrasporta l : payloadLink) payload.add(l.getPersona());
                        
                        Environment.aggiungiEvento(new Carica(mezzo, mezzo.m, payload));
                        
                        mezzo.statoCorrente = IN_TRASBORDO;
                    }
                }
                break;
                
            case IN_TRASBORDO:
                if (e.getClass() == Caricate.class) {
                    // precondizione
                    if (!e.getMitt().equals(mezzo.m)) return;
                    
                    mezzo.m = null;
                    mezzo.statoCorrente = A_RIPOSO;
                }
                break;
            
            default:
                throw new RuntimeException("stato non riconosciuto");
        }
    }
}