package swing_listener;

import javax.swing.*;
import java.awt.*;

public class FrameTest extends JFrame {

    private final JButton btnSend = new JButton("Invia");
    private final JButton btnQuit = new JButton("Chiudi");

    private final JTextField txtField = new JTextField();

    private final Container pnlButtons = new JPanel();

    public FrameTest() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container pane = this.getContentPane();
        pane.setLayout(new BorderLayout());

        btnSend.setActionCommand("play");
        btnSend.addActionListener(new MyActionListener(this));
//        btnSend.addMouseListener(new MyMouseListener());

        pnlButtons.setLayout(new FlowLayout());
        pnlButtons.add(btnQuit);
        pnlButtons.add(btnSend);

        pane.add(txtField, BorderLayout.CENTER);
        pane.add(pnlButtons, BorderLayout.SOUTH);

        this.pack();
    }

    public String getText() {
        return txtField.getText();
    }
}
