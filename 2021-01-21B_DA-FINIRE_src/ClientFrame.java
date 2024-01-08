import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.math.*;

public class ClientFrame extends JFrame {

    //JLabel
    private final JLabel serverLbl = new JLabel("Server Address");
    private final JLabel portLbl = new JLabel("Port");

    private final JLabel Ruota1Lbl = new JLabel("Ruota 1");
    private final JLabel Ruota2Lbl = new JLabel("Ruota 2");
    private final JLabel Ruota3Lbl = new JLabel("Ruota 3");
    private final JLabel Ruota4Lbl = new JLabel("Ruota 4");
    private final JLabel Ruota5Lbl = new JLabel("Ruota 5");

    //JTextField
    private final JTextField serverTxt = new JTextField(20);
    private final JTextField portTxt = new JTextField(10);

    //JButton
    private final JButton connectBtn = new JButton("Connect");
    private final JButton disconnectBtn = new JButton("Disconnect");
    private final JButton startBtn = new JButton("Start");
    private final JButton stopBtn = new JButton("Stop");
    private final JButton resetBtn = new JButton("Reset");
    private final JButton randomizeBtn = new JButton("Randomize");

    //JComboBox
    List<LotteryComboBox> ruote = new ArrayList<>();

    //JPanel
    private final JPanel northCtn = new JPanel(); //North

    private final JPanel centerCtn = new JPanel(); //Center
    private final JPanel DXcenterCtn = new JPanel(); //Center
    private final JPanel SXcenterCtn = new JPanel(); //Center

    private final JPanel southCtn = new JPanel(); //South

    //Socket, IO
    private Socket socket;
    private Scanner inputScanner;
    private PrintWriter outputWriter;

    //Listener
    ClientListener listener = new ClientListener(this);

    //Scanner
    public Scanner getInputScanner() {
        return inputScanner;
    }

    public ClientFrame(String title) {
        super(title);
        Container contentPane = this.getContentPane();

        //northCtn
        northCtn.setLayout(new FlowLayout());
        northCtn.add(serverLbl);
        northCtn.add(serverTxt);
        northCtn.add(portLbl);
        northCtn.add(portTxt);
        northCtn.add(connectBtn);
        northCtn.add(disconnectBtn);

        //southCtn
        southCtn.setLayout(new FlowLayout());
        southCtn.add(startBtn);
        southCtn.add(stopBtn);
        southCtn.add(resetBtn);
        southCtn.add(randomizeBtn);

        //centerCtn
        centerCtn.setLayout(new BorderLayout());
        centerCtn.setBorder(BorderFactory.createTitledBorder("Lotteria"));

        SXcenterCtn.setLayout(new GridLayout(5, 1));
        DXcenterCtn.setLayout(new GridLayout(5, 5));

        SXcenterCtn.add(Ruota1Lbl);
        SXcenterCtn.add(Ruota2Lbl);
        SXcenterCtn.add(Ruota3Lbl);
        SXcenterCtn.add(Ruota4Lbl);
        SXcenterCtn.add(Ruota5Lbl);

        for (int i = 0; i < 25; i++) {
            ruote.add(new LotteryComboBox());
            DXcenterCtn.add(ruote.get(i));
            ruote.get(i).setReadOnly(true);
        }

        centerCtn.add(SXcenterCtn, BorderLayout.WEST);
        centerCtn.add(DXcenterCtn, BorderLayout.CENTER);

        //Riempire ContentPane
        contentPane.setLayout(new BorderLayout());
        contentPane.add(northCtn, BorderLayout.NORTH);
        contentPane.add(southCtn, BorderLayout.SOUTH);
        contentPane.add(centerCtn, BorderLayout.CENTER);

        //ClientListener and setActionCommand
        connectBtn.addActionListener(listener);
        disconnectBtn.addActionListener(listener);
        startBtn.addActionListener(listener);
        stopBtn.addActionListener(listener);
        resetBtn.addActionListener(listener);
        randomizeBtn.addActionListener(listener);

        connectBtn.setActionCommand("connect");
        disconnectBtn.setActionCommand("disconnect");
        startBtn.setActionCommand("start");
        stopBtn.setActionCommand("stop");
        resetBtn.setActionCommand("reset");
        randomizeBtn.setActionCommand("randomize");


        //Funzioni
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

        //Avvio
        connectBtn.setEnabled(true);
        disconnectBtn.setEnabled(false);
        startBtn.setEnabled(false);
        stopBtn.setEnabled(false);
        resetBtn.setEnabled(false);
        randomizeBtn.setEnabled(false);

    }

