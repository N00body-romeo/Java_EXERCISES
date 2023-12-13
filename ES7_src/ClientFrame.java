import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class ClientFrame extends JFrame {

    private final JTextField txtInd = new JTextField(20);
    private final JTextField txtPort = new JTextField(10);

    private final JLabel lblInd = new JLabel("Server Address");
    private final JLabel lblPort = new JLabel("Port");

    private final JButton btnConnect = new JButton("Connect");
    private final JButton btnDisconnect = new JButton("Disconnect");
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");

    private final MyScrollPane paneUSA = new MyScrollPane("USA");
    private final MyScrollPane paneItaly = new MyScrollPane("Italia");
    private final MyScrollPane paneLog = new MyScrollPane("Log");

    private final Container cntNord = new JPanel();
    private final Container cntCenter = new JPanel();
    private final Container cntSud = new JPanel();

    private final ClientListener listener = new ClientListener(this);
    private Socket socket;
    private Scanner inputScanner;
    private PrintWriter outputWriter;

    public ClientFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        cntNord.setLayout(new FlowLayout());
        cntSud.setLayout(new FlowLayout());
        cntCenter.setLayout(new GridLayout(1, 3));

        btnDisconnect.setEnabled(false);
        btnStart.setEnabled(false);
        btnStop.setEnabled(false);

        btnConnect.setActionCommand("connect");
        btnDisconnect.setActionCommand("disconnect");
        btnStart.setActionCommand("start");
        btnStop.setActionCommand("stop");

        btnConnect.addActionListener(listener);
        btnDisconnect.addActionListener(listener);
        btnStart.addActionListener(listener);
        btnStop.addActionListener(listener);

        cntNord.add(lblInd);
        cntNord.add(txtInd);
        cntNord.add(lblPort);
        cntNord.add(txtPort);
        cntNord.add(btnConnect);
        cntNord.add(btnDisconnect);

        cntCenter.add(paneUSA);
        cntCenter.add(paneItaly);
        cntCenter.add(paneLog);

        cntSud.add(btnStart);
        cntSud.add(btnStop);

        contentPane.add(cntNord, BorderLayout.NORTH);
        contentPane.add(cntSud, BorderLayout.SOUTH);
        contentPane.add(cntCenter, BorderLayout.CENTER);

        // this.setTitle(title);
        this.pack();
    }

    public Scanner getInputScanner() {
        return inputScanner;
    }

    public MyScrollPane getPaneItaly() {
        return paneItaly;
    }

    public MyScrollPane getPaneUSA() {
        return paneUSA;
    }

    public MyScrollPane getPaneLog() {
        return paneLog;
    }

    void connect() {
        try {
            socket = new Socket(txtInd.getText(), Integer.parseInt(txtPort.getText()));
            btnConnect.setEnabled(false);
            btnStart.setEnabled(true);
            btnDisconnect.setEnabled(true);

            inputScanner = new Scanner(socket.getInputStream());
            outputWriter = new PrintWriter(socket.getOutputStream());

            paneItaly.clear();
            paneLog.clear();
            paneUSA.clear();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore nella connessione al server");
        }
    }

    void disconnect() {
        // invia "DISCONNECT"
        outputWriter.println("DISCONNECT");
        outputWriter.flush();

        // chiudere canali, socket
        try {
            inputScanner.close();
            outputWriter.close();
            socket.close();
        } catch (Exception e) {}

        btnStart.setEnabled(false);
        btnStop.setEnabled(false);
        btnDisconnect.setEnabled(false);
        btnConnect.setEnabled(true);
    }

    void start() {
        outputWriter.println("START");
        outputWriter.flush();

        btnStop.setEnabled(true);
        btnStart.setEnabled(false);
        btnDisconnect.setEnabled(false);

        Thread thread = new Thread(new DownloadRunnable(this));
        thread.start();
    }

    void stop() {
        outputWriter.println("STOP");
        outputWriter.flush();
    }

    void fineDownload() {
        btnStart.setEnabled(true);
        btnDisconnect.setEnabled(true);
        btnStop.setEnabled(false);
    }

    private JScrollPane creaScrollPane(String titolo) {
        JTextArea textArea = new JTextArea(4, 20);
        JScrollPane pane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setBorder(BorderFactory.createTitledBorder(titolo));
        textArea.setEditable(false);

        /*pane.add(textArea);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);*/

        return pane;
    }
}
