import java.awt.Color;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collector;

public class Simulation {
    private static final int MAX_ANIMAL_COUNT = 10;
    private static final double FOREST_DENSITY = 0.7;
    private static final int TREE_MAX_AGE = 50;
    private static final double CHANCE_OF_LIGHTNING = 0.2;
    private static final double CHANCE_OF_IGNITION = 0.6;
    private static final int MIN_WEATHER_CYCLE_TIME = 4;
    private static final int MAX_WEATHER_CYCLE_TIME = 10;
    private static final Collector<Animal, EnumMap<AnimalStatus, Integer>, EnumMap<AnimalStatus, Integer>>
        ANIMAL_STATUS_COLLECTOR = Collector.of(
            () -> {
                EnumMap<AnimalStatus, Integer> counts = new EnumMap<>(AnimalStatus.class);
                counts.put(AnimalStatus.ALIVE, 0);
                counts.put(AnimalStatus.DEAD, 0);
                return counts;
            },
            (counts, animal) -> {
                AnimalStatus status = animal.isActive() ? AnimalStatus.ALIVE : AnimalStatus.DEAD;
                counts.put(status, counts.get(status) + 1);
            },
            (left, right) -> {
                for (AnimalStatus status : AnimalStatus.values()) {
                    left.put(status, left.get(status) + right.get(status));
                }
                return left;
            }
        );
    private static final Collector<Tree, EnumMap<Tree.TreeState, Integer>, EnumMap<Tree.TreeState, Integer>>
        TREE_STATE_COLLECTOR = Collector.of(
            () -> {
                EnumMap<Tree.TreeState, Integer> counts = new EnumMap<>(Tree.TreeState.class);
                for (Tree.TreeState state : Tree.TreeState.values()) {
                    counts.put(state, 0);
                }
                return counts;
            },
            (counts, tree) -> {
                Tree.TreeState state = tree.getState();
                counts.put(state, counts.get(state) + 1);
            },
            (left, right) -> {
                for (Tree.TreeState state : Tree.TreeState.values()) {
                    left.put(state, left.get(state) + right.get(state));
                }
                return left;
            }
        );

    private final int gridWidth;
    private final int gridHeight;
    private final int cellSize;
    private final int gridBuffer;
    private final int simulationFrameRate;
    private final Forest forest;
    private final ForestManager forestManager;
    private final List<Animal> animals = new ArrayList<>();
    private final List<Panel> animalPanels = new ArrayList<>();
    private final List<Panel> treePanels = new ArrayList<>();
    private final List<PendingTreeSpawn> pendingTreeSpawns = new ArrayList<>();
    private final Weather weather = new Weather(Weather.WeatherState.CLEAR_SKIES);

    private int weatherTimer;
    private int weatherCycleTime;
    private int treeAmount;
    private int treesBurning;
    private int treesBurnt;
    private int animalsAlive;
    private int animalsDead;
    private long ticksPassed;

    public Simulation(
            int gridWidth,
            int gridHeight,
            int cellSize,
            int gridBuffer,
            int simulationFrameRate) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellSize = cellSize;
        this.gridBuffer = gridBuffer;
        this.simulationFrameRate = simulationFrameRate;

