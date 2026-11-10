import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class CellTest {
    @Test
    void 
    storesItsPositionAndOptionalTree() {
        Position position = new Position(2, 3);
        Cell cell = new Cell(position);
        Tree tree = new Tree(position, 5, Tree.TreeState.GROWING, null);

        assertSame(position, cell.getPosition());
        assertNull(cell.getTree());

        cell.setTree(tree);

        assertSame(tree, cell.getTree());
    }
}
