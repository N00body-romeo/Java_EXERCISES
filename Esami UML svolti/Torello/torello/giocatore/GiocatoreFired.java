package torello.giocatore;

class GiocatoreFired implements Task {
    private boolean eseguita = false;
    private Giocatore g;
    private Evento e;
    
    private int iR = 0, iG = 0;
    
    public GiocatoreFired(Giocatore g, Evento e) {
        this.g = g;
        this.e = e;
    }
    
    public synchronized void esegui(Executor exec) {
        if (eseguita || exec == null
            || (e.getDestinatario() != g&& e.getDestinatario() != null))
            return;
        
        eseguita = true;
        switch (g.getStato()) {
            case NON_IN_GIOCO:
                if (e.getClass() == InizioPartita.class) 
                    g.statoCorrente = Giocatore.StatoGiocatore.IN_GIOCO;
                break;
                
            case IN_GIOCO:
                if (e.getClass() == Palla.class) {
                    Palla ee = (Palla) e;
                    Giocatore dest = scegliAvversario();
                    Environment.aggiungiEvento(new Palla(g, dest, ee.getId()));
                            
                } else if (e.getClass() == FinePartita.class) {
                    g.statoCorrente = Giocatore.StatoCorrente.NON_IN_GIOCO;
                }
                break;
                
            default:
                throw new RuntimeException("stato corrente non riconosciuto");
        }
    }
    
    public synchronized boolean estEseguita() {
        return eseguita;
    }
    
    private Giocatore scegliAvversario() {
        if (g.getClass() == GiocatoreRosso.class) {
            LinkedList<LinkPartecipaGiallo> lg =
                ((GiocatoreRosso)g).getLinkPartecipaRosso().getTorello().getLinkPartecipaGiallo();
            
            return lg.get(iG++/lg.size());
        } else {
            LinkedList<LinkPartecipaRosso> lr =
                ((GiocatoreGiallo)g).getLinkPartecipaGiallo().getTorello().getLinkPartecipaRosso();
            
            return lr.get(iR++/lr.size());
        }
    }
}