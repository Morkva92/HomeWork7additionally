package examle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainApp {
    private static String url = "https://bank.gov.ua/NBU_Exchange/exchange_site?";
    private static String startDate = "start=";
    private static String finishDate = "&end=";
    private static String currencyCode = "&valcode=";
    private static String sortAndOrdersParams = "&sort=exchangedate&order=desc&json";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
    private static EntityManager em = emf.createEntityManager();


    public static void getAverageCurrency() {
        saveStatisticToBd(getCurrencyStatistic());
        TypedQuery<Double> query = em.createQuery(
                "SELECT AVG(e.rate) FROM CurrencyUnit e", Double.class);
        Double averageSalary = query.getSingleResult();
        String formattedString = String.format("Average currency is : %.2f", averageSalary);
        System.out.println(formattedString);
    }
    public static void getMaxCurrencyRate() {
        List<CurrencyUnit> currencyUnits = getCurrencyStatistic();

        Optional<CurrencyUnit> maxRateCurrency = currencyUnits.stream()
                .max(Comparator.comparing(CurrencyUnit::getRate));

        if (maxRateCurrency.isPresent()) {
            System.out.println("Максимальный курс валюты: " + maxRateCurrency.get().getRate());
        } else {
            System.out.println("Нет данных за указанный период.");
        }
    }



    private static List<CurrencyUnit> getCurrencyStatistic() {
        Type itemsListType = new TypeToken<List<CurrencyUnit>>() {
        }.getType();
        String response = null;
        try {
            response = getStringFromResponse(getUrlWithParams());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        List<CurrencyUnit> currencyUnits = gson.fromJson(response, itemsListType);
        return currencyUnits;
    }

    private static void saveStatisticToBd(List<CurrencyUnit> list) {
        DAO<CurrencyUnit> cud = new CurrencyUnitDao(em);
        for (CurrencyUnit currencyUnit : list) {
            cud.create(currencyUnit);
        }
    }

    private static URL getUrlWithParams() throws MalformedURLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите начальную дату периода (yyyyMMdd):");
        startDate += scanner.nextLine();
        System.out.println("Введите конечную дату периода (yyyyMMdd):");
        finishDate += scanner.nextLine();
        currencyCode += "usd";
        url += (startDate + finishDate + currencyCode + sortAndOrdersParams);
        return new URL(url);
    }


    public static String getStringFromResponse(URL url) throws IOException {
        String strBuf;
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        InputStream is = http.getInputStream();
        try {
            byte[] buf = MainApp.responseBodyToArray(is);
            strBuf = new String(buf, StandardCharsets.UTF_8);

        } finally {
            is.close();
        }
        return strBuf;
    }

    static byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }


}