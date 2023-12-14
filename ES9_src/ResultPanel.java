import java.awt.*;

public class ResultPanel extends InfoPanel {
    public ResultPanel(String fieldName) {
        super(fieldName);
    }

    public void setOperazione(ClientFrame.Operazione operazione) {
        switch (operazione) {
            case SOMMA:
                setColor(Color.CYAN);
                break;

            case MOLTIPLICAZIONE:
                setColor(Color.YELLOW);
                break;

            case DIVISIONE:
                setColor(Color.RED);
                break;
        }
    }

    @Override
    public void clear() {
        super.clear();
        setColor(Color.WHITE);
    }
}
