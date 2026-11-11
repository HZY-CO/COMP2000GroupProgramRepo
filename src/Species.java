import java.awt.Color;

public enum Species {
    GRASS(10, new Color(170, 220, 60)),   // grows and burns fast
    SHRUB(25, new Color(60, 160, 60)),    // acts ads middle ground
    TREE(50, new Color(0, 110, 0));       // grows and burns slow

    private final int maxAge;
    private final Color colour;

    Species(int maxAge, Color colour) {
        this.maxAge = maxAge;
        this.colour = colour;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public Color getColour() {
        return colour;
    }
}
