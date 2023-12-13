package banca;

public abstract class ContoCorrente implements Comparable<ContoCorrente> {
    private final String codice;
    protected int saldo;
    public String nome;
    private String cognome;

    ContoCorrente(String codice, int saldo) {
        this.codice = codice;
        this.saldo = saldo;
    }

    ContoCorrente(String codice, int saldo, String nome, String cognome) {
        this.codice = codice;
        this.saldo = saldo;
        this.nome = nome;
        this.cognome = cognome;
    }

    public void deposito(int cifra) {
        saldo += cifra;
    }

    abstract public void prelievo(int cifra);

    public String getCodice() {
        return codice;
    }

    public int getSaldo() {
        return saldo;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public int compareTo(ContoCorrente o) {
        // this.saldo -- o.getSaldo()

        if (this.saldo == o.getSaldo())
            return 0;
        else if (this.saldo > o.getSaldo())
            return 1;
        else
            return -1;
    }
}
