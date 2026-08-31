
import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] Args) throws Exception{
        final int setupFrameWidth = 500;
        final int setupFrameHeight = 700;

        Frame setupFrame = new Frame("Simulation Setup");

        setupFrame.setSize(setupFrameWidth, setupFrameHeight);
        setupFrame.setLayout(null);
        setupFrame.setBackground(new Color(40,40,40));
        
        Label title = new Label("Simulation Setup", Label.CENTER);
        title.setBounds(0,60,setupFrameWidth,80);
        title.setBackground(new Color(80,80,80));
        title.setFont(new Font("SansSerif", Font.BOLD, 32)); 
        title.setForeground(new Color(255,255,255));


        setupFrame.add(title);
        setupFrame.setVisible(true);

        Frame mainFrame = new Frame("Forest Fire Simulation");

        //mainFrame

    };
}
