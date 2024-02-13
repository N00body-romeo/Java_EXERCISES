package app_gioco;

import java.lang.*; //?

public class EccezioneMolteplicita extends RuntimeException {
    public EccezioneMolteplicita(String msg) {
        super("Molteplicità violata: " + msg);
    }
}