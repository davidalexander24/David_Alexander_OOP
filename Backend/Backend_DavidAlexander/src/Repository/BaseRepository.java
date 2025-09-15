package Repository;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseRepository<T, ID> {
    HashMap<ID, T> Map = new HashMap<>();
    ArrayList<T> List = new ArrayList<>();
    ArrayList<T> AllData

    public T findById(ID id) {
        return Map.get(id);
    }

    public T findAll() {
        return List
    }

    public abstract void save(T entity);

    public abstract ID getId(T entity);

}
