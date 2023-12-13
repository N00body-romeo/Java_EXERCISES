// Esercitazione 6

// CLASSE MyFrame
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MyFrame extends JFrame {

    // north panel
    private JTextField addressText;
    private JTextField portaText;
    private JButton connectBtn;
    private JButton disconnectBtn;
    private JPanel northPanel;
    // central panel
    private JTextArea logTextArea;
    private JScrollPane logScrollPane;
    private JPanel centralPanel;
    // south panel
    private JButton startBtn;
    private JButton stopBtn;
    private JPanel southpanel;

    // Costruttore
    public MyFrame() {
        // Inizializzazione dei widget
        addressText = new JTextField(20);
        portaText = new JTextField(20);
        connectBtn = new JButton("Connect");
        disconnectBtn = new JButton("Disconnect");
        startBtn = new JButton("Start");
        stopBtn = new JButton("Stop");

        // Layout del pannello nord
        northPanel = new JPanel(new FlowLayout());
        northPanel.add(new JLabel("IP Address"));
        northPanel.add(addressText);
        northPanel.add(new JLabel("Porta"));
        northPanel.add(portaText);
        northPanel.add(connectBtn);
        northPanel.add(disconnectBtn);

        // Layout del pannello centrale
        logTextArea = new JTextArea(8, 20);
        logTextArea.setEditable(false);
        logScrollPane = new JScrollPane(logTextArea);
        logScrollPane.setBorder(BorderFactory.createTitledBorder("Log"));
        centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(logScrollPane);

        // Layout del pannello sud
        southpanel = new JPanel();
        southpanel.add(startBtn);
        southpanel.add(stopBtn);

        // LAYOUT PRINCIPALE
        Container mainContainer = this.getContentPane();
        mainContainer.add(northPanel, BorderLayout.NORTH);
        mainContainer.add(centralPanel, BorderLayout.CENTER);
        mainContainer.add(southpanel, BorderLayout.SOUTH);

        // Ascoltatori degli eventi
        ActionListener list = new ClientListener(this, addressText, portaText);
        connectBtn.setActionCommand(ClientListener.CONNECT);
        connectBtn.addActionListener(list);
        disconnectBtn.setActionCommand(ClientListener.DISCONNECT);
        disconnectBtn.addActionListener(list);
        startBtn.setActionCommand(ClientListener.START);
        startBtn.addActionListener(list);
        stopBtn.setActionCommand(ClientListener.STOP);
        stopBtn.addActionListener(list);

        // Stato iniziale
        setLocation(200, 100);
        setButtons(false, false);
        this.setVisible(true);
    }

    // Imposta lo stato dei pulsanti
    public void setButtons(boolean connected, boolean transmitting) {
        if (connected) {
            connectBtn.setEnabled(false);
            if (transmitting) {
                disconnectBtn.setEnabled(false);
                stopBtn.setEnabled(true);
                startBtn.setEnabled(false);
            } else {
                stopBtn.setEnabled(false);
                startBtn.setEnabled(true);
                disconnectBtn.setEnabled(true);
            }
        } else {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            connectBtn.setEnabled(true);
            disconnectBtn.setEnabled(false);
            startBtn.setEnabled(false);
            stopBtn.setEnabled(false);
        }
    }

    // Restituisce l'oggetto JTextArea per i log
    public JTextArea getLogTextArea() {
        return this.logTextArea;
    }
}

// CLASSE ClientListener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.*;

public class ClientListener implements ActionListener {

    public static final String START = "start", STOP = "stop", CONNECT = "connect", DISCONNECT = "disconnect";
    private JTextField ipAddressField;
    private JTextField portaField;
    private MyFrame frame;

    private boolean connected = false, transmitting = false;

    // Costruttore
    public ClientListener(MyFrame frame, JTextField ipAddr, JTextField porta) {
        this.frame = frame;
        this.ipAddressField = ipAddr;
        this.portaField = porta;
    }

    // Configura la connessione (attualmente solo una stampa di prova)
    private void setupConnection() {
        // logger.log(Level.INFO, "connect " + this.ipAddressField.getText() + ":" + this.portaField.getText());
        System.out.println("connect " + this.ipAddressField.getText() + ":" + this.portaField.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals(ClientListener.CONNECT)) {
            setupConnection();
            connected = true;
        } else if (cmd.equals(ClientListener.START)) {
            transmitting = true;
            //logger.log(Level.INFO, "start");
            System.out.println("start");
            readFromFile(frame);
        } else if (cmd.equals(ClientListener.STOP)) {
            transmitting = false;
            //logger.log(Level.INFO, "stop");
            JTextArea logTextArea = this.frame.getLogTextArea();
            logTextArea.setText("");
            System.out.println("stop");
        } else if (cmd.equals(ClientListener.DISCONNECT)) {
            connected = false;
            //logger.log(Level.INFO, "disconnect");
            System.out.println("disconnect");
        }
        frame.setButtons(connected, transmitting);
    }

    // Legge il contenuto del file "lorem.txt" e lo visualizza nella JTextArea dei log
    public void readFromFile(MyFrame frame) {
        try {
            String line;
            Scanner scan = new Scanner(new File("lorem.txt")); // Imposta la directory di lavoro in "src/client" o utilizza il percorso assoluto
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                JTextArea logTextArea = frame.getLogTextArea();
                String temp = logTextArea.getText();
                temp = temp + line + '\n';
                logTextArea.setText(temp);
                System.out.println(line);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}


/*
Il codice fornito è diviso in due classi: MyFrame e ClientListener. 
La classe MyFrame estende JFrame e rappresenta la finestra principale dell'applicazione. 
La classe ClientListener implementa l'interfaccia ActionListener e gestisce gli eventi generati dai pulsanti nella finestra.

Ecco una breve descrizione delle classi e dei loro componenti:

------------------------------------------------------------------------------------------------------------------------------
*MyFrame*

-Variabili di istanza:-
addressText: JTextField per l'inserimento dell'indirizzo IP.
portaText: JTextField per l'inserimento del numero di porta.
connectBtn: JButton per la connessione.
disconnectBtn: JButton per la disconnessione.
logTextArea: JTextArea per visualizzare i log.
startBtn: JButton per avviare la trasmissione.
stopBtn: JButton per interrompere la trasmissione.

-Metodi:-
Costruttore: Inizializza tutti i componenti grafici, i pannelli e i listener degli eventi.
setButtons(boolean connected, boolean transmitting): Imposta lo stato dei pulsanti in base alla connessione e alla trasmissione.
getLogTextArea(): Restituisce l'oggetto JTextArea utilizzato per visualizzare i log.

------------------------------------------------------------------------------------------------------------------------------
*ClientListener*

-Variabili di istanza:-
ipAddressField: JTextField per l'indirizzo IP.
portaField: JTextField per il numero di porta.
frame: Riferimento all'istanza di MyFrame.
connected: Flag per indicare se è stata stabilita una connessione.
transmitting: Flag per indicare se la trasmissione è attiva.

-Metodi:-
Costruttore: Inizializza le variabili di istanza.
setupConnection(): Metodo privato per configurare la connessione (attualmente solo una stampa di prova).
actionPerformed(ActionEvent e): Gestisce gli eventi dei pulsanti in base al loro comando.
readFromFile(MyFrame frame): Legge il contenuto del file "lorem.txt" e lo visualizza nella JTextArea dei log.
*/
