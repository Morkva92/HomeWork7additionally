package example;

import javax.persistence.EntityManager;
import java.util.List;



public abstract class AbstractDAO<T> {
    protected EntityManager em;

    public AbstractDAO(EntityManager em) {
        this.em = em;
    }

    public abstract void create(T entity);

    public abstract void delete(T entity);

    public abstract void update(T entity);

    public abstract List<T> getAll();
}

