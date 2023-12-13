package threads;

public class Contatore {
    public int contatore = 0;

    public synchronized void incrementa() {
        contatore++;
    }

    public synchronized int getContatore() {
        return contatore;
    }
}