        forest = new Forest(gridWidth, gridHeight);
        forestManager = new ForestManager(forest, new Wind(2, 5, 10));
        createAnimals();
        createTreeSpawns();
        updateAnalytics();
    }

    public void tick() {
        ticksPassed++;
        forestManager.getWind().update();
        updateTrees();
        for (Animal animal : animals) {
            animal.tick();
        }
        replenishAnimals();
        updateAnalytics();
        summonLightning();
        cycleWeather();
    }

    private void createAnimals() {
        for (int i = 0; i < MAX_ANIMAL_COUNT; i++) {
            addAnimal(new Position(
                (int) (Math.random() * gridWidth),
                (int) (Math.random() * gridHeight)
            ));
        }
    }

    void replenishAnimals() {
        if (hasActiveFireOrBurningTree()) {
            return;
        }

        long livingAnimals = animals.stream().filter(Animal::isActive).count();
        while (livingAnimals < MAX_ANIMAL_COUNT) {
            addAnimal(randomEdgePosition());
            livingAnimals++;
        }
    }

    private boolean hasActiveFireOrBurningTree() {
        boolean activeFire = forest.getAllFires().stream().anyMatch(Fire::isActive);
        boolean burningTree = forest.getAllTrees().stream().anyMatch(Tree::isBurning);
        return activeFire || burningTree;
    }

    private Position randomEdgePosition() {
        int side = (int) (Math.random() * 4);
        switch (side) {
            case 0:
                return new Position(0, (int) (Math.random() * gridHeight));
            case 1:
                return new Position(gridWidth - 1, (int) (Math.random() * gridHeight));
            case 2:
                return new Position((int) (Math.random() * gridWidth), 0);
            default:
                return new Position((int) (Math.random() * gridWidth), gridHeight - 1);
        }
    }

    private void addAnimal(Position position) {
        animals.add(new Animal(position, forest));

        Panel animalPanel = new Panel();
        int animalSize = Math.max(1, cellSize / 2);
        animalPanel.setSize(animalSize, animalSize);
        animalPanel.setBackground(new Color(255, 215, 0));
        animalPanels.add(animalPanel);
    }

    private void createTreeSpawns() {
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (Math.random() < FOREST_DENSITY) {
                    Position position = new Position(x, y);
                    Panel panel = new Panel();
                    panel.setSize(cellSize, cellSize);
                    panel.setBackground(new Color(40, 40, 40));
                    panel.setLocation(
                        gridBuffer / 2 + x * cellSize,
                        gridBuffer / 2 + y * cellSize
                    );

                    long spawnTick = 1 + (int) (Math.random() * TREE_MAX_AGE);
                    pendingTreeSpawns.add(new PendingTreeSpawn(position, panel, spawnTick));
                    treePanels.add(panel);
                }
            }
        }
    }

    private void updateTrees() {
        forestManager.tickTrees();
        spawnDueTrees();
        for (Tree tree : forest.getAllTrees()) {
            tree.setDisplayLocation(
                gridBuffer / 2 + tree.getPosition().x * cellSize,
                gridBuffer / 2 + tree.getPosition().y * cellSize
            );
            tree.draw();
        }
        forest.tickFires();
    }

    private void spawnDueTrees() {
        for (PendingTreeSpawn pendingSpawn : new ArrayList<>(pendingTreeSpawns)) {
            if (pendingSpawn.spawnTick <= ticksPassed) {
                spawnTree(pendingSpawn);
                pendingTreeSpawns.remove(pendingSpawn);
            }
        }
    }

    private void spawnTree(PendingTreeSpawn pendingSpawn) {
        Tree.TreeState initialState;
        if (Math.random() < 0.01) {
            initialState = Tree.TreeState.BURNING;
            int fireDuration = (int) (Math.random() * 100);
            int fireIntensity = (int) (Math.random() * 100);
            forest.indexFire(new Fire(
                pendingSpawn.position,
                fireDuration,
                fireIntensity,
                forestManager
            ));
        } else if (1 + (int) (Math.random() * TREE_MAX_AGE) == TREE_MAX_AGE) {
            initialState = Tree.TreeState.FULLY_GROWN;
        } else {
            initialState = Tree.TreeState.GROWING;
        }

        Tree tree = new Tree(
            pendingSpawn.position,
            TREE_MAX_AGE,
            initialState,
            pendingSpawn.panel
        );
        forestManager.addTree(tree);
        tree.draw();
    }

    void updateAnalytics() {
        EnumMap<Tree.TreeState, Integer> treeCounts = forest.getAllTrees().parallelStream()
            .collect(TREE_STATE_COLLECTOR);
        treeAmount = 0;
        for (int count : treeCounts.values()) {
            treeAmount += count;
        }
        treesBurning = treeCounts.get(Tree.TreeState.BURNING);

        EnumMap<AnimalStatus, Integer> animalCounts = animals.parallelStream()
            .collect(ANIMAL_STATUS_COLLECTOR);
        animalsAlive = animalCounts.get(AnimalStatus.ALIVE);
        animalsDead = animalCounts.get(AnimalStatus.DEAD);
    }

    private void summonLightning() {
        Position lightningPosition = new Position(
            (int) (Math.random() * (gridWidth + 1)),
            (int) (Math.random() * (gridHeight + 1))
        );
        double strikeChance = clampProbability(
            CHANCE_OF_LIGHTNING * weather.ReturnLightningMultiplier()
        );
        double ignitionChance = clampProbability(
            CHANCE_OF_IGNITION * weather.ReturnIgnitionMultiplier()
        );
        new Lightning(
            lightningPosition,
            1,
            strikeChance,
            ignitionChance,
            10,
            forest
        ).tick();
    }

    private void cycleWeather() {
        weatherTimer++;
        if (weatherTimer >= simulationFrameRate * weatherCycleTime) {
            weatherTimer = 0;
            weatherCycleTime = MIN_WEATHER_CYCLE_TIME
                + (int) (Math.random() * (MAX_WEATHER_CYCLE_TIME - MIN_WEATHER_CYCLE_TIME + 1));
            weather.CycleWeather();
        }
    }

    static double clampProbability(double probability) {
        return Math.max(0.0, Math.min(probability, 1.0));
    }

    public void addFire(Position position) {
        int duration = (int) (Math.random() * 100);
        int intensity = (int) (Math.random() * 100);
        forest.indexFire(new Fire(position, duration, intensity, forestManager));
    }

    public void recordBurntTree() {
        treesBurnt++;
    }

    public Forest getForest() {
        return forest;
    }

    public Wind getWind() {
        return forestManager.getWind();
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Panel> getAnimalPanels() {
        return animalPanels;
    }

    public List<Panel> getTreePanels() {
        return treePanels;
    }

    public int getTreeAmount() {
        return treeAmount;
    }

    public int getTreesBurning() {
        return treesBurning;
    }

    public int getTreesBurnt() {
        return treesBurnt;
    }

    public int getAnimalsAlive() {
        return animalsAlive;
    }

    public int getAnimalsDead() {
        return animalsDead;
    }

    public long getTicksPassed() {
        return ticksPassed;
    }

    public String getWeatherName() {
        return weather.ReturnWeatherName();
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

    private enum AnimalStatus {
        ALIVE,
        DEAD
    }
}
