import javax.swing.*;

public class ConsoleTXT extends JScrollPane {

    private final JTextArea textArea = new JTextArea(25,80);

    public ConsoleTXT(String titolo) {
        super(null, JScrollPane.VERTICAL_SCROLLBAR_NEVER, HORIZONTAL_SCROLLBAR_NEVER);
        this.setViewportView(textArea);
        textArea.setEditable(false);
        this.setBorder(BorderFactory.createTitledBorder(titolo));
    }

    void clear() {
        textArea.setText("");
    }

    public void aggiungiRiga(String riga) {
        textArea.append(riga + "\n");
    }

}
