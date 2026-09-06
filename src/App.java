//import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App {
    private static int gridWidth = 500;
    private static int gridHeight = 500;
    private static double treeGenerateProbablity = 0.7;

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

    private static void generateForestSimulation() {
        EntityManager<Tree> treeEntityManager = new EntityManager<>();
        
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (Math.random() < treeGenerateProbablity) {
                    int maxAge = 50 + (int)(Math.random() * 50);
                    int startAge = 1 + (int)(Math.random() * maxAge);
                    
                    // Spawn tree (1% chance to start already burning)
                    boolean startsOnFire = Math.random() < 0.01; 

                    treeEntityManager.add(new Tree(x, y, maxAge, startAge, startsOnFire));
                }
            }
        }
    }
}
