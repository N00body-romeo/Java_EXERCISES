import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientFrame extends JFrame {
    //JLabel
    private final JLabel serverLbl = new JLabel("Server Address");
    private final JLabel portLbl = new JLabel("Port");

    private final JLabel vm1Lbl = new JLabel("VM 1");
    private final JLabel vm2Lbl = new JLabel("VM 2");
    private final JLabel vm3Lbl = new JLabel("VM 3");

    private final JLabel CpuLbl = new JLabel("CPU Usage");
    private final JLabel MemoryLbl = new JLabel("Memory Usage");
    private final JLabel DiskLbl = new JLabel("Disk Usage");
    private final JLabel NetworkLbl = new JLabel("Network Usage");


    //JTextField
    private final JTextField serverTxt = new JTextField(20);
    private final JTextField portTxt = new JTextField(10);

    //JButton
    private final JButton connectBtn = new JButton("Connect");
    private final JButton disconnectBtn = new JButton("Disconnect");
    private final JButton startBtn = new JButton("Start");
    private final JButton stopBtn = new JButton("Stop");

    //JRadioButton
    private final JRadioButton vm1Btn = new JRadioButton();
    private final JRadioButton vm2Btn = new JRadioButton();
    private final JRadioButton vm3Btn = new JRadioButton();

    //Buttongroup
    private final ButtonGroup group = new ButtonGroup();

    //JProgessBar
    public final JProgressBar CpuProgr = new JProgressBar();
    public final JProgressBar MemoryProgr = new JProgressBar();
    public final JProgressBar DiskProgr = new JProgressBar();
    public final JProgressBar NetworkProgr = new JProgressBar();

    //JPanel
    private final JPanel northCtn = new JPanel();       //NORTH
    private final JPanel NcenterCtn = new JPanel();     //CENTER
    private final JPanel NsxCenterCtn = new JPanel();
    private final JPanel NdxCenterCtn = new JPanel();
    private final JPanel centerCtn = new JPanel();
    private final JPanel ScenterCtn = new JPanel();
    private final JPanel southCtn = new JPanel();       //SOUTH

    //Socket, IO
    private Socket socket;
    private Scanner inputScanner;
    private PrintWriter outputWriter;

    //Funzioni
    void modificaProg(int dato, JProgressBar progressBar) {
        progressBar.setValue(dato);
    }

    public Scanner getInputScanner() {
        return inputScanner;
    }

    //Listener
    ClientListener listener = new ClientListener(this);

    public ClientFrame(String title) {
        super(title);
        Container contentPane = this.getContentPane();

        //northCtn
        northCtn.setLayout(new FlowLayout());
        northCtn.add(serverLbl); northCtn.add(serverTxt);
        northCtn.add(portLbl); northCtn.add(portTxt);
        northCtn.add(connectBtn); northCtn.add(disconnectBtn);

        //nSINISTROcenterCtn STATUS
        NsxCenterCtn.setLayout(new GridLayout(4,1));
        NsxCenterCtn.add(CpuLbl); NsxCenterCtn.add(MemoryLbl);
        NsxCenterCtn.add(DiskLbl); NsxCenterCtn.add(NetworkLbl);

        //nDESTROcenterCtn USAGE
        NdxCenterCtn.setLayout(new GridLayout(4,1));
        CpuProgr.setStringPainted(true);
        MemoryProgr.setStringPainted(true);
        DiskProgr.setStringPainted(true);
        NetworkProgr.setStringPainted(true);

        NdxCenterCtn.add(CpuProgr); NdxCenterCtn.add(MemoryProgr);
        NdxCenterCtn.add(DiskProgr); NdxCenterCtn.add(NetworkProgr);

        //NcenterCtn VM STATUS
        NcenterCtn.setBorder(BorderFactory.createTitledBorder("VM Status"));
        NcenterCtn.setLayout(new BorderLayout());
        NcenterCtn.add(NsxCenterCtn, BorderLayout.WEST);
        NcenterCtn.add(NdxCenterCtn, BorderLayout.CENTER);

        //ScenterCtn VM
        ScenterCtn.setLayout(new FlowLayout());
        ScenterCtn.setBorder(BorderFactory.createTitledBorder("VM"));
        group.add(vm1Btn); group.add(vm2Btn); group.add(vm3Btn);
        ScenterCtn.add(vm1Btn); ScenterCtn.add(vm1Lbl);
        ScenterCtn.add(vm2Btn); ScenterCtn.add(vm2Lbl);
        ScenterCtn.add(vm3Btn); ScenterCtn.add(vm3Lbl);

        centerCtn.setLayout(new GridLayout(2,1));
        centerCtn.add(NcenterCtn); centerCtn.add(ScenterCtn);

        //southCtn
        southCtn.add(startBtn); southCtn.add(stopBtn);

        //Contentpane
        contentPane.setLayout(new BorderLayout());
        contentPane.add(northCtn, BorderLayout.NORTH);
        contentPane.add(centerCtn, BorderLayout.CENTER);
        contentPane.add(southCtn, BorderLayout.SOUTH);

        //ClientListener
        connectBtn.addActionListener(listener);
        stopBtn.addActionListener(listener);
        disconnectBtn.addActionListener(listener);
        startBtn.addActionListener(listener);

        //setActionCommand
        connectBtn.setActionCommand("connect");
        disconnectBtn.setActionCommand("disconnect");
        startBtn.setActionCommand("start");
        stopBtn.setActionCommand("stop");

        //Visible
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //SetEnable
        disconnectBtn.setEnabled(false);
        startBtn.setEnabled(false);
        stopBtn.setEnabled(false);
        vm1Btn.setEnabled(false);
        vm2Btn.setEnabled(false);
        vm3Btn.setEnabled(false);

    }

    void connect() {
        try {
            //connessione
            socket = new Socket(serverTxt.getText(), Integer.parseInt(portTxt.getText()));

            //SetEnable
            connectBtn.setEnabled(false);
            startBtn.setEnabled(true);
            disconnectBtn.setEnabled(true);
            vm1Btn.setEnabled(true);
            vm2Btn.setEnabled(true);
            vm3Btn.setEnabled(true);

            //IO
            inputScanner = new Scanner(socket.getInputStream());
            outputWriter = new PrintWriter(socket.getOutputStream());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore nella connessione al server");
        }
    }


    void start() {
        //OutputWriter
        if (vm1Btn.isSelected()) {
            outputWriter.println("start:vm1");
            outputWriter.flush();
        } else if (vm2Btn.isSelected()) {
            outputWriter.println("start:vm2");
            outputWriter.flush();
        } else if (vm3Btn.isSelected()) {
            outputWriter.println("start:vm3");
            outputWriter.flush();
        }

        //SetEnable
        stopBtn.setEnabled(true);
        startBtn.setEnabled(false);
        disconnectBtn.setEnabled(false);
        vm1Btn.setEnabled(false);
        vm2Btn.setEnabled(false);
        vm3Btn.setEnabled(false);

        //Thread
        Thread thread = new Thread(new DownloadRunnable(this));
        thread.start();
    }

    void stop() {
        outputWriter.println("stop");
        outputWriter.flush();

        connectBtn.setEnabled(false);
        stopBtn.setEnabled(false);
        startBtn.setEnabled(true);
        disconnectBtn.setEnabled(true);
        vm1Btn.setEnabled(true);
        vm2Btn.setEnabled(true);
        vm3Btn.setEnabled(true);
    }

    void disconnect() {
        outputWriter.println("disconnect");
        outputWriter.flush();

        //chiudere i canali
        try {
            socket.close();
            inputScanner.close();
            outputWriter.close();
        } catch (Exception ignored) {}

        connectBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        startBtn.setEnabled(false);
        disconnectBtn.setEnabled(false);
        vm1Btn.setEnabled(false);
        vm2Btn.setEnabled(false);
        vm3Btn.setEnabled(false);
    }

}
