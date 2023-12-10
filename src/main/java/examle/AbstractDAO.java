package examle;
import javax.persistence.EntityManager;


public abstract class AbstractDAO<T> implements DAO<T> {
    protected EntityManager em;

    public AbstractDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(T entity) {
        em.getTransaction().begin();
        try {
            em.persist(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    @Override
    public void delete(T entity) {
        em.getTransaction().begin();
        try {
            em.remove(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
        }

    }
    @Override
    public void update(T entity){
        em.getTransaction().begin();
        try {
            em.persist(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
        }
    }
}