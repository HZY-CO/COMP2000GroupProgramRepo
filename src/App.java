/*
Resources
https://docs.oracle.com/en/java/javase/26/docs/api/java.desktop/java/awt/Component.html
https://docs.oracle.com/en/java/javase/26/docs/api/java.desktop/java/awt/package-summary.html
*/


//import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App {
    private static int gridWidth = 16;      // The Width of the simulation by number of cells.
    private static int gridHeight = 16;     // The Height of the simulation by number of cells.
    private static int cellSize = 32;       // The size of each cell in pixels. Cells will be square.

    private static double treeGenerateProbablity = 0.7;

    public static void main(String[] Args) throws Exception {
        final int setupFrameWidth = 500;
        final int setupFrameHeight = 700;
        simSetup(setupFrameWidth, setupFrameHeight);

        Frame mainFrame = new Frame("Forest Fire Simulation");

        // mainFrame

    };



    // Method by Ed, Creates a simple frame to take inputs from the user about how the simulation will appear.
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

        // Adjustable Width Attribute
        Panel widthAttribute = new Panel();
        widthAttribute.setLayout(null);
        widthAttribute.setSize(setupFrameWidth, 100);
        widthAttribute.setBackground(new Color(60, 60, 60));
        widthAttribute.setLocation(0, 180);

        Label widthAttributeText = new Label("Simulation Width (By cells)", Label.LEFT);
        widthAttributeText.setBounds(20, 0, setupFrameWidth, 100);
        widthAttributeText.setFont(new Font("SansSerif", Font.BOLD, 24));
        widthAttributeText.setForeground(new Color(255, 255, 255));

        widthAttribute.add(widthAttributeText);

        
        // Adjustable Height Attribute
        Panel heightAttribute = new Panel();
        heightAttribute.setLayout(null);
        heightAttribute.setSize(setupFrameWidth, 100);
        heightAttribute.setBackground(new Color(60, 60, 60));
        heightAttribute.setLocation(0, 300);

        Label heightAttributeText = new Label("Simulation Height (By cells)", Label.LEFT);
        heightAttributeText.setBounds(20, 0, setupFrameWidth, 100);
        heightAttributeText.setFont(new Font("SansSerif", Font.BOLD, 24));
        heightAttributeText.setForeground(new Color(255, 255, 255));

        heightAttribute.add(heightAttributeText);


        //Compile frame content
        setupFrame.add(title);
        setupFrame.add(widthAttribute);
        setupFrame.add(heightAttribute);

        setupFrame.setVisible(true);

        setupFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

    };



    //Method by Aaron,
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
