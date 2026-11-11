import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AnimalTest {
    @Test
    void movesToAnAdjacentInBoundsCellOnEachTick() {
        Forest forest = new Forest(2, 2);
        Animal animal = new Animal(new Position(0, 0), forest);

        for (int i = 0; i < 20; i++) {
            Position previousPosition = animal.getPosition();
            animal.tick();
            Position currentPosition = animal.getPosition();

            assertFalse(
                previousPosition.x == currentPosition.x
                    && previousPosition.y == currentPosition.y
            );
            assertTrue(Math.abs(previousPosition.x - currentPosition.x) <= 1);
            assertTrue(Math.abs(previousPosition.y - currentPosition.y) <= 1);
            assertTrue(forest.inForestBounds(currentPosition.x, currentPosition.y));
        }
    }

    @Test
    void remainsInPlaceWhenForestHasNoNeighbouringCells() {
        Forest forest = new Forest(1, 1);
        Animal animal = new Animal(new Position(0, 0), forest);

        animal.tick();

        assertEquals(0, animal.getPosition().x);
        assertEquals(0, animal.getPosition().y);
    }

    @Test
    void acceptsAnInjectedMovementStrategy() {
        Forest forest = new Forest(2, 2);
        AnimalMovement strategy = (current, neighbours, fireHazards) ->
            neighbours.get(0);
        Animal animal = new Animal(new Position(0, 0), forest, strategy);

        animal.tick();

        assertEquals(1, animal.getPosition().x);
        assertEquals(0, animal.getPosition().y);
        assertTrue(animal.getMovementStrategy() == strategy);
    }

    @Test
    void mayMoveIntoCellsOccupiedByTrees() {
        Forest forest = new Forest(2, 2);
        ForestManager forestManager = new ForestManager(forest, new Wind(0, 0, 0));
        forestManager.addTree(new Tree(
            new Position(1, 0),
            1,
            Tree.TreeState.GROWING,
            null
        ));
        forestManager.addTree(new Tree(
            new Position(0, 1),
            1,
            Tree.TreeState.GROWING,
            null
        ));
        forestManager.addTree(new Tree(
            new Position(1, 1),
            1,
            Tree.TreeState.GROWING,
            null
        ));
        Animal animal = new Animal(new Position(0, 0), forest);

        animal.tick();

        assertTrue(forest.getCellfromPosition(animal.getPosition()).getTree() != null);
    }

    @Test
    void movesAwayFromAnActiveFireWhenItCan() {
        Forest forest = new Forest(2, 2);
        ForestManager forestManager = new ForestManager(forest, new Wind(0, 0, 0));
        forest.indexFire(new Fire(new Position(1, 0), 10, 0, forestManager));
        Animal animal = new Animal(new Position(0, 0), forest);

        animal.tick();

        Position position = animal.getPosition();
        int distanceFromFire = (position.x - 1) * (position.x - 1)
            + position.y * position.y;
        assertTrue(distanceFromFire >= 1);
        assertTrue(position.x != 1 || position.y != 0);
        assertTrue(animal.isActive());
    }

    @Test
    void movesToANonFireCellWhenAllMovesAreCloserToFire() {
        Forest forest = new Forest(3, 1);
        ForestManager forestManager = new ForestManager(forest, new Wind(0, 0, 0));
        forest.indexFire(new Fire(new Position(0, 0), 10, 0, forestManager));
        Animal animal = new Animal(new Position(2, 0), forest);

        animal.tick();

        assertEquals(1, animal.getPosition().x);
        assertEquals(0, animal.getPosition().y);
        assertTrue(animal.isActive());
    }

    @Test
    void diesIfAlreadyOnAnActiveFire() {
        Forest forest = new Forest(2, 1);
        ForestManager forestManager = new ForestManager(forest, new Wind(0, 0, 0));
        forest.indexFire(new Fire(new Position(0, 0), 10, 0, forestManager));
        Animal animal = new Animal(new Position(0, 0), forest);

        animal.tick();

        assertEquals(0, animal.getPosition().x);
        assertFalse(animal.isActive());
    }

    @Test
    void diesWhenOnBurningTreeEvenIfItsFireObjectIsInactive() {
        Forest forest = new Forest(2, 1);
        ForestManager forestManager = new ForestManager(forest, new Wind(0, 0, 0));
        Tree burningTree = new Tree(
            new Position(0, 0),
            2,
            Tree.TreeState.BURNING,
            null
        );
        forestManager.addTree(burningTree);
        Animal animal = new Animal(new Position(0, 0), forest);

        animal.tick();

        assertFalse(animal.isActive());
    }

    @Test
    void diesWhenMoveIsCalledWhileAlreadyOnFire() {
        Forest forest = new Forest(1, 1);
        ForestManager forestManager = new ForestManager(forest, new Wind(0, 0, 0));
        forest.indexFire(new Fire(new Position(0, 0), 10, 0, forestManager));
        Animal animal = new Animal(new Position(0, 0), forest);

        animal.move();

        assertFalse(animal.isActive());
    }

    @Test
    void inactiveAnimalDoesNotMove() {
        Forest forest = new Forest(2, 2);
        Animal animal = new Animal(new Position(0, 0), forest);
        animal.setActive(false);

        animal.tick();

        assertEquals(0, animal.getPosition().x);
        assertEquals(0, animal.getPosition().y);
    }
}
