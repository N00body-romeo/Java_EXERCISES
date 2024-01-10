import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientFrame extends JFrame {
    private final JLabel serverLbl = new JLabel("Server Address");
    private final JLabel portLbl = new JLabel("Port");
    private final JLabel commandLbl = new JLabel("Command");

    private final JTextField serverTxt = new JTextField(20);
    private final JTextField portTxt = new JTextField(10);
    private final JTextField commandTxt = new JTextField(30);

    private final JButton connectBtn = new JButton("Connect");
    private final JButton disconnectBtn = new JButton("Disconnect");
    private final JButton executeBtn = new JButton("Execute");
    private final JButton interruptBtn = new JButton("Interrupt");

    private ConsoleTXT centerTEXT = new ConsoleTXT("Console");

    private final JPanel northCtn = new JPanel();
    private final JPanel southCtn = new JPanel();
    private final JPanel centerCtn = new JPanel();

    private Socket socket;
    private Scanner inputScanner;

    public Scanner getInputScanner() {
        return inputScanner;
    }

    private PrintWriter outputWriter;

    ClientListener listener = new ClientListener(this);

    public ClientFrame(String title) {
        super(title);
        Container contentPane = this.getContentPane();

        //northCtn
        northCtn.setLayout(new FlowLayout());
        northCtn.add(serverLbl); northCtn.add(serverTxt);
        northCtn.add(portLbl); northCtn.add(portTxt);
        northCtn.add(connectBtn); northCtn.add(disconnectBtn);

        //south
        southCtn.setLayout(new FlowLayout());
        southCtn.add(commandLbl); southCtn.add(commandTxt);
        southCtn.add(executeBtn); southCtn.add(interruptBtn);

        //center
        centerCtn.setLayout(new BorderLayout());
        centerCtn.add(centerTEXT, BorderLayout.CENTER);

        //contentPane
        contentPane.setLayout(new BorderLayout());
        contentPane.add(northCtn, BorderLayout.NORTH);
        contentPane.add(southCtn, BorderLayout.SOUTH);
        contentPane.add(centerCtn, BorderLayout.CENTER);

        //addListener and setActionCommand
        connectBtn.addActionListener(listener);
        disconnectBtn.addActionListener(listener);
        executeBtn.addActionListener(listener);
        interruptBtn.addActionListener(listener);

        connectBtn.setActionCommand("connect");
        disconnectBtn.setActionCommand("disconnect");
        executeBtn.setActionCommand("execute");
        interruptBtn.setActionCommand("interrupt");

        //pulsantiEnable
        connectBtn.setEnabled(true);
        disconnectBtn.setEnabled(false);
        executeBtn.setEnabled(false);
        interruptBtn.setEnabled(false);


        //setVisible
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();


    }

    void connect() {
        try {
            socket = new Socket(serverTxt.getText(), Integer.parseInt(portTxt.getText()));

            connectBtn.setEnabled(false);
            disconnectBtn.setEnabled(true);
            executeBtn.setEnabled(true);
            interruptBtn.setEnabled(false);

            inputScanner = new Scanner(socket.getInputStream());
            outputWriter = new PrintWriter(socket.getOutputStream());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore nella connessione al server");
        }
    }
    void disconnect() {
        try {
            outputWriter.println("DISCONNECT");
            outputWriter.flush();

            socket.close();
            inputScanner.close();
            outputWriter.close();

            connectBtn.setEnabled(true);
            executeBtn.setEnabled(false);
            disconnectBtn.setEnabled(false);

        } catch (Exception ignored) {}
    }

    void execute() {
        outputWriter.println(commandTxt.getText());
        outputWriter.flush();

        centerTEXT.clear();

        connectBtn.setEnabled(false);
        disconnectBtn.setEnabled(false);
        executeBtn.setEnabled(false);
        interruptBtn.setEnabled(true);

        Thread thread = new Thread(new DownloadRunnable(this));
        thread.start();
    }

    void interrupt() {
        outputWriter.println("INTERRUPT");
        outputWriter.flush();
    }

    public ConsoleTXT getConsole() {
        return centerTEXT;
    }

    void end() {
        interruptBtn.setEnabled(false);
        executeBtn.setEnabled(true);
        disconnectBtn.setEnabled(true);

    }
}
