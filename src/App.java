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
    private static int maxGridWidth = 512;      
    private static int maxGridHeight = 512;  

    private static int cellSize = 32;       // The size of each cell in pixels. Cells will be square.
    private static int maxCellSize = 512;

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

        Label widthAttributeText = new Label("Simulation Width", Label.LEFT);
        widthAttributeText.setBounds(20, 0, setupFrameWidth, 100);
        widthAttributeText.setFont(new Font("SansSerif", Font.BOLD, 24));
        widthAttributeText.setForeground(new Color(255, 255, 255));

        Label widthAttributeValue = new Label(Integer.toString(gridWidth), Label.CENTER);
        widthAttributeValue.setBounds(setupFrameWidth-180, 30, 80, 40);
        widthAttributeValue.setFont(new Font("SansSerif", Font.BOLD, 32));
        widthAttributeValue.setForeground(new Color(255, 255, 255));

        Button widthDecreaseBtn = new Button("<");
        widthDecreaseBtn.setBounds(setupFrameWidth-220, 30, 40, 40);
        widthDecreaseBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        widthDecreaseBtn.setForeground(new Color(255, 255, 255));
        widthDecreaseBtn.setBackground(new Color(40, 40, 40));
        widthDecreaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gridWidth -= 1;
                if(gridWidth <= 0)
                {
                    gridWidth = 1;
                }
                widthAttributeValue.setText(Integer.toString(gridWidth));
            }
        });

        Button widthIncreaseBtn = new Button(">");
        widthIncreaseBtn.setBounds(setupFrameWidth-100, 30, 40, 40);
        widthIncreaseBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        widthIncreaseBtn.setForeground(new Color(255, 255, 255));
        widthIncreaseBtn.setBackground(new Color(40, 40, 40));
        widthIncreaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gridWidth += 1;
                if(gridWidth > maxGridWidth)
                {
                    gridWidth = maxGridWidth;
                }
                widthAttributeValue.setText(Integer.toString(gridWidth));
            }
        });

        widthAttribute.add(widthDecreaseBtn);
        widthAttribute.add(widthIncreaseBtn);
        widthAttribute.add(widthAttributeValue);
        widthAttribute.add(widthAttributeText);

        // Adjustable Width Attribute
        Panel heightAttribute = new Panel();
        heightAttribute.setLayout(null);
        heightAttribute.setSize(setupFrameWidth, 100);
        heightAttribute.setBackground(new Color(60, 60, 60));
        heightAttribute.setLocation(0, 300);

        Label heightAttributeText = new Label("Simulation Height", Label.LEFT);
        heightAttributeText.setBounds(20, 0, setupFrameWidth, 100);
        heightAttributeText.setFont(new Font("SansSerif", Font.BOLD, 24));
        heightAttributeText.setForeground(new Color(255, 255, 255));

        Label heightAttributeValue = new Label(Integer.toString(gridHeight), Label.CENTER);
        heightAttributeValue.setBounds(setupFrameWidth-180, 30, 80, 40);
        heightAttributeValue.setFont(new Font("SansSerif", Font.BOLD, 32));
        heightAttributeValue.setForeground(new Color(255, 255, 255));

        Button heightDecreaseBtn = new Button("<");
        heightDecreaseBtn.setBounds(setupFrameWidth-220, 30, 40, 40);
        heightDecreaseBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        heightDecreaseBtn.setForeground(new Color(255, 255, 255));
        heightDecreaseBtn.setBackground(new Color(40, 40, 40));
        heightDecreaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gridHeight -= 1;
                if(gridHeight <= 0)
                {
                    gridHeight = 1;
                }
                heightAttributeValue.setText(Integer.toString(gridHeight));
            }
        });

        Button heightIncreaseBtn = new Button(">");
        heightIncreaseBtn.setBounds(setupFrameWidth-100, 30, 40, 40);
        heightIncreaseBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        heightIncreaseBtn.setForeground(new Color(255, 255, 255));
        heightIncreaseBtn.setBackground(new Color(40, 40, 40));
        heightIncreaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gridHeight += 1;
                if(gridHeight > maxGridHeight)
                {
                    gridHeight = maxGridHeight;
                }
                heightAttributeValue.setText(Integer.toString(gridHeight));
            }
        });

        heightAttribute.add(heightDecreaseBtn);
        heightAttribute.add(heightIncreaseBtn);
        heightAttribute.add(heightAttributeValue);
        heightAttribute.add(heightAttributeText);

        // Adjustable Cell Size Attribute
        Panel cellSizeAttribute = new Panel();
        cellSizeAttribute.setLayout(null);
        cellSizeAttribute.setSize(setupFrameWidth, 100);
        cellSizeAttribute.setBackground(new Color(60, 60, 60));
        cellSizeAttribute.setLocation(0, 420);

        Label cellSizeAttributeText = new Label("Cell Size (in pixels)", Label.LEFT);
        cellSizeAttributeText.setBounds(20, 0, setupFrameWidth, 100);
        cellSizeAttributeText.setFont(new Font("SansSerif", Font.BOLD, 24));
        cellSizeAttributeText.setForeground(new Color(255, 255, 255));

        Label cellSizeAttributeValue = new Label(Integer.toString(gridHeight), Label.CENTER);
        cellSizeAttributeValue.setBounds(setupFrameWidth-180, 30, 80, 40);
        cellSizeAttributeValue.setFont(new Font("SansSerif", Font.BOLD, 32));
        cellSizeAttributeValue.setForeground(new Color(255, 255, 255));

        Button cellSizeDecreaseBtn = new Button("<");
        cellSizeDecreaseBtn.setBounds(setupFrameWidth-220, 30, 40, 40);
        cellSizeDecreaseBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        cellSizeDecreaseBtn.setForeground(new Color(255, 255, 255));
        cellSizeDecreaseBtn.setBackground(new Color(40, 40, 40));
        cellSizeDecreaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cellSize -= 1;
                if(cellSize <= 0)
                {
                    cellSize = 1;
                }
                heightAttributeValue.setText(Integer.toString(cellSize));
            }
        });

        Button cellSizeIncreaseBtn = new Button(">");
        cellSizeIncreaseBtn.setBounds(setupFrameWidth-100, 30, 40, 40);
        cellSizeIncreaseBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        cellSizeIncreaseBtn.setForeground(new Color(255, 255, 255));
        cellSizeIncreaseBtn.setBackground(new Color(40, 40, 40));
        cellSizeIncreaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cellSize += 1;
                if(cellSize > maxCellSize)
                {
                    cellSize = maxCellSize;
                }
                cellSizeAttributeValue.setText(Integer.toString(cellSize));
            }
        });


        cellSizeAttribute.add(cellSizeDecreaseBtn);
        cellSizeAttribute.add(cellSizeIncreaseBtn);
        cellSizeAttribute.add(cellSizeAttributeValue);
        cellSizeAttribute.add(cellSizeAttributeText);

        // Button that locks in the setup values and creates the simulation.
        Button createSimulationBtn = new Button("Create");
        createSimulationBtn.setBounds(100, setupFrameHeight-120, 300, 80);
        createSimulationBtn.setFont(new Font("SansSerif", Font.BOLD, 42));
        createSimulationBtn.setForeground(new Color(255, 255, 255));
        createSimulationBtn.setBackground(new Color(40, 40, 40));
        createSimulationBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateForestSimulation();
                setupFrame.dispose();
            }
        });


        //Compile frame content
        setupFrame.add(title);
        setupFrame.add(widthAttribute);
        setupFrame.add(heightAttribute);
        setupFrame.add(cellSizeAttribute);
        setupFrame.add(createSimulationBtn);

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
