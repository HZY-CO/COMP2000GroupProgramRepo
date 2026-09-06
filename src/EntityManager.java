import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class EntityManager<T extends Entity> {
    private List<T> entities = new ArrayList<>();
    private List<T> pendingAdds = new ArrayList<>();
    private List<T> pendingRemoves = new ArrayList<>();

    public void add(T entity) {
        pendingAdds.add(entity);
    }

    public void remove(T entity) {
        pendingRemoves.add(entity);
    }

    public List<T> getAll() {
        return entities;
    }

    public List<T> getActiveList() {
        List<T> active = new ArrayList<>();
        for (T entity : entities) {
            if (entity.isActive()) {
                active.add(entity);
            }
        }

        return active;
    }

    public void update() {
        for (T entity : entities) {
            if (entity.isActive()) {
                entity.update();
            }
        }
        
        entities.addAll(pendingAdds);
        entities.removeAll(pendingRemoves);
        pendingAdds.clear();
        pendingRemoves.clear();
    }

    public void cleanup() {
        Iterator<T> iterator = entities.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isActive()) {
                iterator.remove();
            }
        }
    }
}