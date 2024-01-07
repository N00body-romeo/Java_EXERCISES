import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientListener implements ActionListener {

    private final ClientFrame frame;

    public ClientListener(ClientFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "connect":
                frame.connect();
                break;

            case "disconnect":
                frame.disconnect();
                break;

            case "stop":
                frame.stop();
                break;

            case "artistiAct":
                frame.artistiAct();
                break;

            case "canzoniAct":
                frame.canzoniAct();
                break;
        }
    }

}
