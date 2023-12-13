package exc;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(divisione(5,2));
            System.out.println(divisione(5,0));
            System.out.println(divisione(5,2));
        } catch (CheckedZeroDivisionException e) {
            System.out.println("Hai diviso per zero!");
        } finally {
            System.out.println("Dopo il try-catch");
        }
    }

    public static double divisione(int n, int d) throws CheckedZeroDivisionException {
        if (d == 0)
            throw new CheckedZeroDivisionException();
        return (double) n / d;
    }
}
