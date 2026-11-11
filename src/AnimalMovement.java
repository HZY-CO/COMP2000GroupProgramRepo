import java.util.List;

public interface AnimalMovement {
    Position chooseDestination(
        Position currentPosition,
        List<Position> neighbours,
        List<Position> fireHazards
    );
}