    void connect() {
        try {
            socket = new Socket(serverTxt.getText(), Integer.parseInt(portTxt.getText()));

            inputScanner = new Scanner(socket.getInputStream());
            outputWriter = new PrintWriter(socket.getOutputStream());

            connectBtn.setEnabled(false);
            disconnectBtn.setEnabled(true);
            startBtn.setEnabled(true);
            stopBtn.setEnabled(false);
            resetBtn.setEnabled(true);
            randomizeBtn.setEnabled(true);

            for (int i = 0; i < 25; i++) {
                ruote.get(i).setReadOnly(false);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore di connessione al server");
        }
    }

    void start() {
        boolean ok = ok();

       if (ok) {
           outputWriter.println("start");
           outputWriter.flush();

           connectBtn.setEnabled(false);
           disconnectBtn.setEnabled(false);
           startBtn.setEnabled(false);
           stopBtn.setEnabled(true);
           resetBtn.setEnabled(false);
           randomizeBtn.setEnabled(false);

           for (int i = 0; i < 25; i++) {
               ruote.get(i).setChecked(false);
               ruote.get(i).setReadOnly(true);
           }
       }

        if (ok == false) {
            JOptionPane.showMessageDialog(null, "Errore nelle ruote");
        }

       //Thread
        Thread thread = new Thread(new DownloadRunnable(this));
        thread.start();
    }

    void stop() {
        outputWriter.println("stop");
        outputWriter.flush();

        connectBtn.setEnabled(false);
        disconnectBtn.setEnabled(true);
        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        resetBtn.setEnabled(true);
        randomizeBtn.setEnabled(true);

        for (int i = 0; i < 25; i++) {
            ruote.get(i).setReadOnly(false);
        }

    }

    void reset() {
        for (int i = 0; i < 25; i++) {
            ruote.get(i).setSelectedIndex(0);
            ruote.get(i).setChecked(false);
        }
    }

    void randomize() {
        /*for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                List<LotteryComboBox> ruotaOK = new ArrayList<>();
                ruotaOK.get(i*j).setSelectedIndex(new Random().nextInt(0,90));

                }
            }
        }*/
    }

    void disconnect() {
        outputWriter.println("disconnect");
        outputWriter.flush();

        try {
            socket.close();
            inputScanner.close();
            outputWriter.close();
        } catch (Exception ignored) {
        }

        connectBtn.setEnabled(true);
        disconnectBtn.setEnabled(false);
        startBtn.setEnabled(false);
        stopBtn.setEnabled(false);
        resetBtn.setEnabled(false);
        randomizeBtn.setEnabled(false);

        for (int i = 0; i < 25; i++) {
            ruote.get(i).setReadOnly(true);
        }
    }

    void done() {
        int counterRuota = 0;

        for (int i = 0; i < 5; i++) {
            int counterBox = 0;
            for (int j = 0; j < 5; j++) {
                if (ruote.get(j).isChecked()) {
                    counterBox+=1;
                }
            }
            counterRuota+=1;
            JOptionPane.showMessageDialog(null, "Ruota "+counterRuota+":"+counterBox);
        }

        connectBtn.setEnabled(false);
        disconnectBtn.setEnabled(true);
        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        resetBtn.setEnabled(true);
        randomizeBtn.setEnabled(true);

        for (int i = 0; i < 25; i++) {
            ruote.get(i).setReadOnly(false);
        }
    }

    boolean ok() {
        boolean ok = true;
        for (int i = 0; i < 25; i++) {
            if (!ruote.get(i).isNumberSelected()) {
                ok = false;
                break;
            }
        }

        for (int i = 1; i < 6; i++) {
            Set<Integer> ruota = new HashSet<>();

            for (int j = 1; j < 6; j++) {
                ruota.add(ruote.get(i * j).getSelectedIndex()+1);
            }

            for (int k = 0; k < 5; k++) {
                if (ruota.size() < 5) {
                    ok = false;
                    break;
                }
            }
        }
        return ok;
    }

}
