import java.awt.Color;
import java.awt.Graphics;

public class Tree extends Entity{
    int maxAge;
    int age;
    boolean isBurning;

    public Tree(int x, int y, int maxAge, int age, boolean isBurning) {
        super(x, y);
        this.maxAge = maxAge;
        this.age = age;
        this.isBurning = isBurning;
    }

    void growing() {
        if (this.age < this.maxAge && !this.isBurning) {
            this.age++;
        }
    }

    void burning() {
        if (this.age > 0 && this.isBurning) {
            this.age--;
        }
    }

    @Override
    public void update() {    
    }

    public void draw(Graphics g) {
        g.setColor(new Color(34, 139, 34));

        g.fillRect(x * 1, y * 1, 1 - 1, 1 - 1);
    }
}