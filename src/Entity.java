public abstract class Entity {
    protected final Position position;
    protected boolean active = true;

    public Entity(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}