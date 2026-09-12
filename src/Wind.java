/**
 * Represents the wind conditions used by the forest simulation.
 *
 * The inherited x and y values represent the wind direction rather than a
 * physical position. Direction values are expected to remain between -100
 * and 100, while wind power is kept between 0 and 100.
 */
public class Wind extends Entity {
    /** Current wind strength, from 0 (calm) to 100 (strong). */
    int windPower;

    /** Creates wind with an initial direction and power. */
    public Wind(int x, int y, int windPower){
        super(x, y);
        this.windPower = windPower;
    }

    /**
     * Smoothly moves the wind toward a target direction and power.
     *
     * Direction changes use an 18 percent blend and power changes use a
     * 12 percent blend. A small sine-based adjustment adds gust variation.
     * The resulting direction and power are clamped to their valid ranges.
     */
    public void changeDirection(int targetX, int targetY, int targetPower){
        double directionBlend = 0.18;
        double powerBlend = 0.12;

        double newX = this.x + (targetX - this.x) * directionBlend;
        double newY = this.y + (targetY - this.y) * directionBlend;
        double newPower = this.windPower + (targetPower - this.windPower) * powerBlend;

        double gustSwing = Math.sin((newX + newY) * 0.35) * 2.5;
        this.x = clamp((int)Math.round(newX + gustSwing), -100, 100);
        this.y = clamp((int)Math.round(newY + gustSwing * 0.75), -100, 100);
        this.windPower = clamp((int)Math.round(newPower), 0, 100);
    }

    /** Keeps a value inside the supplied inclusive range. */
    private int clamp(int value, int min, int max){
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * Reserved for automatic wind changes during a simulation update.
     * Wind currently changes only when changeDirection is called explicitly.
     */
    public void update() {
        
    }
}