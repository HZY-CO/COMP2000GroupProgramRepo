import java.util.List;

public class Fire extends Entity implements Tickable {
    private int duration;
    private int intensity; // Out of 100
    private boolean active = true;
    private ForestManager forestManager; // reference to the forest to access trees and other entities such as Trees, Lightning and Wind

    public Fire(Position position, int duration, int intensity, ForestManager forestManager) {
        super(position); // call the constructor of the superclass Entity to set the position of the fire
        this.duration = duration;
        this.intensity = intensity;
        this.forestManager = forestManager;
    }

    @Override
    public void tick(){
        spread();

        duration--;
        if (duration <= 0){
            active = false;
        }
 
    }

    @Override
    public boolean isActive(){
        return active;
    }

    @Override
    public void setActive(boolean active){
        this.active = active;
    }

    public void spread(){
        Forest forest = forestManager.getForest();
        Wind wind = forestManager.getWind();

        List<Cell> neighbours = forest.getNeighbourCells(position);

        for (int i = 0; i < neighbours.size(); i++) {
            Cell cell = neighbours.get(i);
            Tree tree = cell.getTree();

            if (tree == null){ 
                continue;   // no tree in this cell
            }      

            if (tree.isBurning()){
                continue;      // already on fire
            } 

            double chance = spreadChance(tree, wind);
            if (Math.random() < chance) {
                tree.ignite();
            }
        }


    }


    private double spreadChance(Tree tree, Wind wind){
        double base = intensity/100.0; 
        double windBoost = windInfluence(wind, tree);
        return Math.min(base + windBoost, 1.0); // ensures that the chance does not exceed 100%


    }

    private double windInfluence(Wind wind, Tree tree){
        Position treePos = tree.getPosition();
        int dx = treePos.x - position.x;
        int dy = treePos.y - position.y;

        if(dx == 0 && dy == 0){
            return 0; //same position, no influence
        }

        double length = Math.sqrt(dx*dx + dy*dy);
        double dirX = dx/length;
        double dirY = dy/length;

        double windX = wind.x;
        double windY = wind.y;
        double windLength = Math.sqrt(windX*windX + windY*windY);

        if (windLength == 0){
            return 0; // no wind, no influence
        }

        windX /= windLength; 
        windY /= windLength;

        double allignment = (dirX * windX) + (dirY * windY); 
        double normalisedPower = wind.getWindPower() / 100.0;

        return Math.max(allignment, 0) * normalisedPower * 0.3; // the 0.3 factor is a scaling factor to reduce the influence of wind on fire spread

    }

    public int fireIntensity(){
        return intensity; // returns the instensity of the fire, which can be used to determine how quickly it spreads or how much damage it does to trees
    }
}