
// import java.awt.Color;
import java.util.List;

public class Lightning extends Entity {

    private int duration;
    private double strikeChance; // (not every tick will have a strike)
    private double igniteChance; 
    private boolean hasStruck;
    private boolean active = true;
    private Forest forest;

    private int cooldownTicks = 0;
    private int cooldownDuration;
    private int totalStrikes = 0;
    private Position lastStrike; 

    public Lightning(Position position, int duration, double strikeChance, double igniteChance, int cooldownDuration,
            Forest forest) {
        super(position);

        if (strikeChance < 0.0 || strikeChance > 1.0) {
            throw new IllegalArgumentException("strikeChance must be between 0.0 & 1.0");
        }
        if (igniteChance < 0.0 || igniteChance > 1.0) {
            throw new IllegalArgumentException("ignite Chance must be between 0.0 & 1.0");
        }
        if (cooldownDuration < 0) {
            throw new IllegalArgumentException("cooldowDuration can't be negative");
        }
        this.duration = duration;
        this.strikeChance = strikeChance;
        this.igniteChance = igniteChance;
        this.cooldownDuration = cooldownDuration;
        this.hasStruck = false;
        this.forest = forest;
    }

    public void tick() {

        if (cooldownTicks > 0) {
            cooldownTicks--;
            hasStruck = false;
        } else {
            hasStruck = Math.random() < strikeChance;

            if (hasStruck) {
                totalStrikes++;
                cooldownTicks = cooldownDuration;

               
                lastStrike = new Position(
                        (int) (Math.random() * forest.getWidth()),
                        (int) (Math.random() * forest.getHeight()));

                List<Tree> trees = forest.getAllTrees();
                for (Tree tree : trees) {
                    strikeTree(tree);
                }
            }
        }
        duration--;
        if (duration <= 0) {
            active = false;
        }
    }

    private void strikeTree(Tree tree) {
        Position treePos = tree.getPosition();
        if (treePos.x == lastStrike.x && treePos.y == lastStrike.y) {
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

    public int getTotalStrikes() {
        return totalStrikes;
    }

    public boolean hasStruck() {
        return hasStruck;
    }

    public Position getLastStrike() {
        return lastStrike;
    }

    
    public void setStrikeChance(double strikeChance) {
        if (strikeChance < 0.0 || strikeChance > 1.0) {
            throw new IllegalArgumentException("strikeChance must be between 0.0 & 1.0");
        }
        this.strikeChance = strikeChance;
    }

    public void setIgniteChance(double igniteChance) {
        if (igniteChance < 0.0 || igniteChance > 1.0) {
            throw new IllegalArgumentException("ignite Chance must be between 0.0 & 1.0");
        }
        this.igniteChance = igniteChance;
    }

}
