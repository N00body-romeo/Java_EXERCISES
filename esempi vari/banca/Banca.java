package banca;

import java.util.Arrays;

public class Banca {
    private final String nome;
    public String indirizzo;
    private final ContoCorrente[] conti;

    private int curConto = 0;
    private final int maxConto = 10;

    public Banca(String nome, String indirizzo) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        conti = new ContoCorrente[maxConto];
    }

    public void addConto(ContoCorrente conto) {
        if (curConto >= maxConto) return;
        conti[curConto++] = conto;
    }

    public void ordina() {
        ContoCorrente[] copia = Arrays.copyOf(conti, curConto);
        Arrays.sort(copia);

        for (int i = 0; i < copia.length; i++)
            conti[i] = copia[i];
    }

    public void situazioneFinanziaria() {
        for (int i = 0; i < curConto; i++)
            System.out.printf("Conto %s, saldo: %d%n", conti[i].getCodice(), conti[i].getSaldo());
    }

    public String getNome() {
        return nome;
    }
}
