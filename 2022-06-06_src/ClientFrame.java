import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;


public class ClientFrame extends JFrame {

    //JLabel
    private final JLabel serverLbl = new JLabel("Server Address");
    private final JLabel portLbl = new JLabel("Port");

    //JTextField
    private final JTextField serverTxt = new JTextField(20);
    private final JTextField portTxt = new JTextField(10);

    //JButton
    private final JButton connectBtn = new JButton("Connect");
    private final JButton disconnectBtn = new JButton("Disconnect");
    private final JButton artistiBtn = new JButton("Artisti");
    private final JButton canzoniBtn = new JButton("Canzoni");
    private final JButton stopBtn = new JButton("Stop");

    //JPanel principali
    private final Container northCtn = new JPanel();
    private final Container centerCtn = new JPanel();
    private final Container southCtn = new JPanel();

    //JPanel centerCtn
    private final JTextArea artistiCanzoniTXT = new JTextArea(10,20);
    private final MyLogPane logPane = new MyLogPane("Log");


    //ActionListener
    private final ClientListener listener = new ClientListener(this);

    //Socket, Input (Scanner), Output (Printer)
    private Socket socket;
    private Scanner inputScanner;
    private PrintWriter outputWriter;

    public ClientFrame(String title) {
        super(title);
        Container contentPane = this.getContentPane();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //northCtn
        northCtn.setLayout(new FlowLayout());

        northCtn.add(serverLbl); northCtn.add(serverTxt);
        northCtn.add(portLbl); northCtn.add(portTxt);
        northCtn.add(connectBtn); northCtn.add(disconnectBtn);

        //southCtn
        southCtn.setLayout(new FlowLayout());
        southCtn.add(artistiBtn);
        southCtn.add(canzoniBtn);
        southCtn.add(stopBtn);

        //centerCtn
        centerCtn.setLayout(new BorderLayout());
        artistiCanzoniTXT.setBorder(BorderFactory.createTitledBorder("Artisti e Canzoni"));
        artistiCanzoniTXT.setSize(40,20);
        artistiCanzoniTXT.setEditable(false);
        centerCtn.add(logPane, BorderLayout.CENTER);
        centerCtn.add(artistiCanzoniTXT, BorderLayout.AFTER_LINE_ENDS);

        //ContentPane
        contentPane.setLayout(new BorderLayout());
        contentPane.add(northCtn, BorderLayout.NORTH);
        contentPane.add(southCtn, BorderLayout.SOUTH);
        contentPane.add(centerCtn, BorderLayout.CENTER);

        //Enable pulsanti
        disconnectBtn.setEnabled(false);
        stopBtn.setEnabled(false);
        artistiBtn.setEnabled(false);
        canzoniBtn.setEnabled(false);

        //ClientListener
        connectBtn.addActionListener(listener);
        disconnectBtn.addActionListener(listener);
        stopBtn.addActionListener(listener);
        artistiBtn.addActionListener(listener);
        canzoniBtn.addActionListener(listener);

        //setActionCommand
        connectBtn.setActionCommand("connect");
        disconnectBtn.setActionCommand("disconnect");
        stopBtn.setActionCommand("stop");
        artistiBtn.setActionCommand("artistiAct");
        canzoniBtn.setActionCommand("canzoniAct");

        //Finale
        this.setVisible(true);
        this.pack();
    }

    public MyLogPane getLogPane() {
        return logPane;
    }

    public JTextArea getArtistiCanzoniTXT() {
        return artistiCanzoniTXT;
    }

    void aggiungiRigaTXT(String riga) {
        getArtistiCanzoniTXT().append(riga + "\n");
    }

    //Scanner
    public Scanner getInputScanner() {
        return inputScanner;
    }

    void connect() {
        try {
            //connessione
            socket = new Socket(serverTxt.getText(), Integer.parseInt(portTxt.getText()));

            //enable pulsanti
            disconnectBtn.setEnabled(true);
            artistiBtn.setEnabled(true);
            connectBtn.setEnabled(false);

            //IO
            inputScanner = new Scanner(socket.getInputStream());
            outputWriter = new PrintWriter(socket.getOutputStream());

            //clear
            logPane.clear();
            artistiCanzoniTXT.setText("");
        }

        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore nella connessione");
        }
    }
    void artistiAct() {
        //Invia "artisti"
        outputWriter.println("artisti");
        outputWriter.flush();

        //enable pulsanti
        disconnectBtn.setEnabled(false);
        artistiBtn.setEnabled(false);
        stopBtn.setEnabled(true);

        //clear
        artistiCanzoniTXT.setText("");
        logPane.clear();

        //Thread
        Thread thread = new Thread(new DonwloadRunnable(this));
        thread.start();

    }

    void canzoniAct() {
        outputWriter.println("canzoni");
        outputWriter.flush();

        canzoniBtn.setEnabled(false);
        disconnectBtn.setEnabled(false);
        stopBtn.setEnabled(true);

        Thread thread2 = new Thread(new CanzoniDowload(this));
        thread2.start();

    }

    void disconnect() {
        //invia "disconnect"
        outputWriter.println("disconnect");
        outputWriter.flush();

        //chiude tutti i canali
        try {
            inputScanner.close();
            outputWriter.close();
            socket.close();
        } catch (Exception e) { }

        artistiBtn.setEnabled(false);
        canzoniBtn.setEnabled(false);
        stopBtn.setEnabled(false);
        disconnectBtn.setEnabled(false);
        connectBtn.setEnabled(true);
    }

    void stop() {
        outputWriter.println("stop");
        outputWriter.flush();

        stopBtn.setEnabled(false);
        disconnectBtn.setEnabled(true);
    }

    void fineDownload() {
        canzoniBtn.setEnabled(true);
        artistiBtn.setEnabled(false);
    }

    void fineDownloadCanzoni() {
        canzoniBtn.setEnabled(false);
    }
}
