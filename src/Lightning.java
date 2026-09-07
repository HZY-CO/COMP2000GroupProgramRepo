import java.util.List;

public class Lightning extends Entity {
    
    int x;
    int y;
    int duration;
    double strikeChance; // Determines whether a strike occurs(not every tick will have a strike)
    double igniteChance; // Determines whether a strike sets a tree on fire
    boolean hasStruck;
    

    public Lightning(int x, int y, double strikeChance, double igniteChance) {

        super(x, y);
        this.duration = duration;
        this.strikeChance = strikeChance;
        this.igniteChance = igniteChance;
        this.hasStruck = false;
    }


    public void update(List<Tree> treeList) {
        this.hasStruck = Math.random() < this.strikeChance;

        if (this.hasStruck) {
            for (Tree tree : treeList) {
                strikeTree(tree);
            }
        }
    }

    

    private void strikeTree(Tree tree) {

        if (tree.x == this.x && tree.y == this.y) {
            if (Math.random() < this.igniteChance) {
                tree.isBurning = true;
        }
    }
  }

  int lightningIntensity() {
    return duration;
  }

  
} 