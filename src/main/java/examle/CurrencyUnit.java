package examle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CurrencyUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date exchangedate;
    private String cc;
    private String txt;
    private double rate;

    public CurrencyUnit() {
    }

    @Override
    public String toString() {
        return "dao.CurrencyUnitDao.CurrencyUnit{" +
                "exchangedate='" + exchangedate + '\'' +
                ", cc='" + cc + '\'' +
                ", txt='" + txt + '\'' +
                ", rate=" + rate +
                '}';
    }
}