public class DownloadRunnable implements Runnable {

    private ClientFrame frame;

    public DownloadRunnable(ClientFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        while(true) {
            String in = frame.getInputScanner().nextLine();

           if (in.equals("interrupted")) break;

           String[] dati = in.split(":");

           //ruota:estratto

            if ((Integer.parseInt(dati[0]) >= 1 && Integer.parseInt(dati[0]) <= 5) &&
                    (Integer.parseInt(dati[1]) >= 1 && Integer.parseInt(dati[1]) <= 90)) {
                for (int i = 0; i < 25; i++) {
                    if (((String)frame.ruote.get(i).getSelectedItem()).equals(dati[1])) {
                        frame.ruote.get(i).setChecked(true);
                    }
                }

            }

            if (in.equals("done")) {
                frame.done();
            }
        }
        frame.stop();
    }

}
