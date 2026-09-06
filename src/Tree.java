import java.awt.Color;
import java.awt.Graphics;

public class Tree extends Entity {
    public enum TreeState {
        FULLY_GROWN,
        GROWING,
        BURNING,
        BURNT
    }

    private TreeState state;
    int maxAge;
    int age;

    public Tree(int x, int y, int maxAge, int age, TreeState state) {
        super(x, y);
        this.maxAge = maxAge;
        this.age = age;
        this.state = state;
    }

    @Override
    public void update() {
        switch (state) {
            case GROWING:
                age++;
                if (age >= maxAge) {
                    state = TreeState.FULLY_GROWN;
                }
                break;

            case FULLY_GROWN:
                // Remains healthy and stationary until ignited
                break;

            case BURNING:
                age--;
                if (age <= 0) {
                    state = TreeState.BURNT;
                    setActive(false); // Can be purged or drawn as dead ground
                }
                break;

            case BURNT:
                break;
        }   
    }

    public void draw(Graphics g, int cellSize) {
        switch (state) {
            case FULLY_GROWN:
                g.setColor(new Color(34, 139, 34));   // Deep Forest Green
                break;
            case GROWING:
                g.setColor(new Color(144, 238, 144)); // Light Green
                break;
            case BURNING:
                g.setColor(new Color(255, 69, 0));    // Fire Orange-Red
                break;
            case BURNT:
                g.setColor(new Color(50, 50, 50));    // Dark Charcoal
                break;
        }

        g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize); 
    }
}