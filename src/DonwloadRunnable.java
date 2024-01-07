import java.util.ArrayList;

public class DonwloadRunnable implements Runnable {

    private ClientFrame frame;
    public static ArrayList<String> listaArtisti = new ArrayList<>();

    public DonwloadRunnable(ClientFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        while (true) {
            String in = frame.getInputScanner().nextLine();

            if (in.equals("END") || in.equals("INTERRUPTED")) break;

            if (!in.equals("artisti")) {
                //dati[0] = ARTISTA, dati[1] = nome artista
                String[] dati = in.split(":");

                if (dati[0].equals("ARTISTA")) {
                    frame.getLogPane().aggiungiRiga("ARTISTA: "+ dati[1]);
                    listaArtisti.add(dati[1]);
                }

            }
        }
        frame.fineDownload();

    }

    public static boolean listaContiene (ArrayList<String> l, String s) {
        if (l.contains(s)) { return true; }
        else {return false;}
    }
}
