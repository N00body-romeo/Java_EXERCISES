import java.util.ArrayList;

public class CanzoniDowload implements Runnable {

    private ClientFrame frame;

    public CanzoniDowload(ClientFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        while (true) {
            String in = frame.getInputScanner().nextLine();

            if (in.equals("END") || in.equals("INTERRUPTED")) break;

            if (!in.equals("canzoni")) {
                //CANZONE:<iniziali>:<pezzo>
                String[] dati = in.split(":");

                if (dati[0].equals("CANZONE")) {
                    frame.getLogPane().aggiungiRiga("CANZONE:" + dati[1] + ":" + dati[2]);
                }

                /*if (DonwloadRunnable.listaContiene(DonwloadRunnable.listaArtisti, dati[1])) {
                    frame.aggiungiRigaTXT(dati[1] + " - " + dati[2]);
                }*/
                if (in.contains(dati[1])) {
                    frame.aggiungiRigaTXT(dati[1] + " - " + dati[2]);
                }
            }
        }
        frame.fineDownloadCanzoni();
    }
}
