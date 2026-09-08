import java.util.ArrayList;

public class ForestManager {
    private final Forest forest;
    private final Wind wind;

    private final ArrayList<Tree> trees = new ArrayList<>();

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
    
    public void addTree(Tree tree) {
        trees.add(tree);
        forest.getCellfromPosition(tree.getPosition()).setTree(tree);
    }
}