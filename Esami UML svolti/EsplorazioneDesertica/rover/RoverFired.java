package rover;

// import ..

class RoverFired implements Task {
    private Rover rover;
    private Evento e;
    private boolean eseguita = false;
    
    RoverFired(Rover r, Evento e) {
        this.rover = r;
        this.e = e;
    }
    
    public synchronized boolean estEseguita() { return eseguita; }
    
    public synchronized void run() throws RuntimeException {
        if (eseguita) return;
        eseguita = true;
        
        if (e.getDest() != null && !e.getDest().equals(rover)) return;
        
        switch (rover.stato) {
            case StatoRover.IN_BASE:
                if (e.getClass() == EvEsplorazione.class) {
                    EvEsplorazione ev = (EvEsplorazione) e;
                    
                    if (!ev.getMitt().equals(rover.getLinkContiene().getBase())) return;
                    
                    double dist = rover.distanzaA(ev.getLat(), ev.getLong()); // calcola la distanza dal rover (in base)
                    if (dist <= rover.getRaggio()) {
                        rover.muovi(ev.getLat(), ev.getLong()); // cambia rover.lat e rover.lon
                        rover.stato = StatoRover.ESPLORA;
                        
                    } else {
                        rover.d = rover.scegliDrone();
                        rover.muoviDistMax(ev.getLat(), ev.getLong()); // cambia rover.lat e rover.lon per portarle a dist max
                                                                       // verso le coordinate finali
                        Environment.aggiungiEvento(new Muovi(rover, rover.d, ev.getLat(), ev.getLong()));
                        rover.stato = StatoRover.ESPLORA_DRONE;
                    }
                }
                break;
                
            case StatoRover.ESPLORA:
                if (e.getClass() == Rientro.class) {
                    double baseLat = rover.getLinkContiene().getBase().getLat();
                    double baseLong = rover.getLinkContiene().getBase().getLon();
                    rover.muovi(baseLat, baseLong);
                    rover.stato = StatoRover.IN_BASE;
                }
                break;
                
            case StatoRover.ESPLORA_DRONE:
                if (e.getClass() == Rientro.class) {
                    Environment.aggiungiEvento(new Ritornare(rover, rover.d));
                    rover.stato = StatoRover.ATTESA_DRONE;
                }
                break;
                
            case StatoRover.ATTESA_DRONE:
                if (e.getClass() == Rientrato.class) {
                    if (!e.getMitt().equals(rover.d)) return;
                    
                    rover.d = null;
                    double baseLat = rover.getLinkContiene().getBase().getLat();
                    double baseLong = rover.getLinkContiene().getBase().getLon();
                    rover.muovi(baseLat, baseLong);
                }
                break;
                
            default:
                throw new RuntimeException("stato non riconosciuto");
        }
    }
}