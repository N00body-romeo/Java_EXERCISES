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
                frame.doConnect();
                break;

            case "disconnect":
                frame.doDisconnect();
                break;

            case "eval":
                frame.doEval();
                break;
        }
    }
}
