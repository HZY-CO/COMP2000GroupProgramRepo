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
        forest.getCellfromPosition(tree.getPosition()).setTree(tree);
    }
    public void addFire(Fire fire) {
        forest.getCellfromPosition(fire.getPosition());
    }
}