import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void burningTreeCanBurnWithoutGrowingFromAgeZero() {
        int burntCountBefore = App.getBurntTreeCount();
        Tree tree = new Tree(
            new Position(1, 2),
            2,
            Tree.TreeState.BURNING,
            null
        );

        tree.tick();

        assertEquals(0, tree.getAge());
        assertEquals(Tree.TreeState.BURNING, tree.getState());
        assertTrue(tree.isActive());

        tree.tick();

        assertEquals(Tree.TreeState.BURNT, tree.getState());
        assertFalse(tree.isActive());
        assertEquals(burntCountBefore + 1, App.getBurntTreeCount());

        tree.burnOut();
        tree.tick();
        assertEquals(burntCountBefore + 1, App.getBurntTreeCount());
    }

    @Test
    void replacesBurntTreeWithNewGrowingTreeAfterRecoveryPeriod() {
        Forest forest = new Forest(3, 1);
        ForestManager forestManager = new ForestManager(forest, new Wind(0, 0, 0));
        Tree burntTree = new Tree(
            new Position(1, 0),
            1,
            Tree.TreeState.BURNT,
            null
        );
        forestManager.addTree(burntTree);

        for (int i = 0; i < 10; i++) {
            forestManager.tickTrees();
        }

        Tree replacement = forest.getAllTrees().get(0);
        assertNotSame(burntTree, replacement);
        assertEquals(Tree.TreeState.GROWING, replacement.getState());
        assertEquals(0, replacement.getAge());
        assertTrue(forest.inForestBounds(
            replacement.getPosition().x,
            replacement.getPosition().y
        ));
        assertSame(
            replacement,
            forest.getCellfromPosition(replacement.getPosition()).getTree()
        );
    }

    @Test
    void waitsForAllFiresToGoOutBeforeStartingRegrowthTimer() {
        Forest forest = new Forest(3, 1);
        ForestManager forestManager = new ForestManager(forest, new Wind(0, 0, 0));
        Tree burntTree = new Tree(
            new Position(0, 0),
            1,
            Tree.TreeState.BURNT,
            null
        );
        forestManager.addTree(burntTree);

        Fire fire = new Fire(
            new Position(2, 0),
            1,
            0,
            forestManager
        );
        forest.indexFire(fire);

        for (int i = 0; i < 10; i++) {
            forestManager.tickTrees();
        }
        assertSame(burntTree, forest.getCellfromPosition(new Position(0, 0)).getTree());

        forest.tickFires();
        for (int i = 0; i < 9; i++) {
            forestManager.tickTrees();
        }
        assertSame(burntTree, forest.getCellfromPosition(new Position(0, 0)).getTree());

        forestManager.tickTrees();
        assertFalse(forest.getAllTrees().contains(burntTree));
        assertEquals(1, forest.getAllTrees().size());
        assertEquals(Tree.TreeState.GROWING, forest.getAllTrees().get(0).getState());
    }
}
