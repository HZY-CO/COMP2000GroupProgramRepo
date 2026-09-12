import java.util.ArrayList;
import java.util.List;

/**
 * Represents a forest containing trees, and wind events.
 */
public class Forest {
    private final int width;
    private final int height;
    private final Cell[][] grid;
    private final List<Wind> winds;

    public Forest(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[width][height];
        this.winds = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Cell(x, y);
            }
        }
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
        if (position == null || !inForestBounds(position.getX(), position.getY())) {
            return null;
        }
        return grid[position.getX()][position.getY()];
    }

    public List<Cell> getNeighbourCells(Position position) {
        List<Cell> neighbours = new ArrayList<>(); 
        Position[] neighbourPositions = position.getNeighbourPositions();
        for (Position pos : neighbourPositions) {
            if (inForestBounds(pos.getX(), pos.getY())) {
                neighbours.add(grid[pos.getX()][pos.getY()]);
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

    public List<Wind> getWinds() {
        return new ArrayList<>(winds);
    }

    public void addWind(Wind wind) {
        if (wind != null) {
            winds.add(wind);
        }
    }

    public void removeWind(Wind wind) {
        if (wind != null) {
            winds.remove(wind);
        }
    }
}
