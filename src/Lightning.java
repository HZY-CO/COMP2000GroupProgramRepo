import java.util.List;

public class Lightning extends Entity {
    
    private int duration; 
    private double strikeChance; // Determines whether a strike occurs(not every tick will have a strike)
    private double igniteChance; // Determines whether a strike sets a tree on fire
    private boolean hasStruck;
    private boolean active = true;
    private Forest forest;
    

    public Lightning(Position position, int duration, double strikeChance, double igniteChance, Forest forest) {
        super(position);
        this.duration = duration;
        this.strikeChance = strikeChance;
        this.igniteChance = igniteChance;
        this.hasStruck = false;
        this.forest = forest;
    }

    public void tick() {
        hasStruck = Math.random() < strikeChance;

        if (hasStruck) {
            List<Tree> trees = forest.getAllTrees();
            for (Tree tree : trees) {
                strikeTree(tree);
            }
        }
        
        duration = duration - duration;
        if (duration <=0) {
            active = false;
        }
    }
    

    private void strikeTree(Tree tree) {
        Position treePos = tree.getPosition(); 
        if (treePos.x == position.x && treePos.y == position.y) {
            if (Math.random() < igniteChance) {
                tree.ignite();
            }
        }
    }
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int lightningIntensity() {
        return duration;
    }

}
