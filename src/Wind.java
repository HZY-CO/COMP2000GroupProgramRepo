import java.util.stream.IntStream;

/**
 * Represents the wind conditions used by the forest simulation.
 *
 * The inherited x and y values represent the wind direction rather than a
 * physical position. Direction values are expected to remain between -100
 * and 100, while wind power is kept between 0 and 100.
 */
public class Wind extends Position {
    /** Current wind strength, from 0 (calm) to 100 (strong). */
    int windPower;

    /** Creates wind with an initial direction and power. */
    public Wind(int x, int y, int windPower) {
        super(x, y);
        this.windPower = windPower;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWindPower() {
        return windPower;
    }

    public void setWindPower(int windPower) {
        this.windPower = windPower;
    }

    /**
     * Smoothly moves the wind toward a target direction and power.
     *
     * Direction changes use a very gentle blend so the field drifts in a
     * realistic, nature-like pace rather than snapping from one vector to the next.
     */
    public void changeDirection(int targetX, int targetY, int targetPower) {
        double directionBlend = 0.025;
        double powerBlend = 0.035;

        double newX = this.x + (targetX - this.x) * directionBlend;
        double newY = this.y + (targetY - this.y) * directionBlend;
        double newPower = this.windPower + (targetPower - this.windPower) * powerBlend;

        double gustSwing = Math.sin((newX + newY) * 0.35) * 2.5;
        double[] values = {
            newX + gustSwing,
            newY + gustSwing * 0.75,
            newPower
        };
        int[] minimums = {-100, -100, 0};
        int[] maximums = {100, 100, 100};
        int[] boundedValues = IntStream.range(0, values.length)
            .map(index -> {
                int roundedValue = (int) Math.round(values[index]);
                return Math.max(minimums[index], Math.min(roundedValue, maximums[index]));
            })
            .toArray();

        this.x = boundedValues[0];
        this.y = boundedValues[1];
        this.windPower = boundedValues[2];
    }

    /** Reserved for automatic wind changes during a simulation update. */
    public void update() {
        double t = System.currentTimeMillis() / 1000.0;
        int targetX = (int) Math.round(50 * Math.sin(t / 9.0));
        int targetY = (int) Math.round(35 * Math.cos(t / 11.0));
        int targetPower = (int) Math.round(15 + 40 * (0.5 + 0.5 * Math.sin(t / 7.0)));
        changeDirection(targetX, targetY, targetPower);
    }
}