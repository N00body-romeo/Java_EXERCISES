import javax.swing.*;

public class MyScrollPane extends JScrollPane {

    private final JTextArea textArea = new JTextArea(4, 20);

    public MyScrollPane(String titolo) {
        super(null, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        textArea.setEditable(false);

        this.setViewportView(textArea);
        this.setBorder(BorderFactory.createTitledBorder(titolo));
    }

    public void aggiungiRiga(String riga) {
        textArea.append(riga + '\n');
    }

    public boolean contiene(String s) {
        return textArea.getText().contains(s);
    }

    public void clear() {
        textArea.setText("");
    }
}
