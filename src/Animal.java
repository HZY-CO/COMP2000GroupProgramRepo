import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Animal extends Entity implements Tickable, Movable {
    private final Forest forest;
    private boolean active = true;
    private AnimalMovement movementStrategy;

    public Animal(Position position, Forest forest) {
        this(position, forest, new FireAvoidanceMovement());
    }

    public Animal(
            Position position,
            Forest forest,
            AnimalMovement movementStrategy) {
        super(position);
        this.forest = forest;
        this.movementStrategy = Objects.requireNonNull(movementStrategy);
        forest.getCellfromPosition(position);
    }

    @Override
    public void tick() {
        if (!active) {
            return;
        }
        move();
    }

    @Override
    public void move() {
        if (!active) {
            return;
        }
        if (isOnFireHazard()) {
            setActive(false);
            return;
        }

        List<Cell> neighbours = forest.getNeighbourCells(position);
        if (neighbours.isEmpty()) {
            return;
        }

        List<Position> neighbourPositions = new ArrayList<>();
        for (Cell neighbour : neighbours) {
            neighbourPositions.add(neighbour.getPosition());
        }
        Position destination = movementStrategy.chooseDestination(
            position,
            neighbourPositions,
            getFireHazards()
        );
        if (destination == null) {
            return;
        }

        position = new Position(destination.x, destination.y);
        if (isOnFireHazard()) {
            setActive(false);
        }
    }

    private List<Position> getFireHazards() {
        List<Position> fireHazards = new ArrayList<>();
        for (Fire fire : forest.getAllFires()) {
            if (fire.isActive()) {
                fireHazards.add(fire.getPosition());
            }
        }

        for (Tree tree : forest.getAllTrees()) {
            if (tree.isBurning()) {
                fireHazards.add(tree.getPosition());
            }
        }
        return fireHazards;
    }

    private boolean isOnFire(Position target, List<Position> fireHazards) {
        for (Position firePosition : fireHazards) {
            if (target.x == firePosition.x && target.y == firePosition.y) {
                return true;
            }
        }
        return false;
    }

    private boolean isOnFireHazard() {
        return isOnFire(position, getFireHazards());
    }

    public AnimalMovement getMovementStrategy() {
        return movementStrategy;
    }

    public void setMovementStrategy(AnimalMovement movementStrategy) {
        this.movementStrategy = Objects.requireNonNull(movementStrategy);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
