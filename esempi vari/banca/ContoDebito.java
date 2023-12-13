package banca;

public final class ContoDebito extends ContoCorrente {

    ContoDebito(String cod, int saldoIniziale) {
        super(cod, saldoIniziale);
    }

    ContoDebito(String cod, int saldoIniziale, String nome, String cognome) {
        super(cod, saldoIniziale, nome, cognome);
    }

    @Override
    public void prelievo(int cifra) {
        saldo -= cifra;
    }

    public void riconosciInteresse(double interesse) {
        if (interesse < 0 || interesse > 1)
            return;

        saldo += (int) Math.round(saldo*interesse);
    }
}
