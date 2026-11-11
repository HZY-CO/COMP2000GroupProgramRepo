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
    private int burnTimer;
    private boolean active = true;
    private Panel panel;
    private static final int MAX_BURNT_DURATION = 10;
    private int burntTimer = MAX_BURNT_DURATION;
    private Species species;

    public Tree(Position position, int maxAge, TreeState state, Panel panel) {
        this(position, maxAge, state, panel, Species.TREE);
    }

    public Tree(Position position, int maxAge, TreeState state, Panel panel, Species species) {
        super(position);
        this.maxAge = maxAge;
        this.state = state;
        this.panel = panel;
        this.species = species;
        if (state == TreeState.BURNING) {
            burnTimer = maxAge;
        }
        if (state == TreeState.FULLY_GROWN) {
            age = maxAge;
        }
    }

    public TreeState getState() {
        return state;
    }

    public int getAge() {
        return age;
    }

    public Species getSpecies() {
        return species;
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
            burnTimer = maxAge;
            App.addFire(position);
        }
    }

    @Override 
    public void burnOut() {
        if (state == TreeState.BURNING) {
            becomeBurnt();
        }
    }

    private void becomeBurnt() {
        state = TreeState.BURNT;
        setActive(false);
        App.addBurntCounter();
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

    boolean isReadyForReplacement() {
        return state == TreeState.BURNT && burntTimer <= 0;
    }

    Tree createReplacement(Position newPosition) {
        return new Tree(newPosition, maxAge, TreeState.GROWING, panel, species);
    }

    // Used when the new spot is in a different biome, so a different species can grow there
    Tree createReplacement(Position newPosition, Species newSpecies) {
        return new Tree(newPosition, newSpecies.getMaxAge(), TreeState.GROWING, panel, newSpecies);
    }

    public void setDisplayLocation(int x, int y) {
        if (panel != null) {
            panel.setLocation(x, y);
        }
    }

    @Override
    public void tick() {
        tick(true);
    }

    void tick(boolean firesOut) {
        switch (state) {
            case GROWING:
                age++;
                //System.err.println("I'm growing!!");

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
                burnTimer--;
                if (burnTimer <= 0) {
                    becomeBurnt();
                }
                break;
            case BURNT:
                if (firesOut) {
                    burntTimer = Math.max(0, burntTimer - 1);
                }
                break;
        }   
    }

    public void draw() {
        switch (state) {
            case FULLY_GROWN:
                panel.setBackground(species.getColour());   // Colour depends on species
                break;
            case GROWING:
                float g = ((float) age / maxAge);
                if (g <= 0) {
                    g = 0;
                }
                if (g > 1) {
                    g = 1;
                }
                // Fades from black up to the species colour as it grows
                Color colour = species.getColour();
                panel.setBackground(new Color(
                        (int) (colour.getRed() * g),
                        (int) (colour.getGreen() * g),
                        (int) (colour.getBlue() * g)));
                break;
            case BURNING:
                float r = ((float) burnTimer / maxAge);
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
    }
}