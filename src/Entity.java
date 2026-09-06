public abstract class Entity {
    protected int x;
    protected int y;
    protected boolean active;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = true;
    }

    public abstract void update();
 
    public int getX() {
        return x;
    }
 
    public int getY() {
        return y;
    }
 
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}