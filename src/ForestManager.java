import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class ForestManager {
    private static final int MAX_REPLACEMENT_DELAY = 50;

    private final Forest forest;
    private final Map<Tree, Integer> replacementDelays = new IdentityHashMap<>();
    private Wind wind;

    public ForestManager(Forest forest, Wind wind) {
        this.forest = forest;
        this.wind = wind;
    }

    public Forest getForest() {
        return forest;
    }

    public Wind getWind() {
        return wind;
    }

    public Wind setWind(Wind wind) {
        this.wind = wind;
        return this.wind;
    }
    
    public void addTree(Tree tree) {
        forest.getCellfromPosition(tree.getPosition()).setTree(tree);
    }

    public void tickTrees() {
        boolean firesOut = forest.getAllFires().stream().noneMatch(Fire::isActive);
        for (Tree tree : forest.getAllTrees()) {
            tree.tick(firesOut);
            if (tree.isReadyForReplacement()) {
                Integer delay = replacementDelays.get(tree);
                if (delay == null) {
                    replacementDelays.put(
                        tree,
                        1 + (int) (Math.random() * MAX_REPLACEMENT_DELAY)
                    );
                } else if (delay == 1) {
                    if (replaceBurntTree(tree)) {
                        replacementDelays.remove(tree);
                    }
                } else {
                    replacementDelays.put(tree, delay - 1);
                }
            }
        }
    }

    private boolean replaceBurntTree(Tree burntTree) {
        List<Cell> availableCells = new ArrayList<>(forest.getEmptyCells());
        Cell burntCell = forest.getCellfromPosition(burntTree.getPosition());
        if (burntCell.getTree() == burntTree) {
            availableCells.add(burntCell);
        }
        if (availableCells.isEmpty()) {
            return false;
        }

        Cell destination = availableCells.get((int) (Math.random() * availableCells.size()));
        burntCell.setTree(null);
        
        Species newSpecies = destination.getBiome().pickSpecies();
        destination.setTree(burntTree.createReplacement(destination.getPosition(), newSpecies));
        return true;
    }

    public void addFire(Fire fire) {
        forest.getCellfromPosition(fire.getPosition());
    }
}