import java.awt.Color;
import java.awt.Panel;

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
    private Panel panel;

    public Tree(Position position, int maxAge, TreeState state, Panel panel) {
        super(position);
        this.maxAge = maxAge;
        this.state = state;
        this.panel = panel;
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

    public void draw(Panel p, int cellSize) {
        switch (state) {
            case FULLY_GROWN:
                //panel.setBackground(new Color(34, 139, 34));   // Deep Forest Green
                break;
            case GROWING:
                float g = ((float) age / maxAge);
                if (g <= 0) {
                    g = 0;
                }
                if (g > 1) {
                    g = 1;
                }
                panel.setBackground(new Color(0, g, 0));
                break;
            case BURNING:
                float r = 1 - ((float) age / maxAge);
                if (r <= 0) {
                    r = 0;
                }
                if (r > 1) {
                    r = 1;
                }
                System.err.println(r);
                panel.setBackground(new Color(r, 0, 0));
                break;
            case BURNT:
                panel.setBackground(new Color(50, 50, 50));    // Dark Charcoal
                break;
        }

        //p.fillRect(position.x * cellSize, position.y * cellSize, cellSize, cellSize); 


    }
}