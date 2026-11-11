
public enum Biome {
    GRASSLAND(0.8, 0.75, 0.20, 0.05, 0.02),
    WOODLAND (0.7, 0.10, 0.30, 0.60, 0.03),
    ROCKY    (0.4, 0.40, 0.50, 0.10, 0.20);

    private final double density;
    private final double grassChance;
    private final double shrubChance;
    private final double treeChance;
    private final double rockChance;

    Biome(double density, double grassChance, double shrubChance, double treeChance, double rockChance) {
        this.density = density;
        this.grassChance = grassChance;
        this.shrubChance = shrubChance;
        this.treeChance = treeChance;
        this.rockChance = rockChance;
    }

    public double getDensity() {
        return density;
    }

    public double getRockChance() {
        return rockChance;
    }

    
    public Species pickSpecies() {
        double total = grassChance + shrubChance + treeChance;
        double roll = Math.random() * total;

        if (roll < grassChance) {
            return Species.GRASS;
        }
        if (roll < grassChance + shrubChance) {
            return Species.SHRUB;
        }
        return Species.TREE;
    }
}
