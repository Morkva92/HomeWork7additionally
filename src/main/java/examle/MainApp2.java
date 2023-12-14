package examle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.utils.URIBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MainApp2 {
    private static String url = "https://bank.gov.ua/NBU_Exchange/exchange_site?";
    private static String startDate = "date=";
    private static String currencyCode = "&valcode=";
    private static String sortAndOrdersParams = "&sort=exchangedate&order=desc&json";

    public static double getExchangeRateByDate(String inputDate) {
        try {
            URL url = buildUrlWithParams(inputDate, "usd", currencyCode);
            String response = getStringFromResponse(url);

            Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
            List<CurrencyUnit> currencyUnits = gson.fromJson(response, new TypeToken<List<CurrencyUnit>>() {
            }.getType());

            if (!currencyUnits.isEmpty()) {
                CurrencyUnit currencyUnit = currencyUnits.get(0);
                return currencyUnit.getRate();
            } else {
                System.out.println("No data available for the specified date.");
                return 0.0;
            }

        } catch (IOException e) {
            System.out.println("Error fetching exchange rate data: " + e.getMessage());
            return 0.0;
        }
    }


    static URL buildUrlWithParams(String date, String currencyCode, String code) throws MalformedURLException {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.addParameter("start", date);
            uriBuilder.addParameter("end", date);
            uriBuilder.addParameter("valcode", currencyCode);
            uriBuilder.addParameter("sort", "exchangedate");
            uriBuilder.addParameter("order", "desc");
            uriBuilder.addParameter("json", "");
            uriBuilder.addParameter("random", Long.toString(System.currentTimeMillis()));

            URL fullUrl = uriBuilder.build().toURL();
            return fullUrl;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getStringFromResponse(URL url) throws IOException {
        String strBuf;
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        try (InputStream is = http.getInputStream()) {
            byte[] buf = responseBodyToArray(is);
            strBuf = new String(buf, StandardCharsets.UTF_8);
        }
        return strBuf;
    }

    private static byte[] responseBodyToArray(InputStream is) throws IOException {
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