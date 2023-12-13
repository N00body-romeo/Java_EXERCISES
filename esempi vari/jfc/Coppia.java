package jfc;

import pizza.BasePizza;

public class Coppia<T extends BasePizza, S> {
    private T primo;
    private S secondo;

    public Coppia(T primo, S secondo) {
        this.primo = primo;
        this.secondo = secondo;
    }

    public T getPrimo() {
        return primo;
    }

    public void setPrimo(T primo) {
        this.primo = primo;
    }

    public S getSecondo() {
        return secondo;
    }

    public void setSecondo(S secondo) {
        this.secondo = secondo;
    }
}
