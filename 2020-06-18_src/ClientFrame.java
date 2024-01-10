import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
    private final JButton startBtn = new JButton("Start");
    private final JButton stopBtn = new JButton("Stop");
    private final JButton rivelaBtn = new JButton("Rivela");

    //JBoardButton
    BoardButton[][] griglia = new BoardButton[10][10];

    //JPanel
    private final JPanel northCtn = new JPanel();
    private final JPanel southCtn = new JPanel();
    private final JPanel centerCtn = new JPanel();

    //Socket, IO
    private Socket socket;
    private Scanner inputScanner;
    private PrintWriter outputWriter;

    //Listener, getInputScanner()
    Board listener = new Board(this);

    public Scanner getInputScanner() {
        return inputScanner;
    }

    public ClientFrame(String title) {
        super(title);
        Container contentPane = this.getContentPane();

        //northCtn
        northCtn.setLayout(new FlowLayout());
        northCtn.add(serverLbl); northCtn.add(serverTxt);
        northCtn.add(portLbl); northCtn.add(portTxt);
        northCtn.add(connectBtn); northCtn.add(disconnectBtn);

        //southCtn
        southCtn.setLayout(new FlowLayout());
        southCtn.add(startBtn); southCtn.add(stopBtn);
        southCtn.add(rivelaBtn);

        //centerCtn
        centerCtn.setLayout(new GridLayout(10,10));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                griglia[i][j] = new BoardButton();
                centerCtn.add(griglia[i][j]);
            }
        }

        //contentPane
        contentPane.setLayout(new BorderLayout());
        contentPane.add(northCtn, BorderLayout.NORTH);
        contentPane.add(southCtn, BorderLayout.SOUTH);
        contentPane.add(centerCtn, BorderLayout.CENTER);


        //setActionCommand
        connectBtn.addActionListener(listener);
        disconnectBtn.addActionListener(listener);
        startBtn.addActionListener(listener);
        stopBtn.addActionListener(listener);
        rivelaBtn.addActionListener(listener);

        connectBtn.setActionCommand("connect");
        disconnectBtn.setActionCommand("disconnect");
        startBtn.setActionCommand("start");
        stopBtn.setActionCommand("stop");
        rivelaBtn.setActionCommand("rivela");

        //setEnable
        connectBtn.setEnabled(true);
        disconnectBtn.setEnabled(false);
        startBtn.setEnabled(false);
        stopBtn.setEnabled(false);
        rivelaBtn.setEnabled(false);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                griglia[i][j].setEnabled(false);
            }
        }


        //visible
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

    }

    void connect() {
        try {
            socket = new Socket(serverTxt.getText(), Integer.parseInt(portTxt.getText()));

            //SetEnable
            connectBtn.setEnabled(false);
            disconnectBtn.setEnabled(true);
            startBtn.setEnabled(true);
            stopBtn.setEnabled(false);
            rivelaBtn.setEnabled(true);

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    griglia[i][j].setEnabled(false);
                }
            }

            //IO
            inputScanner = new Scanner(socket.getInputStream());
            outputWriter = new PrintWriter(socket.getOutputStream());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore nella connessione al server");
        }
    }

    void disconnect() {
        try {
            outputWriter.println("disconnect");
            outputWriter.flush();

            socket.close();
            inputScanner.close();
            outputWriter.close();

            connectBtn.setEnabled(true);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    griglia[i][j].setEnabled(false);
                }
            }
        } catch (Exception ignored) {}
    }

    void start() {
        outputWriter.println("start");
        outputWriter.flush();

        connectBtn.setEnabled(false);
        disconnectBtn.setEnabled(false);
        startBtn.setEnabled(false);
        stopBtn.setEnabled(true);
        rivelaBtn.setEnabled(false);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                griglia[i][j].reset();
            }
        }

    }

    void stop() {
        outputWriter.println("stop");
        outputWriter.flush();

        connectBtn.setEnabled(false);
        disconnectBtn.setEnabled(true);
        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        rivelaBtn.setEnabled(true);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
               griglia[i][j].setEnabled(false);
            }
        }

    }

    void rivela() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                griglia[i][j].setSelected(false);
            }
        }
    }

    void done() {
        JOptionPane.showMessageDialog(null, "La partita può iniziare");
        connectBtn.setEnabled(false);
        disconnectBtn.setEnabled(true);
        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        rivelaBtn.setEnabled(true);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                griglia[i][j].setEnabled(true);
            }
        }

        //TODO: play() {
        //defeat()
        //win()
    }

    void defeat() {

    }

    void win() {

    }



}
