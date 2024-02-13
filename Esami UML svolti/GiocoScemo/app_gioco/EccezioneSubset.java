package app_gioco;

import java.lang.*; //?

public class EccezioneSubset extends RuntimeException {
    public EccezioneSubset(String msg) {
        super("Subset violato: " + msg);
    }
}