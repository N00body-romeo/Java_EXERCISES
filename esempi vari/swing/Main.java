package swing;

// pacchetto di Swing
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JFrame frame1 = new JFrame("Titolo");

        // imposta il titolo della finestra
        frame.setTitle("Titolo frame 1");
        frame1.setTitle("Titolo frame 2");

        // imposta la dimensione della finestra
        frame.setSize(200, 100);
        frame1.setSize(100, 200);

        // imposta la posizione iniziale della finestra
        frame.setLocation(0, 0);

        // imposta la visibilità della finestra (default è false)
        frame.setVisible(true);
        // frame1.setVisible(true);

        // imposta l'azione al click della 'x' sulla finestra
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(new JLabel("ciao mondo"));
        contentPane.add(new JLabel("ciao mondo 1"));
        contentPane.add(new JLabel("ciao mondo 2"));
        contentPane.add(new JLabel("ciao mondo 3"));

        Container gridContainer = new Container();
        gridContainer.setLayout(new GridLayout(3,3));

        // ottimizza la dimensione della finestra al contenuto
//        frame.pack();
    }
}
