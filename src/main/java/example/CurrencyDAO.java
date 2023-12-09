package example;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;




public class CurrencyDAO extends AbstractDAO<ExchangeRate> implements DAO<ExchangeRate> {
    public CurrencyDAO(EntityManager em) {
        super(em);
    }

    @Override
    public void create(ExchangeRate entity) {
        em.getTransaction().begin();
        try {
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    @Override
    public void delete(ExchangeRate entity) {
        em.getTransaction().begin();
        try {
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    @Override
    public void update(ExchangeRate entity) {
        em.getTransaction().begin();
        try {
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<ExchangeRate> getAll() {
        Query query = em.createQuery("SELECT c FROM ExchangeRate c", ExchangeRate.class);
        return query.getResultList();
    }
}
