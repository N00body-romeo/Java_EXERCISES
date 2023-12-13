package exc;

public class CheckedZeroDivisionException extends Exception {
    public CheckedZeroDivisionException() {
        super("Eccezione: Divisione per zero!");
    }
}
