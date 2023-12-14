public class DowloadRunnable implements Runnable {

    private final ClientFrame frame;
    private final ClientSocket socket;

    public DowloadRunnable(ClientFrame frame, ClientSocket socket) {
        this.frame = frame;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            String input = socket.read();

            if (input.equals("END") || input.equals("ERROR")) break;

            String[] data = input.split(":");

            if (data.length == 2) {
                frame.handleRisultato(data[0], data[1]);
            }
        }

        frame.handleFineDownload();
    }
}
