public class DownloadRunnable implements Runnable {
    private final ClientFrame frame;

    public DownloadRunnable(ClientFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        while (true) {
            String in = frame.getInputScanner().nextLine();

            if (in.equals("interrupted")) { break; }
            if (in.equals("done")) { frame.done(); }

            //riga:colonna:n
            String[] dati = in.split(":");

            
        }
    }
}
