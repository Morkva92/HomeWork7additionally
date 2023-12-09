package example;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrencyApiUtil {
    private static final String API_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=%s&date=%s&json";

    public static List<ExchangeRate> fetchExchangeRates(String currencyCode, String date) throws IOException {
        String apiUrl = String.format(API_URL, currencyCode, date);
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            return parseExchangeRates(response.toString());
        } else {
            System.out.println("Failed to fetch exchange rates. Response code: " + responseCode);
            connection.disconnect();
            return null;
        }
    }

    private static List<ExchangeRate> parseExchangeRates(String jsonResponse) {
        Type listType = new TypeToken<List<ExchangeRate>>() {}.getType();
        JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            ExchangeRate rate = new Gson().fromJson(jsonElement, ExchangeRate.class);
            // Преобразование даты в нужный формат (YYYYMMDD)
            String formattedDate = formatDate(rate.getExchangedate());
            rate.setExchangedate(formattedDate);
            exchangeRates.add(rate);
        }

        return exchangeRates;
    }

    private static String formatDate(String originalDate) {
        try {
            // Парсинг исходной даты в формате "dd.MM.yyyy"
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = inputFormat.parse(originalDate);

            // Преобразование в нужный формат "YYYYMMDD"
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
