/*
Resources
https://docs.oracle.com/en/java/javase/26/docs/api/java.desktop/java/awt/Component.html
https://docs.oracle.com/en/java/javase/26/docs/api/java.desktop/java/awt/package-summary.html
*/

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.Timer;

public class App {
    private static final int GRID_BUFFER = 120;

    // Grid dimensions and cell sizing
    private static int gridWidth = 16;
    private static int gridHeight = 16;
    private static int maxGridWidth = 512;
    private static int maxGridHeight = 512;
    private static int minSizeY = 800;
    private static int cellSize = 32;
    private static int maxCellSize = 512;

    // Simulation settings
    private static int simulationFrameRate = 8;

    // Forest generation
    private static Forest forest;
    private static ForestManager forestManager;
    private static double forestDensity = 0.7;
    private static int treeMaxAge = 50;

    // Lightning settings
    private static double chanceOfLightning = 0.2;
    private static double chanceOfIgnition = 0.6;

    // Weather
    private static Weather weather = new Weather(Weather.WeatherState.CLEAR_SKIES);
    private static int weatherTimer;
    private static int weatherCycleTime;            // time it takes to cycle the weather in seconds.
    private static int minWeatherCycleTime = 4;     // Min time it takes to cycle the weather in seconds.
    private static int maxWeatherCycleTime = 10;    // Max time it takes to cycle the weather in seconds.

    // Analytics
    private static int treeAmount;
    private static int treesBurning;
    private static int treesBurnt;
    private static long ticksPassed;
    private static final List<PendingTreeSpawn> pendingTreeSpawns = new ArrayList<>();

    // Analytics labels
    private static Label treeAmountText;
    private static Label treesOnFireText;
    private static Label burntTreesText;
    private static Label weatherNameText;
    private static Label ticksPassedText;

    // Simulation timer
    private static Timer timer;

    public static void main(String[] Args) throws Exception {
        startSimulation();
    }

    private static void startSimulation() {
        List<Panel> treePanels = generateForest();
        setupWindow(treePanels);
    }

    private static void setupWindow(List<Panel> treePanels) {
        int controlPanelWidth = 300;
        int mainFrameSizeX = gridWidth * cellSize + GRID_BUFFER + controlPanelWidth;
        int mainFrameSizeY = gridHeight * cellSize + GRID_BUFFER;
        if (mainFrameSizeY < minSizeY) {
            mainFrameSizeY = minSizeY;
        }

        Frame mainFrame = createMainFrame(mainFrameSizeX, mainFrameSizeY);
        for (Panel treePanel : treePanels) {
            mainFrame.add(treePanel);
        }

        Panel controlPanel = createControlPanel(mainFrame, mainFrameSizeX, mainFrameSizeY, controlPanelWidth);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        startSimulationTimer(forestManager.getWind());
    }

    private static Frame createMainFrame(int mainFrameSizeX, int mainFrameSizeY) {
        Frame mainFrame = new Frame("Forest Fire Simulation");
        mainFrame.setSize(mainFrameSizeX, mainFrameSizeY);
        mainFrame.setLayout(null);
        mainFrame.setBackground(new Color(40, 40, 40));
        mainFrame.setResizable(false);
        return mainFrame;
    }

    private static Panel createControlPanel(
            Frame mainFrame, int mainFrameSizeX, int mainFrameSizeY, int controlPanelWidth) {
        Panel controlPanel = new Panel();
        controlPanel.setLayout(null);
        controlPanel.setSize(controlPanelWidth, mainFrameSizeY);
        controlPanel.setBackground(new Color(44, 44, 44));
        controlPanel.setLocation(mainFrameSizeX - controlPanelWidth, 0);
        controlPanel.add(createWidthControl(controlPanelWidth));
        controlPanel.add(createHeightControl(controlPanelWidth));
        controlPanel.add(createCellSizeControl(controlPanelWidth));
        controlPanel.add(createRefreshButton(mainFrame, controlPanelWidth));
        controlPanel.add(createAnalyticsPanel(controlPanelWidth));
        return controlPanel;
    }

