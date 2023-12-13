import pizza.BasePizza;
import pizza.Boscaiola;
import pizza.Focaccia;
import pizza.Margherita;

public class Main {
    public static void main(String[] args) {
        // BasePizza basePizza = new BasePizza(); // BasePizza è astratta

        Focaccia focaccia = new Focaccia();
        BasePizza margherita = new Margherita();
        Boscaiola boscaiola = new Boscaiola(true);
        /*BasePizza focaccia1 = new Focaccia();
        PizzaInterface focaccia2 = new Focaccia();*/

        focaccia.mangia();
        margherita.mangia();
        boscaiola.prepara();
        boscaiola.mangia();
    }

}
