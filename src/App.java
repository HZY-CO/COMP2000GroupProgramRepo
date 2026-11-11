import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Timer;

public class App {
    private static final int GRID_BUFFER = 120;
    private static final int SIMULATION_FRAME_RATE = 8;
    private static final int CONTROL_PANEL_WIDTH = 300;

    private static int gridWidth = 16;
    private static int gridHeight = 16;
    private static final int MAX_GRID_WIDTH = 512;
    private static final int MAX_GRID_HEIGHT = 512;
    private static final int MIN_WINDOW_HEIGHT = 800;
    private static int cellSize = 32;
    private static final int MAX_CELL_SIZE = 512;

    private static Simulation simulation;
    private static Timer timer;
    private static int standaloneBurntTreeCount;

    public static void main(String[] args) {
        startSimulation();
    }

    private static void startSimulation() {
        simulation = new Simulation(
            gridWidth,
            gridHeight,
            cellSize,
            GRID_BUFFER,
            SIMULATION_FRAME_RATE
        );
        setupWindow();
    }

    private static void setupWindow() {
        int frameWidth = gridWidth * cellSize + GRID_BUFFER + CONTROL_PANEL_WIDTH;
        int frameHeight = Math.max(
            gridHeight * cellSize + GRID_BUFFER,
            MIN_WINDOW_HEIGHT
        );

        Frame mainFrame = createMainFrame(frameWidth, frameHeight);
        for (Panel treePanel : simulation.getTreePanels()) {
            mainFrame.add(treePanel);
        }
        for (int i = 0; i < simulation.getAnimals().size(); i++) {
            Panel animalPanel = simulation.getAnimalPanels().get(i);
            updateAnimalDisplay(simulation.getAnimals().get(i), animalPanel);
            mainFrame.add(animalPanel);
            mainFrame.setComponentZOrder(animalPanel, 0);
        }

        ControlPanel controlPanel = new ControlPanel(
            CONTROL_PANEL_WIDTH,
            frameHeight,
            gridWidth,
            gridHeight,
            cellSize,
            MAX_GRID_WIDTH,
            MAX_GRID_HEIGHT,
            MAX_CELL_SIZE,
            simulation,
            panel -> restartSimulation(mainFrame, panel)
        );
        controlPanel.setLocation(frameWidth - CONTROL_PANEL_WIDTH, 0);
        mainFrame.add(controlPanel);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        mainFrame.setVisible(true);

        startSimulationTimer(mainFrame, controlPanel);
    }

    private static Frame createMainFrame(int width, int height) {
        Frame mainFrame = new Frame("Forest Fire Simulation");
        mainFrame.setSize(width, height);
        mainFrame.setLayout(null);
        mainFrame.setBackground(new Color(40, 40, 40));
        mainFrame.setResizable(false);
        return mainFrame;
    }

    private static void startSimulationTimer(Frame mainFrame, ControlPanel controlPanel) {
        int millisecondsPerFrame = 1000 / SIMULATION_FRAME_RATE;
        timer = new Timer(millisecondsPerFrame, event -> {
            simulation.tick();
            for (int i = 0; i < simulation.getAnimals().size(); i++) {
                Animal animal = simulation.getAnimals().get(i);
                Panel animalPanel = simulation.getAnimalPanels().get(i);
                if (animal.isActive() && animalPanel.getParent() == null) {
                    mainFrame.add(animalPanel);
                    mainFrame.setComponentZOrder(animalPanel, 0);
                }
                updateAnimalDisplay(animal, animalPanel);
            }
            updateLightningDisplay(mainFrame);
            controlPanel.updateAnalytics(simulation);
        });
        timer.start();
    }

    
    private static void updateLightningDisplay(Frame mainFrame) {
        Panel[] lightningPanels = {
            simulation.getLightningPanel(),
            simulation.getLightningBoltPanel()
        };
        for (Panel lightningPanel : lightningPanels) {
            if (lightningPanel.getParent() == null) {
                mainFrame.add(lightningPanel);
            }
            if (simulation.isLightningVisible()) {
                mainFrame.setComponentZOrder(lightningPanel, 0);
            }
            lightningPanel.setVisible(simulation.isLightningVisible());
        }
    }

    private static void updateAnimalDisplay(Animal animal, Panel animalPanel) {
        if (!animal.isActive()) {
            Container parent = animalPanel.getParent();
            if (parent != null) {
                parent.remove(animalPanel);
                parent.revalidate();
                parent.repaint();
            }
            return;
        }

        int x = GRID_BUFFER / 2 + animal.getPosition().x * cellSize;
        int y = GRID_BUFFER / 2 + animal.getPosition().y * cellSize;
        int offset = (cellSize - animalPanel.getWidth()) / 2;
        animalPanel.setLocation(x + offset, y + offset);
    }

    private static void restartSimulation(Frame mainFrame, ControlPanel controlPanel) {
        gridWidth = controlPanel.getGridWidth();
        gridHeight = controlPanel.getGridHeight();
        cellSize = controlPanel.getCellSize();
        mainFrame.dispose();
        cleanUp();
        startSimulation();
    }

    private static void cleanUp() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        simulation = null;
    }

    public static void addFire(Position position) {
        if (simulation == null) {
            throw new IllegalStateException("Cannot add a fire before the simulation starts");
        }
        simulation.addFire(position);
    }

    public static void addBurntCounter() {
        if (simulation == null) {
            standaloneBurntTreeCount++;
        } else {
            simulation.recordBurntTree();
        }
    }

    static int getBurntTreeCount() {
        return simulation == null
            ? standaloneBurntTreeCount
            : simulation.getTreesBurnt();
    }
}
