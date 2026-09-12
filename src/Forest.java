import java.util.List;
import java.util.ArrayList;

/**
 * Represents a forest containing trees, fires, lightnings, and winds.
 */

public class Forest {
    private final int width;
    private final int height;
    private final Cell[][] grid;

    public Forest(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[width][height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean inForestBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Cell getCellfromPosition(Position position) {
        if (inForestBounds(position.x, position.y)) {
            return grid[position.x][position.y];
        }
        return null;
    }

    /**
     * Returns a list of neighbouring cells for a given position in the forest. 
     * @param position
     * @return
     */
    public List<Cell> getNeighbourCells(Position position) {
        List<Cell> neighbours = new ArrayList<>(); 
        
        Position[] neighbourPositions = position.getNeighbourPositions();
        for (Position pos : neighbourPositions) {
            if (inForestBounds(pos.x, pos.y)) {
                Cell neighbourCell = getCellfromPosition(pos);
                if (neighbourCell != null) {
                    neighbours.add(neighbourCell);
                }
            }
        }

        return neighbours;
    }

    public List<Tree> getAllTrees() {
        List<Tree> trees = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = grid[x][y];
                if (cell != null && cell.getTree() != null) {
                    trees.add(cell.getTree());
                }
            }
        }
        return trees;
    }
}
