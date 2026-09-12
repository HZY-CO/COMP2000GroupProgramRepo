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
    private int maxBurntDuration = 10;
    private int burntTimer = maxBurntDuration;

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
            App.addFire(position);
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
                if (age >= maxAge) {
                    state = TreeState.FULLY_GROWN;
                }
                break;
            case FULLY_GROWN:
                // Age remains constant until tree is ignited
                if(age != maxAge){
                    //System.err.println("larping ah tree");
                    age = maxAge;
                }
                //System.err.println("Tree at " + position.x + ", " +  position.y + " is fully grown!");
                break;

            case BURNING:
                age--;
                System.err.println("ahh im burning!!!");
                if (age <= 0) {
                    state = TreeState.BURNT;
                    //setActive(false);
                    age = 0;
                }
                break;
            case BURNT:
                burntTimer -= 1;
                if (burntTimer <= 0)
                {
                    System.err.println("i want to grow again!!");
                    state = TreeState.GROWING;
                    burntTimer = maxBurntDuration;
                    age = 0;
                }
                break;
        }   
    }

    public void draw() {
        switch (state) {
            case FULLY_GROWN:
                panel.setBackground(new Color(0, 1f, 0));   // Green
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
                float r = ((float) age / maxAge);
                if (r <= 0) {
                    r = 0;
                }
                if (r > 1) {
                    r = 1;
                }
                panel.setBackground(new Color(r, 0, 0));
                break;
            case BURNT:
                panel.setBackground(new Color(0, 0, 0));    // Black
                break;
        }

        //p.fillRect(position.x * cellSize, position.y * cellSize, cellSize, cellSize); 


    }
}