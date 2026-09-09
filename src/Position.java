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
        return new Position[]{
            new Position(x - 1, y),     // Left
            new Position(x + 1, y),     // Right
            new Position(x, y - 1),     // Up
            new Position(x, y + 1),     // Down
            new Position(x - 1, y - 1), // Up-Left
            new Position(x + 1, y - 1), // Up-Right
            new Position(x - 1, y + 1), // Down-Left
            new Position(x + 1, y + 1)  // Down-Right
        };
    }
}