    private static Panel createWidthControl(int controlPanelWidth) {
        Panel widthAttribute = new Panel();
        widthAttribute.setLayout(null);
        widthAttribute.setSize(controlPanelWidth, 100);
        widthAttribute.setBackground(new Color(60, 60, 60));
        widthAttribute.setLocation(0, 80);

        Label widthAttributeText = new Label("Columns", Label.LEFT);
        widthAttributeText.setBounds(20, 0, controlPanelWidth, 100);
        widthAttributeText.setFont(new Font("SansSerif", Font.BOLD, 24));
        widthAttributeText.setForeground(new Color(255, 255, 255));

        Label widthAttributeValue = new Label(Integer.toString(gridWidth), Label.CENTER);
        widthAttributeValue.setBounds(controlPanelWidth - 130, 30, 80, 40);
        widthAttributeValue.setFont(new Font("SansSerif", Font.BOLD, 32));
        widthAttributeValue.setForeground(new Color(255, 255, 255));

        Button widthDecreaseBtn = new Button("<");
        widthDecreaseBtn.setBounds(controlPanelWidth - 160, 30, 40, 40);
        widthDecreaseBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        widthDecreaseBtn.setForeground(new Color(255, 255, 255));
        widthDecreaseBtn.setBackground(new Color(40, 40, 40));
        widthDecreaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gridWidth -= 1;
                if (gridWidth <= 0) {
                    gridWidth = 1;
                }
                widthAttributeValue.setText(Integer.toString(gridWidth));
            }
        });

        Button widthIncreaseBtn = new Button(">");
        widthIncreaseBtn.setBounds(controlPanelWidth - 60, 30, 40, 40);
        widthIncreaseBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        widthIncreaseBtn.setForeground(new Color(255, 255, 255));
        widthIncreaseBtn.setBackground(new Color(40, 40, 40));
        widthIncreaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gridWidth += 1;
                if (gridWidth > maxGridWidth) {
                    gridWidth = maxGridWidth;
                }
                widthAttributeValue.setText(Integer.toString(gridWidth));
            }
        });

        widthAttribute.add(widthDecreaseBtn);
        widthAttribute.add(widthIncreaseBtn);
        widthAttribute.add(widthAttributeValue);
        widthAttribute.add(widthAttributeText);
        return widthAttribute;
    }

    private static Panel createHeightControl(int controlPanelWidth) {
        Panel heightAttribute = new Panel();
        heightAttribute.setLayout(null);
        heightAttribute.setSize(controlPanelWidth, 100);
        heightAttribute.setBackground(new Color(60, 60, 60));
        heightAttribute.setLocation(0, 200);

        Label heightAttributeText = new Label("Rows", Label.LEFT);
        heightAttributeText.setBounds(20, 0, controlPanelWidth, 100);
        heightAttributeText.setFont(new Font("SansSerif", Font.BOLD, 24));
        heightAttributeText.setForeground(new Color(255, 255, 255));

        Label heightAttributeValue = new Label(Integer.toString(gridHeight), Label.CENTER);
        heightAttributeValue.setBounds(controlPanelWidth - 130, 30, 80, 40);
        heightAttributeValue.setFont(new Font("SansSerif", Font.BOLD, 32));
        heightAttributeValue.setForeground(new Color(255, 255, 255));

        Button heightDecreaseBtn = new Button("<");
        heightDecreaseBtn.setBounds(controlPanelWidth - 160, 30, 40, 40);
        heightDecreaseBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        heightDecreaseBtn.setForeground(new Color(255, 255, 255));
        heightDecreaseBtn.setBackground(new Color(40, 40, 40));
        heightDecreaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gridHeight -= 1;
                if (gridHeight <= 0) {
                    gridHeight = 1;
                }
                heightAttributeValue.setText(Integer.toString(gridHeight));
            }
        });

        Button heightIncreaseBtn = new Button(">");
        heightIncreaseBtn.setBounds(controlPanelWidth - 60, 30, 40, 40);
        heightIncreaseBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        heightIncreaseBtn.setForeground(new Color(255, 255, 255));
        heightIncreaseBtn.setBackground(new Color(40, 40, 40));
        heightIncreaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gridHeight += 1;
                if (gridHeight > maxGridHeight) {
                    gridHeight = maxGridHeight;
                }
                heightAttributeValue.setText(Integer.toString(gridHeight));
            }
        });

        heightAttribute.add(heightDecreaseBtn);
        heightAttribute.add(heightIncreaseBtn);
        heightAttribute.add(heightAttributeValue);
        heightAttribute.add(heightAttributeText);
        return heightAttribute;
    }

    private static Panel createCellSizeControl(int controlPanelWidth) {
        Panel cellSizeAttribute = new Panel();
        cellSizeAttribute.setLayout(null);
        cellSizeAttribute.setSize(controlPanelWidth, 100);
        cellSizeAttribute.setBackground(new Color(60, 60, 60));
        cellSizeAttribute.setLocation(0, 320);

        Label cellSizeAttributeText = new Label("Cell Size", Label.LEFT);
        cellSizeAttributeText.setBounds(20, 0, controlPanelWidth, 100);
        cellSizeAttributeText.setFont(new Font("SansSerif", Font.BOLD, 24));
        cellSizeAttributeText.setForeground(new Color(255, 255, 255));

        Label cellSizeAttributeValue = new Label(Integer.toString(cellSize), Label.CENTER);
        cellSizeAttributeValue.setBounds(controlPanelWidth - 130, 30, 80, 40);
        cellSizeAttributeValue.setFont(new Font("SansSerif", Font.BOLD, 32));
        cellSizeAttributeValue.setForeground(new Color(255, 255, 255));

        Button cellSizeDecreaseBtn = new Button("<");
        cellSizeDecreaseBtn.setBounds(controlPanelWidth - 160, 30, 40, 40);
        cellSizeDecreaseBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        cellSizeDecreaseBtn.setForeground(new Color(255, 255, 255));
        cellSizeDecreaseBtn.setBackground(new Color(40, 40, 40));
        cellSizeDecreaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cellSize -= 1;
                if (cellSize <= 0) {
                    cellSize = 1;
                }
                cellSizeAttributeValue.setText(Integer.toString(cellSize));
            }
        });

        Button cellSizeIncreaseBtn = new Button(">");
        cellSizeIncreaseBtn.setBounds(controlPanelWidth - 60, 30, 40, 40);
        cellSizeIncreaseBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        cellSizeIncreaseBtn.setForeground(new Color(255, 255, 255));
        cellSizeIncreaseBtn.setBackground(new Color(40, 40, 40));
        cellSizeIncreaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cellSize += 1;
                if (cellSize > maxCellSize) {
                    cellSize = maxCellSize;
                }
                cellSizeAttributeValue.setText(Integer.toString(cellSize));
            }
        });

        cellSizeAttribute.add(cellSizeDecreaseBtn);
        cellSizeAttribute.add(cellSizeIncreaseBtn);
        cellSizeAttribute.add(cellSizeAttributeValue);
        cellSizeAttribute.add(cellSizeAttributeText);
        return cellSizeAttribute;
    }

    private static Button createRefreshButton(Frame mainFrame, int controlPanelWidth) {
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setBounds(80, 440, controlPanelWidth - 160, 40);
        refreshBtn.setFont(new Font("SansSerif", Font.BOLD, 24));
        refreshBtn.setForeground(new Color(255, 255, 255));
        refreshBtn.setBackground(new Color(40, 40, 40));
        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                cleanUp();
                startSimulation();
            }
        });
        return refreshBtn;
    }

    private static Panel createAnalyticsPanel(int controlPanelWidth) {
        Panel analyticsPanel = new Panel();
        analyticsPanel.setLayout(null);
        analyticsPanel.setSize(controlPanelWidth, 300);
        analyticsPanel.setBackground(new Color(60, 60, 60));
        analyticsPanel.setLocation(0, 510);

        Label analyticsTitle = new Label("Analytics", Label.LEFT);
        analyticsTitle.setBounds(20, 4, controlPanelWidth, 28);
        analyticsTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        analyticsTitle.setForeground(new Color(255, 255, 255));

        treeAmountText = new Label("Amount of Trees: " + treeAmount, Label.LEFT);
        treeAmountText.setBounds(20, 34, controlPanelWidth, 22);
        treeAmountText.setFont(new Font("SansSerif", Font.BOLD, 18));
        treeAmountText.setForeground(new Color(255, 255, 255));

        treesOnFireText = new Label("Trees burning: " + treesBurning, Label.LEFT);
        treesOnFireText.setBounds(20, 56, controlPanelWidth, 22);
        treesOnFireText.setFont(new Font("SansSerif", Font.BOLD, 18));
        treesOnFireText.setForeground(new Color(255, 255, 255));

        burntTreesText = new Label("Total Trees Burnt: " + treesBurnt, Label.LEFT);
        burntTreesText.setBounds(20, 78, controlPanelWidth, 22);
        burntTreesText.setFont(new Font("SansSerif", Font.BOLD, 18));
        burntTreesText.setForeground(new Color(255, 255, 255));

        weatherNameText = new Label("Current Weather: " + weather.ReturnWeatherName(), Label.LEFT);
        weatherNameText.setBounds(20, 100, controlPanelWidth, 22);
        weatherNameText.setFont(new Font("SansSerif", Font.BOLD, 18));
        weatherNameText.setForeground(new Color(255, 255, 255));
      
        ticksPassedText = new Label("Ticks passed: " + ticksPassed, Label.LEFT);
        ticksPassedText.setBounds(20, 100, controlPanelWidth, 22);
        ticksPassedText.setFont(new Font("SansSerif", Font.BOLD, 18));
        ticksPassedText.setForeground(new Color(255, 255, 255));

        analyticsPanel.add(analyticsTitle);
        analyticsPanel.add(treeAmountText);
        analyticsPanel.add(treesOnFireText);
        analyticsPanel.add(burntTreesText);
        analyticsPanel.add(weatherNameText);
        analyticsPanel.add(ticksPassedText);
        return analyticsPanel;
    }

    private static void startSimulationTimer(Wind wind) {
        int millisecondsPerFrame = 1000 / simulationFrameRate;
        timer = new Timer(millisecondsPerFrame, e -> {
            ticksPassed++;
            wind.update();
            updateTrees();
            filterForBurning();
            updateAnalyticsLabels();
            summonLightning();
            cycleWeather();
        });
        timer.start();
    }

    private static void updateAnalyticsLabels() {
        treeAmount = forest.getAllTrees().size();
        treeAmountText.setText("Amount of Trees: " + treeAmount);
        treesOnFireText.setText("Trees burning: " + treesBurning);
        burntTreesText.setText("Total Trees Burnt: " + treesBurnt);
        weatherNameText.setText("Current Weather: " + weather.ReturnWeatherName());
        ticksPassedText.setText("Ticks passed: " + ticksPassed);
    }

    private static List<Panel> generateForest() {
        forest = new Forest(gridWidth, gridHeight);
        Wind wind = new Wind(2, 5, 10);
        forestManager = new ForestManager(forest, wind);
        return populateTrees();
    }

    private static List<Panel> populateTrees() {
        List<Panel> treePanels = new ArrayList<>();
        pendingTreeSpawns.clear();
        treeAmount = 0;

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (Math.random() < forestDensity) {
                    Position treePos = new Position(x, y);
                    Panel treePanel = new Panel();
                    treePanel.setSize(cellSize, cellSize);
                    treePanel.setBackground(new Color(40, 40, 40));

                    int treePanelX = GRID_BUFFER / 2 + (x * cellSize);
                    int treePanelY = GRID_BUFFER / 2 + (y * cellSize);
                    treePanel.setLocation(treePanelX, treePanelY);

                    long spawnTick = 1 + (int) (Math.random() * treeMaxAge);
                    pendingTreeSpawns.add(new PendingTreeSpawn(treePos, treePanel, spawnTick));
                    treePanels.add(treePanel);
                }
            }
        }
        return treePanels;
    }

    private static void spawnDueTrees() {
        for (PendingTreeSpawn pendingSpawn : new ArrayList<>(pendingTreeSpawns)) {
            if (pendingSpawn.spawnTick <= ticksPassed) {
                spawnTree(pendingSpawn);
                pendingTreeSpawns.remove(pendingSpawn);
            }
        }
    }

    private static void spawnTree(PendingTreeSpawn pendingSpawn) {
        Tree.TreeState initialState;
        if (Math.random() < 0.01) {
            initialState = Tree.TreeState.BURNING;
            int maxFireDuration = (int) (Math.random() * 100);
            int maxFireIntensity = (int) (Math.random() * 100);
            Fire fire = new Fire(
                pendingSpawn.position,
                maxFireDuration,
                maxFireIntensity,
                forestManager
            );
            forest.indexFire(fire);
        } else if (1 + (int) (Math.random() * treeMaxAge) == treeMaxAge) {
            initialState = Tree.TreeState.FULLY_GROWN;
        } else {
            initialState = Tree.TreeState.GROWING;
        }

        Tree tree = new Tree(pendingSpawn.position, treeMaxAge, initialState, pendingSpawn.panel);
        forestManager.addTree(tree);
        tree.draw();
    }

    private static void summonLightning() {
        int randomX = 0 + (int) (Math.random() * (gridWidth + 1));
        int randomY = 0 + (int) (Math.random() * (gridHeight + 1));
        Position lightningPos = new Position(randomX, randomY);
        Lightning lightning = new Lightning(lightningPos, 1, chanceOfLightning, chanceOfIgnition, 10, forest);
        lightning.tick();
    }

    private static void updateTrees() {
        forestManager.tickTrees();
        spawnDueTrees();
        for (Tree tree : forest.getAllTrees()) {
            tree.setDisplayLocation(
                GRID_BUFFER / 2 + tree.getPosition().x * cellSize,
                GRID_BUFFER / 2 + tree.getPosition().y * cellSize
            );
            tree.draw();
        }

        forest.tickFires();
    }

    public static void addFire(Position pos) {
        int maxFireDuration = (int) (Math.random() * 100);
        int maxFireIntensity = (int) (Math.random() * 100);
        Fire fire = new Fire(pos, maxFireDuration, maxFireIntensity, forestManager);
        forest.indexFire(fire);
    }

    private static void filterForBurning() {
        List<Tree> burningTreesList = forest.getAllTrees().stream()
                .filter(t -> t.getState() == Tree.TreeState.BURNING)
                .collect(Collectors.toList());
        treesBurning = burningTreesList.size();
    }

    private static void cycleWeather() {        // Cycles the weather after a random range of time in seconds.
        weatherTimer++;
        if(weatherTimer >= simulationFrameRate * weatherCycleTime)
        {
            weatherTimer = 0;
            weatherCycleTime = (int) (Math.random() * ((maxWeatherCycleTime - minWeatherCycleTime) + 1)) + minWeatherCycleTime;
            weather.CycleWeather();
        }
    }

    private static void cleanUp() {
        timer.stop();
        treeAmount = 0;
        treesBurning = 0;
        treesBurnt = 0;
        ticksPassed = 0;
        pendingTreeSpawns.clear();
    }

    public static void addBurntCounter() {
        treesBurnt += 1;
    }

    static int getBurntTreeCount() {
        return treesBurnt;
    }

    private static class PendingTreeSpawn {
        private final Position position;
        private final Panel panel;
        private final long spawnTick;

        private PendingTreeSpawn(Position position, Panel panel, long spawnTick) {
            this.position = position;
            this.panel = panel;
            this.spawnTick = spawnTick;
        }
    }
}
