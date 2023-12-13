package fig_geom;

public abstract class FiguraGeometrica implements FiguraGeometricaInterface {
    public static final double PI = 3.14;

    final String descrizione;
    final String descrizioneParam;
    protected final String descrizioneProtected;

    public FiguraGeometrica(String desc) {
        descrizione = "valore costante";
        descrizioneProtected = "";
        descrizioneParam = desc;
    }
}
