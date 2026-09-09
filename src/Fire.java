import java.util.List;

public class Fire extends Entity {
    private int duration;
    private int intensity;
    private Forest forest; // reference to the forest to access trees and other entities such as Trees, Lightning and Wind

    public Fire(int x, int y, int duration, int intensity, Forest forest) {
        super(x, y);
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
        List<Tree> treeList = forest.getTrees().getAll();

        for(int i = 0; i < treeList.size(); i++){

            Tree tree = treeList.get(i);
            if (tree.getState() == Tree.TreeState.BURNING || tree.getState() == Tree.TreeState.BURNT) {
                continue; // skip trees already burning or already burnt
            }

            if (!isAdjacent(tree)){
                continue; //skip trees that are not adjacent to the fire
            }

            double chance = spreadChance(tree);
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
        List<Wind> windList = forest.getWinds.getAll();
        for(int i = 0; i < windList.size(); i++){
            windBoost += windInfluence(windList.get(i), tree);
        }

        return Math.min(base + windBoost, 1.0);


    }

    private double WindInfluence(Wind wind, Tree tree){


    }

    public int fireIntensity(){
        return intensity;
    }
}