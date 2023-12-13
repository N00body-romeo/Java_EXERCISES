package swing_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyActionListener implements ActionListener {

    private final FrameTest frame;

    public MyActionListener(FrameTest frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "play":
                System.out.println("play: " + frame.getText());
                break;

            case "stop":
                System.out.println("stop: " + e.getActionCommand());
                break;
        }

    }
}
