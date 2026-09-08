public interface Tickable {
    void tick();
    boolean isActive();
    void setActive(boolean active);
}