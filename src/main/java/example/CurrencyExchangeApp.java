package example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CurrencyExchangeApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
        EntityManager em = emf.createEntityManager();

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите код валюты (например, EUR): ");
            String currencyCode = scanner.nextLine();

            System.out.print("Введите дату (в формате YYYYMMDD): ");
            String date = scanner.nextLine();

            CurrencyDAO currencyDAO = new CurrencyDAO(em);

            // Получаем данные с API и сохраняем в базу
            List<ExchangeRate> exchangeRates = CurrencyApiUtil.fetchExchangeRates(currencyCode, date);
            for (ExchangeRate rate : exchangeRates) {
                currencyDAO.create(rate);
            }

            // Вычисляем средний курс
            double averageRate = calculateAverageRate(currencyDAO.getAll());
            System.out.println("Средний курс за период: " + averageRate);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static double calculateAverageRate(List<ExchangeRate> exchangeRates) {
        if (exchangeRates.isEmpty()) {
            return 0.0;
        }

        double totalRate = 0.0;
        for (ExchangeRate rate : exchangeRates) {
            totalRate += rate.getRate();
        }

        return totalRate / exchangeRates.size();
    }
}

