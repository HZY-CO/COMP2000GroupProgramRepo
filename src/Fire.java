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

    }

    public void spread(){

    }

    private boolean isAdjacent(Tree tree){

    }

    private double spreadChance(Tree tree){

    }

    private double WindInfluence(Wind wind, Tree tree){

    }

    public int fireIntensity(){
        return intensity;
    }
}