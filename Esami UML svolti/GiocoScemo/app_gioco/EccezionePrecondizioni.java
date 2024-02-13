package app_gioco;

import java.lang.*; //?

public class EccezionePrecondizioni extends RuntimeException {
    public EccezionePrecondizioni(String msg) {
        super("Precondizione violata: " + msg);
    }
}