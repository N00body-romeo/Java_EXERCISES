package banca;

public class Main {
    public static void main(String[] args) {
        Banca banca = new Banca("nome", "indirizzo");

        ContoCredito c1 = new ContoCredito("c1", 200);
        ContoCredito c2 = new ContoCredito("c2", 100);
        ContoDebito d1 = new ContoDebito("d1", 300);



        banca.addConto(c1);
        banca.addConto(c2);
        banca.addConto(d1);

        banca.ordina();
        banca.situazioneFinanziaria();
        System.out.println();

        c1.deposito(50);
        d1.prelievo(100);
        c2.deposito(300);
        c2.prelievo(150);

        banca.ordina();
        banca.situazioneFinanziaria();
    }
}
