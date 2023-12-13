package exc;

public class ZeroDivisionException extends RuntimeException {

    public ZeroDivisionException() {
        super("Eccezione: Divisione per zero!");
    }

    public ZeroDivisionException(String msg) {
        super("Eccezione: Divisione per zero! " + msg);
    }

    public ZeroDivisionException(int dividendo) {
        super("Eccezione: Divisione per zero su " + dividendo + "!");
    }
}
