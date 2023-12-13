package pizza;

public abstract class BasePizza implements PizzaInterface {
    abstract void condisci();

    void prepara() {
        condisci();
        cuoci();
    }

    public BasePizza() {}
}
