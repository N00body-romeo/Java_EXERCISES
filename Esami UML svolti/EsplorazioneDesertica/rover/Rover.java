package rover;

// import ..

public class Rover extends DispositivoRobotico implements Listener {
    private double vel;
    
    public Rover(String n, double la, double lo, int r, double v) {
        super(n, la, lo, r);
        this.vel = v;
    }
    
    public double getVelocita() { return vel; }
    
    // Gestione eventi
    enum StatoRover { IN_BASE, ESPLORA, ESPLORA_DRONE, ATTESA_DRONE }
    
    StatoRover stato = IN_BASE;
    Drone d;
    
    public void fired(Evento e) {
        TaskExecutor.getInstance().perform(new RoverFired(this, e));
    }
    
    public StatoRover getStato() { return stato; }
}