package pizza;

public class Focaccia extends BasePizza {

    public Focaccia() {
        prepara();
    }

    @Override
    public void cuoci() {
        System.out.println("cuoci focaccia");
    }

    @Override
    public void mangia() {
        System.out.println("gnam focaccia");
    }

    @Override
    void condisci() {
        System.out.println("sale, olio e rosmarino");
    }
}
