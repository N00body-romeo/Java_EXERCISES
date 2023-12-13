package persona;

public class Animale extends EssereViventeAbstract {

    @Override
    public void respira() {
        super.respira();
        System.out.println("fine respiro");
    }

    @Override
    public void dorme() {

    }
}
