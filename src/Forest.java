import java.util.List;
import java.util.ArrayList;

/**
 * Represents a forest containing trees, fires, lightnings, and winds.
 */

public class Forest {
    private int width;
    private int height;
    //private final Cell[][] grid;
    private ArrayList<Cell> grid = new ArrayList<Cell>();

    public Forest(int width, int height) {
        this.width = width;
        this.height = height;
        //this.grid = new Cell[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Position pos = new Position(x, y);
                Cell cell = new Cell(pos);
                grid.add(cell);
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
        //System.err.println(x >= 0 && x < width && y >= 0 && y < height);
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Cell getCellfromPosition(Position position) {
        if (inForestBounds(position.x, position.y)) {
            //System.err.println("reached");
            for (int i = 0; i < grid.size(); i++) {
                Cell cell = grid.get(i);
                if(cell.getPosition().x == position.x && cell.getPosition().y == position.y)
                {
                    //System.err.println("found");
                    return cell;
                }
            }

            //return grid[position.x][position.y];
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
        // for (int x = 0; x < width; x++) {
        //     for (int y = 0; y < height; y++) {
        //         Cell cell = grid[x][y];
        //         if (cell != null && cell.getTree() != null) {
        //             trees.add(cell.getTree());
        //         }
        //     }
        // }

        for (int i = 0; i < grid.size(); i++) {
                Cell cell = grid.get(i);
                if (cell != null && cell.getTree() != null) {
                    trees.add(cell.getTree());
                }
            }
        return trees;
    }
}
