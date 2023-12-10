package examle;
import java.util.List;

public interface DAO<T>{
    void create(T object);

    void update(T object);

    void delete(T object);

    List<T> getAll();
}