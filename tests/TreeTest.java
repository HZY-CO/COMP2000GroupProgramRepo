import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class TreeTest {
    @Test
    void growingTreeBecomesFullyGrownAtMaxAge() {
        Tree tree = new Tree(
            new Position(1, 2),
            2,
            Tree.TreeState.GROWING,
            null
        );

        tree.tick();
        assertEquals(Tree.TreeState.GROWING, tree.getState());

        tree.tick();
        assertEquals(Tree.TreeState.FULLY_GROWN, tree.getState());
    }

    @Test
    void burningTreeBecomesBurntAndInactive() {
        Tree tree = new Tree(
            new Position(1, 2),
            2,
            Tree.TreeState.BURNING,
            null
        );

        tree.tick();

        assertEquals(Tree.TreeState.BURNT, tree.getState());
        assertFalse(tree.isActive());
    }
}
