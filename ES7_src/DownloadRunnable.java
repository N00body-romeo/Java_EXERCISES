public class DownloadRunnable implements Runnable {

    private final ClientFrame frame;

    public DownloadRunnable(ClientFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        while (true) {
            String in = frame.getInputScanner().nextLine();

            if (in.equals("END") || in.equals("INTERRUPTED")) break;

            if (!in.equals("START")) {
                // NAZIONE:città

                // aggiungi una riga in paneLog
                frame.getPaneLog().aggiungiRiga(in);

                // dati[0] = NAZIONE, dati[1] = città
                String[] dati = in.split(":");

                if (dati[0].equals("ITALIA")) {
                    if (frame.getPaneItaly().contiene(dati[1]))
                        frame.getPaneItaly().aggiungiRiga(dati[1] + " dopp.");
                    else
                        frame.getPaneItaly().aggiungiRiga(dati[1]);
                } else if (dati[0].equals("USA")) {
                    if (frame.getPaneUSA().contiene(dati[1]))
                        frame.getPaneUSA().aggiungiRiga(dati[1] + " dopp.");
                    else
                        frame.getPaneUSA().aggiungiRiga(dati[1]);
                }
            }
        }
        frame.fineDownload();
    }
}
