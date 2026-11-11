import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SimulationTest {
    @Test
    void ownsForestAndAdvancesItsTickState() {
        Simulation simulation = new Simulation(4, 3, 10, 20, 8);

        assertEquals(4, simulation.getForest().getWidth());
        assertEquals(3, simulation.getForest().getHeight());
        assertEquals(10, simulation.getAnimals().size());

        simulation.tick();

        assertEquals(1, simulation.getTicksPassed());
        assertEquals(
            simulation.getAnimals().size(),
            simulation.getAnimalsAlive() + simulation.getAnimalsDead()
        );
    }

    @Test
    void replenishesDeadAnimalsAtRandomGridEdgesUpToMaximum() {
        Simulation simulation = new Simulation(4, 3, 10, 20, 8);
        simulation.getAnimals().get(0).setActive(false);
        simulation.getAnimals().get(1).setActive(false);

        simulation.replenishAnimals();
        simulation.updateAnalytics();

        assertEquals(12, simulation.getAnimals().size());
        assertEquals(10, simulation.getAnimalsAlive());
        assertEquals(2, simulation.getAnimalsDead());
        for (int i = 10; i < 12; i++) {
            Position position = simulation.getAnimals().get(i).getPosition();
            assertTrue(
                position.x == 0
                    || position.x == simulation.getForest().getWidth() - 1
                    || position.y == 0
                    || position.y == simulation.getForest().getHeight() - 1
            );
        }
    }

    @Test
    void waitsForAllFiresAndBurningTreesToBurnOutBeforeReplenishing() {
        Simulation simulation = new Simulation(4, 3, 10, 20, 8);
        Forest forest = simulation.getForest();
        Animal deadAnimal = simulation.getAnimals().get(0);
        deadAnimal.setActive(false);
        Fire fire = new Fire(
            new Position(0, 0),
            10,
            0,
            new ForestManager(forest, new Wind(0, 0, 0))
        );
        forest.indexFire(fire);
        Tree burningTree = new Tree(
            new Position(1, 0),
            2,
            Tree.TreeState.BURNING,
            null
        );
        forest.getCellfromPosition(new Position(1, 0)).setTree(burningTree);

        simulation.replenishAnimals();
        assertEquals(10, simulation.getAnimals().size());

        fire.setActive(false);
        simulation.replenishAnimals();
        assertEquals(10, simulation.getAnimals().size());

        burningTree.burnOut();
        simulation.replenishAnimals();
        simulation.updateAnalytics();

        assertEquals(11, simulation.getAnimals().size());
        assertEquals(10, simulation.getAnimalsAlive());
        assertEquals(1, simulation.getAnimalsDead());
    }

    @Test
    void countsAliveAndDeadAnimalsWithParallelCollector() {
        Simulation simulation = new Simulation(4, 3, 10, 20, 8);
        simulation.getAnimals().get(0).setActive(false);
        simulation.getAnimals().get(1).setActive(false);

        simulation.replenishAnimals();
        simulation.updateAnalytics();

        assertEquals(10, simulation.getAnimalsAlive());
        assertEquals(2, simulation.getAnimalsDead());
    }

    @Test
    void countsTreeStatesWithParallelCollector() {
        Simulation simulation = new Simulation(3, 1, 10, 20, 8);
        Forest forest = simulation.getForest();
        forest.getCellfromPosition(new Position(0, 0)).setTree(new Tree(
            new Position(0, 0), 2, Tree.TreeState.GROWING, null
        ));
        forest.getCellfromPosition(new Position(1, 0)).setTree(new Tree(
            new Position(1, 0), 2, Tree.TreeState.BURNING, null
        ));
        forest.getCellfromPosition(new Position(2, 0)).setTree(new Tree(
            new Position(2, 0), 2, Tree.TreeState.FULLY_GROWN, null
        ));

        simulation.updateAnalytics();

        assertEquals(3, simulation.getTreeAmount());
        assertEquals(1, simulation.getTreesBurning());
    }
}
