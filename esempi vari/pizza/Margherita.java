package pizza;

public class Margherita extends BasePizza {

    public Margherita() {
        prepara();
    }

    @Override
    void condisci() {
        System.out.println("sugo, mozzarella, olio, origano");
    }

    @Override
    public void cuoci() {
        System.out.println("cuoci margherita");
    }

    @Override
    public void mangia() {
        System.out.println("gnam margherita");
    }

    @Override
    public void prepara() {
        super.prepara();
    }
}
