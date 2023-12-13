// Esercitazione 7

// Comando da terminale di Eclipse per scaricare ed eseguire il server.jar
// --> java -jar (nome_file_server).jar (in genere è sempre server.jar)

// CLASSE ClientMain
public class ClientMain {
    public static void main(String[] args) {
        ClientFrame frame = new ClientFrame();
        frame.setTitle("Lorenzo Musilli 2084188");
        frame.pack();
        frame.setVisible(true);
    }
}

// CLASSE ClientFrame
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientFrame extends JFrame {

    // Componenti grafici
    JPanel nordPanel;
    JTextField serverAddressText;
    JTextField portText;
    JButton connectBtn;
    JButton disconnectBtn;

    JPanel centroPanel;
    JTextArea usaArea;
    JTextArea itaArea;
    JTextArea logTextArea;
    JScrollPane usaScroll;
    JScrollPane itaScroll;
    JScrollPane logScroll;

    JPanel sudPanel;
    JButton startBtn;
    JButton stopBtn;

    // Costruttore
    public ClientFrame() {
        // Inizializzazione dei widget
        this.serverAddressText = new JTextField("127.0.0.1", 20);
        this.portText = new JTextField("4400", 20);
        this.connectBtn = new JButton("Connect");
        this.disconnectBtn = new JButton("Disconnect");
        this.startBtn = new JButton("Start");
        this.stopBtn = new JButton("Stop");

        // Pannello nord
        this.nordPanel = new JPanel(new FlowLayout());
        this.nordPanel.add(new Label("Server Address"));
        this.nordPanel.add(this.serverAddressText);
        this.nordPanel.add(new Label("Port"));
        this.nordPanel.add(this.portText);
        this.nordPanel.add(this.connectBtn);
        this.nordPanel.add(this.disconnectBtn);

        // Pannello centrale
        this.usaArea = new JTextArea(8, 10);
        this.usaArea.setEditable(false);
        this.usaScroll = new JScrollPane(this.usaArea);
        this.usaScroll.setBorder(BorderFactory.createTitledBorder("USA"));

        this.itaArea = new JTextArea(8, 10);
        this.itaArea.setEditable(false);
        this.itaScroll = new JScrollPane(this.itaArea);
        this.itaScroll.setBorder(BorderFactory.createTitledBorder("Italia"));

        this.logTextArea = new JTextArea(8, 10);
        this.logTextArea.setEditable(false);
        this.logScroll = new JScrollPane(this.logTextArea);
        this.logScroll.setBorder(BorderFactory.createTitledBorder("Log"));

        this.centroPanel = new JPanel(new GridLayout(1, 3));
        this.centroPanel.add(this.usaScroll);
        this.centroPanel.add(this.itaScroll);
        this.centroPanel.add(this.logScroll);

        // Pannello sud
        this.sudPanel = new JPanel(new FlowLayout());
        this.sudPanel.add(startBtn);
        this.sudPanel.add(stopBtn);

        // Contenitore principale
        Container mainContainer = this.getContentPane();
        mainContainer.add(nordPanel, BorderLayout.NORTH);
        mainContainer.add(centroPanel, BorderLayout.CENTER);
        mainContainer.add(sudPanel, BorderLayout.SOUTH);

        // Listener
        ActionListener list = new ClientListener(this);
        this.connectBtn.setActionCommand(ClientListener.CONNECT);
        this.connectBtn.addActionListener(list);
        this.disconnectBtn.setActionCommand(ClientListener.DISCONNECT);
        this.disconnectBtn.addActionListener(list);
        this.startBtn.setActionCommand(ClientListener.START);
        this.startBtn.addActionListener(list);
        this.stopBtn.setActionCommand(ClientListener.STOP);
        this.stopBtn.addActionListener(list);

        // Stato iniziale
        this.setLocation(200, 200);
        setButtons(false, false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // Imposta lo stato dei pulsanti
    public void setButtons(boolean connected, boolean transmitting) {
        if (connected) {
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            this.connectBtn.setEnabled(false);
            if (transmitting) {
                this.disconnectBtn.setEnabled(false);
                this.startBtn.setEnabled(false);
                this.stopBtn.setEnabled(true);
            } else {
                this.disconnectBtn.setEnabled(true);
                this.startBtn.setEnabled(true);
                this.stopBtn.setEnabled(false);
            }
        } else {
            this.disconnectBtn.setEnabled(false);
            this.startBtn.setEnabled(false);
            this.stopBtn.setEnabled(false);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.connectBtn.setEnabled(true);
        }
    }
}

// CLASSE ClientListener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ClientListener implements ActionListener {
    public static final String CONNECT = "connect", DISCONNECT = "disconnect", START = "start", STOP = "stop";

    private boolean connected, transmitting;
    private Socket sock;
    private Scanner sockScanner;
    private PrintWriter sockWriter;
    private ClientFrame frame;
    private DownloadThread downloadThread;

    // Costruttore
    public ClientListener(ClientFrame frame) {
        this.frame = Objects.requireNonNull(frame, "Client frame must not be null");
    }

    // Configurazione della connessione
    private void setupConnection() throws IOException {
        try {
            String serverAddress = frame.serverAddressText.getText();
            int serverPort = Integer.parseInt(frame.portText.getText());
            sock = new Socket(serverAddress, serverPort);
            sockScanner = new Scanner(sock.getInputStream());
            sockWriter = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
        } catch (NumberFormatException e) {
            throw new IOException("Formato porta errato");
        }
    }

    // Chiamato quando il download è completo
    protected void downloadComplete() {
        transmitting = false;
        frame.setButtons(connected, transmitting);
    }

    // Chiamato per disconnettersi forzatamente
    protected void disconnect() {
        frame.disconnectBtn.doClick();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case ClientListener.CONNECT:
                try {
                    this.setupConnection();
                    connected = true;
                    JOptionPane.showMessageDialog(frame, "Connessione stabilita");
                    this.frame.usaArea.setText("");
                    this.frame.itaArea.setText("");
                    this.frame.logTextArea.setText("");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Impossibile connettersi al server: \n" + ex.getMessage());
                    ex.printStackTrace();
                    return;
                }
                break;
            case ClientListener.START:
                transmitting = true;
                this.frame.usaArea.setText("");
                this.frame.itaArea.setText("");
                this.frame.logTextArea.setText("");
                sockWriter.println(ClientListener.START);
                this.sockWriter.flush();
                downloadThread = new DownloadThread(frame, this, sockScanner);
                Thread th1 = new Thread(downloadThread);
                th1.start();
                break;
            case ClientListener.STOP:
                transmitting = false;
                this.sockWriter.println(ClientListener.STOP);
                this.sockWriter.flush();
                downloadThread.stop();
                break;
            case ClientListener.DISCONNECT:
                this.sockWriter.println(ClientListener.DISCONNECT);
                downloadThread.stop();
                transmitting = false;
                connected = false;
                try {
                    this.sockWriter.close();
                    this.sockScanner.close();
                    this.sock.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
        }
        this.frame.setButtons(connected, transmitting);
    }
}

// CLASSE DownloadThread
import java.util.Objects;
import java.util.Scanner;

public class DownloadThread implements Runnable {
    private boolean running, errored;
    private ClientFrame frame;
    private ClientListener listener;
    private Scanner scan;

    // Costruttore
    public DownloadThread(ClientFrame frame, ClientListener list, Scanner scan) {
        this.frame = Objects.requireNonNull(frame, "Client frame must not be null");
        this.listener = Objects.requireNonNull(list, "Client listener must not be null");
        this.scan = Objects.requireNonNull(scan, "Server scanner must not be null");
    }

    // Ferma il thread
    public void stop() {
        if (!running) return;
        running = false;
    }

    @Override
    public void run() {
        if (running) return;
        running = true;
        try {
            while (running) {
                if (Thread.currentThread().isInterrupted()) throw new InterruptedException();
                String cmd = scan.nextLine();
                System.out.println("Ricevuto: " + cmd);

                if (cmd.equalsIgnoreCase("START")) running = true;
                else if (cmd.equalsIgnoreCase("END")) running = false;
                else if (cmd.equalsIgnoreCase("INTERRUPTED")) running = false;
                else if (cmd.equalsIgnoreCase("ERROR")) {
                    running = false;
                    errored = true;
                } else {
                    String[] tokens = cmd.split(":");
                    if (tokens[0].equalsIgnoreCase("USA")) {
                        String text = frame.usaArea.getText();
                        String citta = tokens[1];
                        if (text.isEmpty()) this.frame.usaArea.setText(citta);
                        else if (text.contains(citta)) this.frame.usaArea.setText(text + '\n' + citta + " dopp.");
                        else this.frame.usaArea.setText(text + '\n' + citta);
                    }
                    if (tokens[0].equalsIgnoreCase("ITALIA")) {
                        String text = frame.itaArea.getText();
                        String citta = tokens[1];
                        if (text.isEmpty()) this.frame.itaArea.setText(citta);
                        else if (text.contains(citta)) this.frame.itaArea.setText(text + '\n' + citta + " dopp.");
                        else this.frame.itaArea.setText(text + '\n' + citta);
                    }

                    String text = frame.logTextArea.getText();
                    if (text.isEmpty()) this.frame.logTextArea.setText(cmd);
                    else this.frame.logTextArea.setText(text + '\n' + cmd);
                }
            }
        } catch (InterruptedException e) {
            running = false;
        } finally {
            listener.downloadComplete();
            if (errored) listener.disconnect();
        }
    }
}
