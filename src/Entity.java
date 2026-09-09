public abstract class Entity {
    protected final Position position;

    public Entity(Position position) {
        this.position = position;
    }

    public Entity(int x, int y) {
        this(new Position(x, y));
    }

    public Position getPosition() {
        return position;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }
}
