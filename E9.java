// Esercitazione 9

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

// CLASSE CONNECTIONPANEL
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientFrame extends JFrame{
    JPanel northPanel;
    JTextField serverAddressText;
    JTextField portText;
    JButton connectBtn;
    JButton disconnectBtn;
    
    JPanel centerPanel;
    JTextField num1Text;
    JTextField num2Text;
    JButton resultBtn;
    
    JPanel southPanel;
    JTextField operText;
    JTextField decText;
    JTextField hexText;
    JTextField binText;
    
    ClientFrame(){
        this.serverAddressText = new JTextField("127.0.0.1", 20);
        this.portText = new JTextField("4400", 7);
        this.num1Text = new JTextField(5);
        this.num2Text= new JTextField(5);
        this.operText = new JTextField(20);
        this.operText.setEditable(false);
        this.decText = new JTextField(10);
        this.decText.setEditable(false);
        this.hexText = new JTextField(8);
        this.hexText.setEditable(false);
        this.binText = new JTextField(20);
        this.binText.setEditable(false);
        this.connectBtn = new JButton("Connect");
        this.disconnectBtn = new JButton("Disconnect");
        this.resultBtn = new JButton("=");
        
        //north panel
        this.northPanel = new JPanel(new FlowLayout());
        this.northPanel.add(new JLabel("ServerAddress"));
        this.northPanel.add(serverAddressText);
        this.northPanel.add(new JLabel("Porta"));
        this.northPanel.add(portText);
        this.northPanel.add(connectBtn);
        this.northPanel.add(disconnectBtn);
        
        //center panel
        this.centerPanel = new JPanel(new FlowLayout());
        this.centerPanel.add(new JLabel("1° Intero"));
        this.centerPanel.add(num1Text);
        this.centerPanel.add(new JLabel("2° Intero"));
        this.centerPanel.add(num2Text);
        this.centerPanel.add(resultBtn);
        
        //south panel
        this.southPanel = new JPanel(new FlowLayout());
        this.southPanel.add(new JLabel("Operazione"));
        this.southPanel.add(operText);
        this.southPanel.add(new JLabel("Risultato"));
        this.southPanel.add(decText);
        this.southPanel.add(new JLabel("Esadecimale"));
        this.southPanel.add(hexText);
        this.southPanel.add(new JLabel("Binario"));
        this.southPanel.add(binText);
        
        //container
        Container mainContainer = this.getContentPane();
        mainContainer.add(northPanel, BorderLayout.NORTH);
        mainContainer.add(centerPanel, BorderLayout.CENTER);
        mainContainer.add(southPanel, BorderLayout.SOUTH);
        
        //listener
        ClientListener listener = new ClientListener(this);
        this.connectBtn.setActionCommand(ClientListener.CONNECT);
        this.connectBtn.addActionListener(listener);
        this.disconnectBtn.setActionCommand(ClientListener.DISCONNECT);
        this.disconnectBtn.addActionListener(listener);
        this.resultBtn.setActionCommand(ClientListener.UGUALE);
        this.resultBtn.addActionListener(listener);
        
        //initial state
        this.setButtons(false, false);
    }
    
    void setButtons(boolean connected, boolean transmitting) {
        if(connected) {
            this.connectBtn.setEnabled(false);
            if(transmitting) {
                this.disconnectBtn.setEnabled(false);
                this.num1Text.setEditable(false);
                this.num2Text.setEditable(false);
            }else {
                this.disconnectBtn.setEnabled(true);
                this.num1Text.setEditable(true);
                this.num2Text.setEditable(true);
            }
        }else {
            this.connectBtn.setEnabled(true);
            this.disconnectBtn.setEnabled(false);
            this.num1Text.setEditable(false);
            this.num2Text.setEditable(false);
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

public class ClientListener implements ActionListener{
    static final String CONNECT = "CONNECT", DISCONNECT = "DISCONNECT", UGUALE = "UGUALE";
    
    ClientFrame frame;
    DownloadThread downloadThread;
    Socket sock;
    Scanner sockScanner;
    PrintWriter sockWriter;
    
    private boolean connected, transmitting;
    
    ClientListener(ClientFrame frame){
        this.frame = Objects.requireNonNull(frame, "Client frame must not be null");
    }
    
    private void setupConnection() throws IOException{
        try {
            String serverIP = this.frame.serverAddressText.getText();
            int serverPort = Integer.parseInt(this.frame.portText.getText());
            this.sock = new Socket(serverIP, serverPort);
            this.sockScanner = new Scanner(this.sock.getInputStream());
            this.sockWriter = new PrintWriter(new OutputStreamWriter(this.sock.getOutputStream()));
        }catch(IOException e) {
            throw new IOException("Formato porta non ammesso");
        }
    }
    
    void downloadComplete() { this.frame.setButtons(connected, transmitting = false); }
    
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch(cmd) {
            case ClientListener.CONNECT:
                try {
                    this.connected = true;
                    this.setupConnection();
                    JOptionPane.showMessageDialog(frame, "Connessione stabilita!");
                    this.frame.operText.setText("");
                    this.frame.decText.setText("");
                    this.frame.hexText.setText("");
                    this.frame.binText.setText("");
                    this.frame.operText.setBackground(Color.lightGray);
                }catch(IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Impossibile connettersi al server: \n" + ex.getMessage());
                    ex.printStackTrace();
                    return;
                }
                break;
            case ClientListener.UGUALE:
                String n1 = this.frame.num1Text.getText();
                String n2 = this.frame.num2Text.getText();
                try {
                    if(Integer.parseInt(n1) < 0 || Integer.parseInt(n2) < 0) {
                        JOptionPane.showMessageDialog(frame, "Inserire dei numeri interi positivi");
                        break;
                    }

                    this.frame.operText.setText("");
                    this.frame.decText.setText("");
                    this.frame.hexText.setText("");
                    this.frame.binText.setText("");
                    this.frame.decText.setBackground(Color.lightGray);
                    this.frame.hexText.setBackground(Color.lightGray);
                    this.frame.binText.setBackground(Color.lightGray);
                    this.transmitting = true;
                    this.downloadThread = new DownloadThread(this.frame, this, this.sockScanner);
                    this.sockWriter.println("uguale:" + n1 + ":" + n2);
                    this.sockWriter.flush();
                    Thread th = new Thread(this.downloadThread);
                    th.start();
                }catch(NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(frame, "Inserire dei numeri interi positivi");
                    break;
                }
                break;
            case ClientListener.DISCONNECT:
                this.connected = false;
                this.transmitting = false;
                this.sockWriter.println(ClientListener.DISCONNECT);
                try {
                    this.sockWriter.close();
                    this.sockScanner.close();
                    this.sock.close();
                }catch(IOException ex) {
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

public class DownloadThread implements Runnable{
    private ClientFrame frame;
    private ClientListener listener;
    Scanner scanner;
    
    private boolean running, errored;
    
    DownloadThread(ClientFrame frame, ClientListener list, Scanner scan){
        this.frame = Objects.requireNonNull(frame, "Client frame must not be null");
        this.listener = Objects.requireNonNull(list, "Client frame must not be null");
        this.scanner = Objects.requireNonNull(scan, "Client frame must not be null");
    }
    
    @Override
    public void run() {
        if(!running) running = true;
        try{
            while(running) {
                if(Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                String cmd = this.scanner.nextLine();
                System.out.println("Ricevuto: " + cmd);
                if(cmd.equalsIgnoreCase("END")) {
                    running = false;
                }else if(cmd.equalsIgnoreCase("ERROR")) {
                    running = false;
                    errored = true;
                }else {
                    String[] tokens = cmd.split(":");
                    this.frame.operText.setText(tokens[0]);
                    this.frame.decText.setText(tokens[1]);
                    this.frame.hexText.setText(Integer.toHexString(Integer.parseInt(tokens[1])));
                    this.frame.binText.setText(Integer.toBinaryString(Integer.parseInt(tokens[1])));
                    if(tokens[0].equalsIgnoreCase("somma")) {
                        this.frame.decText.setBackground(Color.cyan);
                        this.frame.hexText.setBackground(Color.cyan);
                        this.frame.binText.setBackground(Color.cyan);
                    }else if(tokens[0].equalsIgnoreCase("moltiplicazione")) {
                        this.frame.decText.setBackground(Color.yellow);
                        this.frame.hexText.setBackground(Color.yellow);
                        this.frame.binText.setBackground(Color.yellow);
                    }else if(tokens[0].equalsIgnoreCase("divisione")) {
                        this.frame.decText.setBackground(Color.red);
                        this.frame.hexText.setBackground(Color.red);
                        this.frame.binText.setBackground(Color.red);
                    }
                }
            }
        }catch(InterruptedException e) {
            running = false;
        }finally {
            this.listener.downloadComplete();
            if(errored) this.frame.disconnectBtn.doClick();
        }
    }
}

// Commenti
/*
ClientFrame Class:

La classe rappresenta la finestra principale dell'applicazione.
Suddivisa in pannelli (north, center, south) per organizzare l'interfaccia grafica.
I campi vengono inizializzati all'interno del costruttore.
Viene utilizzato un listener (ClientListener) per gestire le azioni degli utenti sugli elementi dell'interfaccia.
Il metodo setButtons regola lo stato dei pulsanti in base allo stato della connessione.
ClientListener Class:

Gestisce le azioni dell'utente (pulsanti di connessione, disconnessione, calcolo risultato).
setupConnection crea una connessione al server.
downloadComplete notifica il completamento del download al thread principale.
DownloadThread Class:

Esegue un thread separato per gestire la ricezione dei dati dal server.
Aggiorna l'interfaccia grafica con i risultati ricevuti dal server.
Notifica il completamento al listener principale.

*/
