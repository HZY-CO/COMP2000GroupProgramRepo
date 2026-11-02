import java.util.ArrayList;
import java.util.List;

public class ForestManager {
    private final Forest forest;
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
                replaceBurntTree(tree);
            }
        }
    }

    private void replaceBurntTree(Tree burntTree) {
        List<Cell> availableCells = new ArrayList<>(forest.getEmptyCells());
        Cell burntCell = forest.getCellfromPosition(burntTree.getPosition());
        if (burntCell.getTree() == burntTree) {
            availableCells.add(burntCell);
        }
        if (availableCells.isEmpty()) {
            return;
        }

        Cell destination = availableCells.get((int) (Math.random() * availableCells.size()));
        burntCell.setTree(null);
        destination.setTree(burntTree.createReplacement(destination.getPosition()));
    }

    public void addFire(Fire fire) {
        forest.getCellfromPosition(fire.getPosition());
    }
}