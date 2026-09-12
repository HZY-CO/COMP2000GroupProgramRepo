/**
 * Represents a position with coordinates in the forest.
 */

public class Position {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position[] getNeighbourPositions() {
        return new Position[] {
            new Position(x - 1, y),
            new Position(x + 1, y),
            new Position(x, y - 1),
            new Position(x, y + 1),
            new Position(x - 1, y - 1),
            new Position(x + 1, y - 1),
            new Position(x - 1, y + 1),
            new Position(x + 1, y + 1)
        };
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
