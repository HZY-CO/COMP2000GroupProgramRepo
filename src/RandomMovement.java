import java.util.List;

public class RandomMovement implements AnimalMovement {
    @Override
    public Position chooseDestination(
            Position currentPosition,
            List<Position> neighbours,
            List<Position> fireHazards) {
        if (neighbours.isEmpty()) {
            return null;
        }
        return neighbours.get((int) (Math.random() * neighbours.size()));
    }
}
