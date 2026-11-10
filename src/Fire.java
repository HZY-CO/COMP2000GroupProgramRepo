import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Fire extends Entity implements Tickable {
    private int duration;
    private int intensity; // Out of 100
    private boolean active = true;
    private ForestManager forestManager;
    private Spread strategy;   // the swappable spread rule

    // Keeps App and Tree compiling: defaults to the wind rule
    public Fire(Position position, int duration, int intensity, ForestManager forestManager) {
        this(position, duration, intensity, forestManager, new WindSpread());
    }

    public Fire(Position position, int duration, int intensity, ForestManager forestManager, Spread strategy) {
        super(position);
        this.duration = duration;
        this.intensity = intensity;
        this.forestManager = forestManager;
        this.strategy = strategy;
    }

    @Override
    public void tick(){
        if (!active) return;   // a burnt-out fire does nothing

        spread();

        duration--;
        if (duration <= 0){
            active = false;
        }
    }

    @Override
    public boolean isActive(){
        return active;
    }

    @Override
    public void setActive(boolean active){
        this.active = active;
    }

    // Collector 1: toList() gathers neighbouring trees that could still catch fire
    private List<Tree> getFlammableNeighbours() {
        return forestManager.getForest().getNeighbourCells(position).stream()
            .map(Cell::getTree)                // Cell -> Tree (may be null)
            .filter(Objects::nonNull)          // no tree in this cell
            .filter(tree -> !tree.isBurning()) // already on fire
            .collect(Collectors.toList());
    }

    // Collector 2: summingDouble() adds up each tree's spread chance
    public double expectedSpread() {
        Wind wind = forestManager.getWind();
        return getFlammableNeighbours().stream()
            .collect(Collectors.summingDouble(tree -> strategy.chance(this, tree, wind)));
    }

    public void spread(){
        Wind wind = forestManager.getWind();

        getFlammableNeighbours().stream()
            .filter(tree -> Math.random() < strategy.chance(this, tree, wind))  // roll the dice for each tree
            .forEach(tree -> {
                try {
                    tree.ignite();
                } catch (TreeStateException ex) {
                    System.out.println("Failed to ignite tree at " + tree.getPosition() + ": " + ex.getMessage());
                }
            });
    }

    public int fireIntensity(){
        return intensity;   // must stay public: the strategies call it
    }
}