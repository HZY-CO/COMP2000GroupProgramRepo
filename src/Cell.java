public class Cell {
    private final Position position;

    private Tree tree;
    private Biome biome;
    private boolean isRock;

    public Cell(Position position) {
        this.position = position;
        this.tree = null;
        this.biome = Biome.WOODLAND;
        this.isRock = false;
    }

    public Position getPosition() {
        return position;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public Biome getBiome() {
        return biome;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    
    public boolean isRock() {
        return isRock;
    }

    public void setRock(boolean isRock) {
        this.isRock = isRock;
    }
}