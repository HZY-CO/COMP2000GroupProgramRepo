public class PositionExceptions extends RuntimeException {
    private final int x;
    private final int y;

    public PositionExceptions(Position position) {
        super("Position (" + position.x + ", " + position.y + ") is outside the forest bounds");
        this.x = position.x;
        this.y = position.y;
    }

    public PositionExceptions(Position position, String message) {
        super(message + " [position: (" + position.x + ", " + position.y + ")]");
        this.x = position.x;
        this.y = position.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

