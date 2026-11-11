import java.util.ArrayList;
import java.util.List;

public class FireAvoidanceMovement implements AnimalMovement {
    @Override
    public Position chooseDestination(
            Position currentPosition,
            List<Position> neighbours,
            List<Position> fireHazards) {
        if (neighbours.isEmpty()) {
            return null;
        }
        if (fireHazards.isEmpty()) {
            return chooseRandom(neighbours);
        }

        int currentDistance = distanceToNearestFire(currentPosition, fireHazards);
        List<Position> saferDestinations = new ArrayList<>();
        List<Position> nonFireDestinations = new ArrayList<>();

        for (Position neighbour : neighbours) {
            if (!isOnFire(neighbour, fireHazards)) {
                nonFireDestinations.add(neighbour);
                if (distanceToNearestFire(neighbour, fireHazards) >= currentDistance) {
                    saferDestinations.add(neighbour);
                }
            }
        }

        List<Position> possibleDestinations = saferDestinations.isEmpty()
            ? nonFireDestinations
            : saferDestinations;
        return possibleDestinations.isEmpty() ? null : chooseRandom(possibleDestinations);
    }

    private Position chooseRandom(List<Position> positions) {
        return positions.get((int) (Math.random() * positions.size()));
    }

    private int distanceToNearestFire(Position target, List<Position> fireHazards) {
        int nearestDistance = Integer.MAX_VALUE;
        for (Position firePosition : fireHazards) {
            int dx = target.x - firePosition.x;
            int dy = target.y - firePosition.y;
            nearestDistance = Math.min(nearestDistance, dx * dx + dy * dy);
        }
        return nearestDistance;
    }

    private boolean isOnFire(Position target, List<Position> fireHazards) {
        for (Position firePosition : fireHazards) {
            if (target.x == firePosition.x && target.y == firePosition.y) {
                return true;
            }
        }
        return false;
    }
}
