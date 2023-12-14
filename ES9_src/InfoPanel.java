import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private final JLabel label;
    private final JTextField field;

    public InfoPanel(String fieldName) {
        this.setBorder(BorderFactory.createTitledBorder(""));
        this.setLayout(new FlowLayout(FlowLayout.LEADING));

        label = new JLabel(fieldName);
        field = new JTextField(8);

        field.setEditable(false);

        this.add(label);
        this.add(field);
    }

    public void setValue(String value) {
        field.setText(value);
    }

    public void setColor(Color color) {
        field.setBackground(color);
    }

    public void clear() {
        field.setText("");
    }
}
