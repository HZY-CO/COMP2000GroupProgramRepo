public class ForestManager {
    private final Forest forest;
    private final Wind wind;

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
        Cell cell = forest.getCellfromPosition(tree.getPosition());
        if (cell != null) {
            cell.setTree(tree);
        }
    }
}
