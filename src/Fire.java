import java.util.List;

public class Fire extends Entity {
    private int duration;
    private int intensity;
    private Forest forest; // reference to the forest to access trees and other entities such as Trees, Lightning and Wind

    public Fire(int x, int y, int duration, int intensity, Forest forest) {
        super(x, y); // call the constructor of the superclass Entity to set the position of the fire
        this.duration = duration;
        this.intensity = intensity;
        this.forest = forest;
    }

    public void update(Tree tree, Wind wind){
        spread();

        duration--;
        if (duration <= 0){
            setActive(false);
        }
 
    }

    public void spread(){
        List<Tree> treeList = forest.getTrees().getAll(); // get all the trees in the forest

        for(int i = 0; i < treeList.size(); i++){

            Tree tree = treeList.get(i);
            if (tree.getState() == Tree.TreeState.BURNING || tree.getState() == Tree.TreeState.BURNT) { 
                continue; // skip trees already burning or already burnt
            }

            if (!isAdjacent(tree)){
                continue; //skip trees that are not adjacent to the fire
            }

            double chance = spreadChance(tree); // calculate the chance of the fire spreading to this tree bassed on the fire's intensity and the wind's influence 
            if (Math.random() < chance){
                tree.setState(Tree.TreeState.BURNING);
            }

        }


    }

    private boolean isAdjacent(Tree tree){
        int dx = Math.abs(tree.getX() - this.getX()); 
        int dy = Math.abs(tree.getY() - this.getY()); 
        return dx <= 1 && dy <= 1; 
    }

    private double spreadChance(Tree tree){
        double base = intensity/100; 

        double windBoost = 0;
        List<Wind> windList = forest.getWinds.getAll(); // get all the wind entitities in the forest
        for(int i = 0; i < windList.size(); i++){
            windBoost += windInfluence(windList.get(i), tree); 
        }

        return Math.min(base + windBoost, 1.0); // ensures that the chance does not exceed 100%


    }

    private double WindInfluence(Wind wind, Tree tree){
        int dx = tree.getX() - this.getX();
        int dy = tree.getY() - this.getY();

        if(dx == 0 && dy == 0){
            return 0; //same position, no influence
        }

        double length = Math.sqrt(dx*dx + dy*dy);
        double dirX = dx/length;
        double dirY = dy/length;

        double windX = wind.getX();
        double windY = wind.getY();
        double windLength = Math.sqrt(windX*windX + windY*windY); 

        if (windLength == 0){
            return 0 // no wind, no influence
        }

        windX /= windLength; 
        windY /= windLength;

        double allignment = (dirX * windX) + (dirY * windY); 
        double normalisedPower = wind.windPower / 100.0;

        return Math.max(allignment, 0) * normalisedPower * 0.3; // the 0.3 factor is a scaling factor to reduce the influence of wind on fire spread

    }

    public int fireIntensity(){
        return intensity; // returns the instensity of the fire, which can be used to determine how quickly it spreads or how much damage it does to trees
    }
}