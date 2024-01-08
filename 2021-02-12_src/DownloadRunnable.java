public class DownloadRunnable implements Runnable {
    private final ClientFrame frame;

    public DownloadRunnable(ClientFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        while (true) {
            String in = frame.getInputScanner().nextLine();

            if (in.equals("Not Found") || in.equals("error")) break;

            String[] dati = in.split(":");

            //CPU
            if (Integer.parseInt(dati[0])>0 && Integer.parseInt(dati[0])<100) {
                frame.modificaProg(Integer.parseInt(dati[0]), frame.CpuProgr);
            }

            //MEM
            if (Integer.parseInt(dati[1])>0 && Integer.parseInt(dati[1])<100) {
                frame.modificaProg(Integer.parseInt(dati[1]), frame.MemoryProgr);
            }

            //DISK
            if (Integer.parseInt(dati[2])>0 && Integer.parseInt(dati[2])<100) {
                frame.modificaProg(Integer.parseInt(dati[2]), frame.DiskProgr);
            }

            //NETWORK
            if (Integer.parseInt(dati[3])>0 && Integer.parseInt(dati[3])<100) {
                frame.modificaProg(Integer.parseInt(dati[3]), frame.NetworkProgr);
            }

        }
        frame.stop();
    }
}
