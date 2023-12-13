package banca;

public final class ContoCredito extends ContoCorrente {

    private static final int SOGLIA = 5;

    public int costoCommissione = 0;
    private int numeroOperazioni = 0;

    ContoCredito(String cod, int saldoIniziale) {
        super(cod, saldoIniziale);
    }

    ContoCredito(String cod, int saldoIniziale, String nome, String cognome) {
        super(cod, saldoIniziale, nome, cognome);
    }

    @Override
    public void prelievo(int cifra) {
        saldo -= cifra;
        numeroOperazioni++;

        if (numeroOperazioni > SOGLIA)
            saldo -= costoCommissione;
    }

    @Override
    public void deposito(int cifra) {
        super.deposito(cifra);
        numeroOperazioni++;

        if (numeroOperazioni > SOGLIA)
            saldo -= costoCommissione;
    }

    public void reset() {
        numeroOperazioni = 0;
    }
}
