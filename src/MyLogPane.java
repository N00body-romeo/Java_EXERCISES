import javax.swing.*;

public class MyLogPane extends JScrollPane {

    private final JTextArea textArea = new JTextArea();

    public MyLogPane(String titolo) {
        super(null, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        textArea.setEditable(false);

        this.setViewportView(textArea);
        this.setBorder(BorderFactory.createTitledBorder(titolo));
    }

    public void clear() {
        textArea.setText("");
    }

    public void aggiungiRiga(String riga) {
        textArea.append(riga + '\n');
    }

    public boolean contiene(String s) {
        return textArea.getText().contains(s);
    }
}
