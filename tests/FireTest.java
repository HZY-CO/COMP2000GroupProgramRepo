import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FireTest {
    @Test
    void removesFireAfterItsLastActiveTick() {
        Forest forest = new Forest(1, 1);
        ForestManager forestManager = new ForestManager(forest, new Wind(0, 0, 0));
        Fire fire = new Fire(new Position(0, 0), 1, 0, forestManager);
        forest.indexFire(fire);

        forest.tickFires();

        assertTrue(forest.getAllFires().isEmpty());
    }

    @Test
    void removesAlreadyInactiveFireWithoutTickingIt() {
        Forest forest = new Forest(2, 1);
        ForestManager forestManager = new ForestManager(forest, new Wind(0, 0, 0));
        Fire fire = new Fire(new Position(0, 0), 1, 0, forestManager);
        Tree tree = new Tree(
            new Position(1, 0),
            1,
            Tree.TreeState.GROWING,
            null
        );
        forestManager.addTree(tree);
        fire.setActive(false);
        forest.indexFire(fire);

        forest.tickFires();

        assertTrue(forest.getAllFires().isEmpty());
        assertEquals(Tree.TreeState.GROWING, tree.getState());
    }

    @Test
    void doesNotSpreadWhenItsDurationHasAlreadyExpired() {
        Forest forest = new Forest(2, 1);
        ForestManager forestManager = new ForestManager(forest, new Wind(0, 0, 0));
        Fire fire = new Fire(new Position(0, 0), 0, 100, forestManager);
        Tree tree = new Tree(
            new Position(1, 0),
            1,
            Tree.TreeState.GROWING,
            null
        );
        forestManager.addTree(tree);
        forest.indexFire(fire);

        forest.tickFires();

        assertTrue(forest.getAllFires().isEmpty());
        assertEquals(Tree.TreeState.GROWING, tree.getState());
    }
}
