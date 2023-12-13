// Esercitazione 8

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
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientFrame extends JFrame{
    JPanel nordPanel;
    JTextField serverAddressText;
    JTextField portText;
    JButton connectBtn;
    JButton disconnectBtn;

    JButton[] griglia;
    JTextArea logArea;
    JScrollPane logScroll;
    JPanel centroPanel;
    JPanel leftPanel;

    JTextField numeroText;
    JButton getBtn;
    JButton resetBtn;
    JPanel sudPanel;

    public ClientFrame() {
        this.serverAddressText = new JTextField("127.0.0.1", 20);
        this.portText = new JTextField("4400", 8);
        this.numeroText = new JTextField(5);
        this.connectBtn = new JButton("Connect");
        this.disconnectBtn = new JButton("Disconnect");
        this.getBtn = new JButton("Get");
        this.resetBtn = new JButton("Reset");

        this.nordPanel = new JPanel(new FlowLayout());
        this.nordPanel.add(new JLabel("Server Address"));
        this.nordPanel.add(serverAddressText);
        this.nordPanel.add(new JLabel("Port"));
        this.nordPanel.add(portText);
        this.nordPanel.add(connectBtn);
        this.nordPanel.add(disconnectBtn);

        this.griglia = new JButton[25];
        this.leftPanel = new JPanel(new GridLayout(5, 5));
        for(int i = 0; i < 25; i ++) {
            this.griglia[i] = new JButton();
            this.griglia[i].setPreferredSize(new Dimension(70, 70));
            this.griglia[i].setBackground(Color.white);
            this.griglia[i].setEnabled(false);
            this.leftPanel.add(this.griglia[i]);
        }
        this.logArea = new JTextArea(8,26);
        this.logArea.setEditable(false);
        this.logScroll = new JScrollPane(this.logArea);
        this.logScroll.setBorder(BorderFactory.createTitledBorder("Log"));
        this.centroPanel = new JPanel(new FlowLayout());
        this.centroPanel.add(this.leftPanel);
        this.centroPanel.add(logScroll);

        this.sudPanel = new JPanel(new FlowLayout());
        this.sudPanel.add(new JLabel("Numero: "));
        this.sudPanel.add(numeroText);
        this.sudPanel.add(getBtn);
        this.sudPanel.add(this.resetBtn);

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
        this.getBtn.setActionCommand(ClientListener.GET);
        this.getBtn.addActionListener(list);
        this.resetBtn.setActionCommand(ClientListener.RESET);
        this.resetBtn.addActionListener(list);

        // Stato iniziale
        this.setLocationRelativeTo(null);
        this.pack();
        this.setButtons(false, false);
        this.setVisible(true);
    }

    public void setButtons(boolean connected, boolean transmitting) {
        if (connected) {
            this.connectBtn.setEnabled(false);
            if (transmitting) {
                this.disconnectBtn.setEnabled(false);
                this.getBtn.setEnabled(false);
                this.resetBtn.setEnabled(true);
            } else {
                this.disconnectBtn.setEnabled(true);
                this.getBtn.setEnabled(true);
                this.resetBtn.setEnabled(true);
            }
        } else {
            this.connectBtn.setEnabled(true);
            this.disconnectBtn.setEnabled(false);
            this.getBtn.setEnabled(false);
            this.resetBtn.setEnabled(false);
        }
    }
}

