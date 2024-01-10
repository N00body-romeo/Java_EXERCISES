import javax.swing.*;

public class DownloadRunnable implements Runnable{
    private final ClientFrame frame;

    public DownloadRunnable(ClientFrame frame) {
        this.frame = frame;
    }

    public void run() {
        while (true) {
            String in = frame.getInputScanner().nextLine();

            if (in.equals("File Not Found")) {
                JOptionPane.showMessageDialog(null, "File non trovato");
                break;
            }

            if (in.equals("END")) {
                frame.getConsole().aggiungiRiga("=============Donwload completato=============");
                frame.end();
                break;
            }

            if (in.equals("INTERRUPETD")) {
                frame.getConsole().aggiungiRiga("=============Donwload interrotto=============");
                frame.end();
                break;
            }

            if (in.equals("ERROR")) {
                frame.getConsole().aggiungiRiga("=============Comando errato=============");
                frame.end();
                break;
            }

            frame.getConsole().aggiungiRiga(in);

        }
    }
}
