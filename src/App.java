//import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App {
    public static void main(String[] Args) throws Exception {
        final int setupFrameWidth = 500;
        final int setupFrameHeight = 700;
        simSetup(setupFrameWidth, setupFrameHeight);

        Frame mainFrame = new Frame("Forest Fire Simulation");

        // mainFrame

    };

    private static void simSetup(int setupFrameWidth, int setupFrameHeight) {
        Frame setupFrame = new Frame("Simulation Setup");

        setupFrame.setSize(setupFrameWidth, setupFrameHeight);
        setupFrame.setLayout(null);
        setupFrame.setBackground(new Color(40, 40, 40));
        setupFrame.setResizable(false);

        Label title = new Label("Simulation Setup", Label.CENTER);
        title.setBounds(0, 60, setupFrameWidth, 80);
        title.setBackground(new Color(80, 80, 80));
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(new Color(255, 255, 255));

        //Panel //create function for attributes


        setupFrame.add(title);
        setupFrame.setVisible(true);

        setupFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

    };
}
