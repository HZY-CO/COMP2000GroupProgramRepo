import java.awt.Color;
import java.awt.Graphics;

public class Tree extends Entity implements Tickable, Flammable {
    public enum TreeState {
        FULLY_GROWN,
        GROWING,
        BURNING,
        BURNT
    }

    private TreeState state;
    private final int maxAge;
    private int age = 0;
    private boolean active = true;

    public Tree(Position position, int maxAge, TreeState state) {
        super(position);
        this.maxAge = maxAge;
        this.state = state;
    }

    public TreeState getState() {
        return state;
    }

    // Flammable interface methods
    @Override 
    public Position getPosition() {
        return position;
    }

    @Override 
    public boolean isBurning() {
        return state == TreeState.BURNING;
    }

    @Override 
    public void ignite() {
        if (state == TreeState.FULLY_GROWN || state == TreeState.GROWING) {
            state = TreeState.BURNING;
        }
    }

    @Override 
    public void burnOut() {
        if (state == TreeState.BURNING) {
            state = TreeState.BURNT;
            setActive(false);
        }
    }

    // Tickable interface method
    @Override 
    public boolean isActive() {
        return active;
    }
    
    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void tick() {
        switch (state) {
            case GROWING:
                age++;
                // Tree has fully grown and will not age further
                if (age == maxAge) {
                    state = TreeState.FULLY_GROWN;
                }
                break;
            case FULLY_GROWN:
                // Age remains constant until tree is ignited
                break;

            case BURNING:
                age--;
                if (age <= 0) {
                    state = TreeState.BURNT;
                    setActive(false);
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

        g.fillRect(position.x * cellSize, position.y * cellSize, cellSize, cellSize); 
    }
}