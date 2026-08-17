public class Wind{
    int x;
    int y;
    int windPower;

    public Wind(int x, int y, int windPower){
        this.x = x;
        this.y = y;
        this.windPower = windPower;
    }

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

    private int clamp(int value, int min, int max){
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    
}