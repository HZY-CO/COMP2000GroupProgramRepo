import java.awt.Color;
import java.awt.Graphics;

public class Tree extends Entity implements Tickable {
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

    public Tree(int x, int y, int maxAge, int age, TreeState state) {
        this(new Position(x, y), maxAge, state);
        this.age = age;
    }

    public TreeState getState() {
        return state;
    }

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
        if (state == TreeState.GROWING) {
            age++;
            if (age >= maxAge) {
                state = TreeState.FULLY_GROWN;
            }
        }
    }

    public void draw(Graphics g, int cellSize) {
        switch (state) {
            case FULLY_GROWN:
                g.setColor(new Color(34, 139, 34));
                break;
            case GROWING:
                g.setColor(new Color(144, 238, 144));
                break;
            case BURNING:
                g.setColor(new Color(255, 69, 0));
                break;
            case BURNT:
                g.setColor(new Color(50, 50, 50));
                break;
        }

        g.fillRect(position.x * cellSize, position.y * cellSize, cellSize, cellSize);
    }

    public void ignite() {
        if (state == TreeState.GROWING || state == TreeState.FULLY_GROWN) {
            state = TreeState.BURNING;
        }
    }

    public void burnOut() {
        if (state == TreeState.BURNING) {
            state = TreeState.BURNT;
            active = false;
        }
    }

    public boolean isBurning() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isBurning'");
    }
}