// CLASSE ClientListener
import java.awt.Color;
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
    public static final String CONNECT = "CONNECT", DISCONNECT = "DISCONNECT", GET = "GET", RESET = "RESET";

    private ClientFrame frame;
    private DownloadThread downloadThread;
    private Socket sock;
    private Scanner sockScanner;
    private PrintWriter sockWriter;
    private boolean connected, transmitting;

    public ClientListener(ClientFrame frame) {
        this.frame = Objects.requireNonNull(frame, "Client frame must not be null");
    }

    private void setupConnection() throws IOException {
        try {
            String ip = this.frame.serverAddressText.getText();
            int port = Integer.parseInt(this.frame.portText.getText());
            this.sock = new Socket(ip, port);
            this.sockScanner = new Scanner(this.sock.getInputStream());
            this.sockWriter = new PrintWriter(new OutputStreamWriter(this.sock.getOutputStream()));
        } catch (IOException e) {
            throw new IOException("Formato porta non ammesso");
        }
    }

    protected void downloadComplete() {
        this.frame.setButtons(connected, transmitting = false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case ClientListener.CONNECT:
                try {
                    connected = true;
                    this.setupConnection();
                    this.frame.logArea.setText("");
                    this.frame.numeroText.setText("");
                    for (int i = 0; i < 25; i++) {
                        this.frame.griglia[i].setBackground(Color.white);
                    }
                    JOptionPane.showMessageDialog(frame, "Connessione stabilita");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Impossibile connettersi al server: \n" + ex.getMessage());
                    ex.printStackTrace();
                    return;
                }
                break;
            case ClientListener.GET:
                String numero = frame.numeroText.getText();
                try {
                    int num = Integer.parseInt(numero);
                    if (num < 0 || num > 9) {
                        JOptionPane.showMessageDialog(frame, "Inserire un numero compreso tra 0 e 9");
                        break;
                    }
                    for (int i = 0; i < 25; i++) {
                        this.frame.griglia[i].setBackground(Color.white);;
                    }
                    frame.numeroText.setText("");
                    transmitting = true;
                    sockWriter.println(ClientListener.GET + ":" + num);
                    sockWriter.flush();
                    downloadThread = new DownloadThread(frame, this, sockScanner);
                    Thread tr1 = new Thread(downloadThread);
                    tr1.start();
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(frame, "Inserire un numero compreso tra 0 e 9");
                }
                break;
            case ClientListener.RESET:
                this.frame.logArea.setText("");
                this.frame.numeroText.setText("");
                for (int i = 0; i < 25; i++) {
                    this.frame.griglia[i].setBackground(Color.white);
                }
                break;
            case ClientListener.DISCONNECT:
                connected = false;
                transmitting = false;
                this.sockWriter.println(ClientListener.DISCONNECT);
                this.downloadThread.stop();
                try {
                    this.frame.logArea.setText("");
                    this.frame.numeroText.setText("");
                    for (int i = 0; i < 25; i++) {
                        this.frame.griglia[i].setBackground(Color.white);
                    }
                    this.sockScanner.close();
                    this.sockWriter.close();
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
import java.awt.Color;
import java.util.Objects;
import java.util.Scanner;

public class DownloadThread implements Runnable {
    private ClientFrame frame;
    private ClientListener listener;
    private Scanner scanner;
    private boolean running, errored;

    public DownloadThread(ClientFrame frame, ClientListener list, Scanner scan) {
        this.frame = Objects.requireNonNull(frame, "Client frame must not be null");
        this.listener = Objects.requireNonNull(list, "Client listener must not be null");
        this.scanner = Objects.requireNonNull(scan, "Server port must not be null");
    }

    public void stop() {
        if (this.running) this.running = false;
    }

    @Override
    public void run() {
        if (!this.running) this.running = true;
        try {
            while (running) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                String cmd = this.scanner.nextLine();
                System.out.println("Ricevuto: " + cmd);
                if (cmd.equalsIgnoreCase("END")) {
                    this.running = false;
                } else if (cmd.equalsIgnoreCase("INTERRUPTED")) {
                    this.running = false;
                } else if (cmd.equalsIgnoreCase("ERROR")) {
                    this.running = false;
                    this.errored = true;
                } else {
                    String[] tokens = cmd.split(":");
                    this.frame.numeroText.setText(tokens[0]);
                    String[] array = tokens[1].split("");
                    for (int i = 0; i < 25; i++) {
                        if (array[i].equalsIgnoreCase("1")) {
                            this.frame.griglia[i].setBackground(Color.black);
                        }
                    }
                    this.frame.logArea.append(cmd + '\n');
                }
            }
        } catch (InterruptedException e) {
            running = false;
        } finally {
            this.listener.downloadComplete();
            if (errored) this.frame.disconnectBtn.doClick();
        }
    }
}
