import javax.swing.*;
import java.awt.*;

public class ClientFrame extends JFrame {

    public enum Operazione {
        SOMMA,
        MOLTIPLICAZIONE,
        DIVISIONE;

        static Operazione of(String s) {
            switch (s.toUpperCase()) {
                case "SOMMA": return SOMMA;
                case "MOLTIPLICAZIONE": return MOLTIPLICAZIONE;
                case "DIVISIONE": return DIVISIONE;
            }
            return null;
        }
    }

    private final static JLabel lblAddr = new JLabel("Server Address");
    private final static JLabel lblPort = new JLabel("Port");
    private final static JLabel lblNum1 = new JLabel("1° intero");
    private final static JLabel lblNum2 = new JLabel("2° intero");

    private final JTextField txtAddr = new JTextField(20);
    private final JTextField txtPort = new JTextField(10);
    private final JTextField txtNum1 = new JTextField(10);
    private final JTextField txtNum2 = new JTextField(10);

    private final JButton btnConnect = new JButton("Connect");
    private final JButton btnDisconnect = new JButton("Disconnect");
    private final JButton btnEval = new JButton("=");

    private final InfoPanel infoOperation = new InfoPanel("Operazione");
    private final ResultPanel infoResDec = new ResultPanel("Risultato");
    private final ResultPanel infoResExa = new ResultPanel("Esadecimale");
    private final ResultPanel infoResBin = new ResultPanel("Binario");

    private final JPanel top = new JPanel();
    private final JPanel center = new JPanel();
    private final JPanel bottom = new JPanel();

    private final ClientSocket socket = new ClientSocket();
    private final ClientListener listener = new ClientListener(this);

    public ClientFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnConnect.setActionCommand("connect");
        btnDisconnect.setActionCommand("disconnect");
        btnEval.setActionCommand("eval");

        btnConnect.addActionListener(listener);
        btnDisconnect.addActionListener(listener);
        btnEval.addActionListener(listener);

        top.setLayout(new FlowLayout());
        center.setLayout(new FlowLayout());
        bottom.setLayout(new FlowLayout());

        top.add(lblAddr);
        top.add(txtAddr);
        top.add(lblPort);
        top.add(txtPort);
        top.add(btnConnect);
        top.add(btnDisconnect);

        center.add(lblNum1);
        center.add(txtNum1);
        center.add(lblNum2);
        center.add(txtNum2);
        center.add(btnEval);

        bottom.add(infoOperation);
        bottom.add(infoResDec);
        bottom.add(infoResExa);
        bottom.add(infoResBin);

        Container contentPane = this.getContentPane();
        contentPane.add(top, BorderLayout.NORTH);
        contentPane.add(bottom, BorderLayout.SOUTH);
        contentPane.add(center);

        btnDisconnect.setEnabled(false);
        btnEval.setEnabled(false);

        this.pack();
    }

    public void doConnect() {
        if (socket.connect(txtAddr.getText(), Integer.parseInt(txtPort.getText()))) {
            btnConnect.setEnabled(false);
            btnDisconnect.setEnabled(true);
            btnEval.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Errore nella connessione al server");
        }
    }

    public void doDisconnect() {
        socket.send("disconnect");
        if (socket.close()) {
            btnEval.setEnabled(false);
            btnDisconnect.setEnabled(false);
            btnConnect.setEnabled(true);
        }
    }

    public void doEval() {
        if (txtNum1.getText().isEmpty() || Integer.parseInt(txtNum1.getText()) < 0 ||
            txtNum2.getText().isEmpty() || Integer.parseInt(txtNum2.getText()) < 0) {
            JOptionPane.showMessageDialog(null, "I campi '1° numero' e '2° numero' devono " +
                    "contenere numeri interi positivi o nulli");
            return;
        }

        int n1 = Integer.parseInt(txtNum1.getText()),
            n2 = Integer.parseInt(txtNum2.getText());

        socket.send(String.format("uguale:%d:%d", n1, n2));
        Thread thread = new Thread(new DowloadRunnable(this, socket));
        thread.start();

        infoOperation.clear();
        infoResBin.clear();
        infoResDec.clear();
        infoResExa.clear();

        btnEval.setEnabled(false);
        btnDisconnect.setEnabled(false);
    }

    public void handleRisultato(String op, String ris) {
        Operazione operazione = Operazione.of(op);

        infoResExa.setOperazione(operazione);
        infoResDec.setOperazione(operazione);
        infoResBin.setOperazione(operazione);

        infoOperation.setValue(operazione.name().toLowerCase());
        infoResDec.setValue(ris);
        infoResBin.setValue(Integer.toBinaryString(Integer.parseInt(ris)));
        infoResExa.setValue(Integer.toHexString(Integer.parseInt(ris)));
    }

    public void handleFineDownload() {
        btnEval.setEnabled(true);
        btnDisconnect.setEnabled(true);
    }
}
