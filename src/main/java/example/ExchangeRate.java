package example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String exchangedate;
    private String currencyCode;
    private double rate;

    public ExchangeRate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Изменен тип возвращаемого значения с Date на String
    public String getExchangedate() {
        return exchangedate;
    }

    // Изменен тип параметра с Date на String
    public void setExchangedate(String exchangedate) {
        this.exchangedate = exchangedate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


    public ExchangeRate(String exchangedate, String currencyCode, double rate) {
        this.exchangedate = exchangedate;
        this.currencyCode = currencyCode;
        this.rate = rate;
    }
}
