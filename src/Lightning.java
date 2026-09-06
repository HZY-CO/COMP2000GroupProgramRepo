/**
 * Lightning strikes at a fixed position (x, y) 
 * strikeChance determines whether a strike occurs
 * igniteChance determines whether a strike starts a fire
 * duration is how long the strike lasts
 */

import java.util.List;

public class Lightniing extends Entity {
    
    int x;
    int y;
    double strikeChance;
    double igniteChance;
    boolean hasStruck;

    public Lightning(int x, int y, double strikeChance, double igniteChance) {

        super(x, y);
        this.duration = duration;
        this.strikeChance = strikeChance;
        this.igniteChance = igniteChance;
        this.hasStruck = false;
    }


    public void update(List<Tree> trees) {
        this.hasStruck = Math.random() < this.strikeChance;

        if (this.hasStruck) {
            for (int i = 0; i < trees.size(); i++) {
                Tree tree = trees.get(i);
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