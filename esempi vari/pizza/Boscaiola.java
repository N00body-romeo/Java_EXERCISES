package pizza;

public class Boscaiola extends BasePizza {
    final boolean conSugo;

    public Boscaiola(boolean conSugo) {
        this.conSugo = conSugo;
    }

    @Override
    public void prepara() {
        super.prepara();
    }

    @Override
    void condisci() {
        System.out.println("conSugo: " + conSugo + ", salsiccia, funghi, mozzarella");
    }

    @Override
    public void cuoci() {
        System.out.println("cuoci boscaiola");
    }

    @Override
    public void mangia() {
        System.out.println("gnam boscaiola");
    }
}
