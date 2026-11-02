import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class ForestTest {
    @Test
    void createsOneCellForEachForestCoordinate() {
        Forest forest = new Forest(3, 2);
        Set<Cell> cells = new HashSet<>();

        assertEquals(3, forest.getWidth());
        assertEquals(2, forest.getHeight());
        for (int x = 0; x < forest.getWidth(); x++) {
            for (int y = 0; y < forest.getHeight(); y++) {
                assertTrue(cells.add(
                    forest.getCellfromPosition(new Position(x, y))
                ));
            }
        }
        assertEquals(6, cells.size());
    }

    @Test
    void rejectsNonPositiveDimensions() {
        assertThrows(ForestDimensionException.class, () -> new Forest(0, 2));
        assertThrows(ForestDimensionException.class, () -> new Forest(2, -1));
    }

    @Test
    void returnsOnlyInBoundsNeighbours() {
        Forest forest = new Forest(2, 2);

        assertEquals(3, forest.getNeighbourCells(new Position(0, 0)).size());
        assertTrue(forest.getNeighbourCells(new Position(0, 0)).stream()
            .allMatch(cell -> forest.inForestBounds(
                cell.getPosition().x,
                cell.getPosition().y
            )));
    }

    @Test
    void rejectsPositionsOutsideForest() {
        Forest forest = new Forest(2, 2);

        assertThrows(
            PositionExceptions.class,
            () -> forest.getCellfromPosition(new Position(2, 0))
        );
    }
}
