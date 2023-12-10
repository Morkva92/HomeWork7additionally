package examle;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CurrencyUnitDao extends AbstractDAO<CurrencyUnit>{
    public CurrencyUnitDao(EntityManager em) {
        super(em);
    }

    @Override
    public List<CurrencyUnit> getAll() {
        Query query = em.createQuery("SELECT c FROM CurrencyUnit c", CurrencyUnit.class);
        List<CurrencyUnit> list = (List<CurrencyUnit>) query.getResultList();
        return list;
    }
}