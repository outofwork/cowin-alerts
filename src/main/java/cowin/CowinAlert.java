package cowin;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * @author Ramesh Gupta
 * created on 20/05/21
 */
public class CowinAlert implements Runnable {

    private final List<CentreDetails> centreDetailList;

    public CowinAlert(List<CentreDetails> centreDetailList) {
        this.centreDetailList = centreDetailList;
    }

    @Override
    public void run() {
        try {
            for (CentreDetails centre : centreDetailList) {
                cowinAlert(centre);
            }
        } catch (Exception e) {
            // Some Exception Occurred.
        }
    }

    public void cowinAlert(CentreDetails centre) throws IOException {

        String currentDate = Utils.getCurrentDate();

        URIBuilder builder = new URIBuilder()
                .setScheme("https")
                .setHost("cdn-api.co-vin.in")
                .setPath("/api/v2/appointment/sessions/public/calendarByPin")
                .addParameter("pincode", centre.getPinCode())
                .addParameter("date", currentDate);

        String apiResponse = httpGet(builder.toString(), Utils.getHeaderList());
        if (isSlotAvailable(apiResponse, centre)) {
            System.out.println("Slot Available");
            Utils.alertMe();
        } else {
            System.out.println("Oops No Slot Available for " + centre.getAge() + " - " + centre.getPinCode() + "-" + centre.getAddress() + " - " + Utils.getCurrentTimeInSecond());
        }
    }


    public static String httpGet(String httpUrl, List<Header> headerList) {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .build();

        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build()) {

            HttpGet httpget = new HttpGet(httpUrl);
            for (Header header : headerList) {
                httpget.addHeader(header);
            }
            CloseableHttpResponse getResponse = httpClient.execute(httpget);

            if (getResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = getResponse.getEntity();
                String response = EntityUtils.toString(entity, "UTF-8");
                getResponse.close();
                return response;
            }
        } catch (IOException e) {
            // Ignore Exception.
        }
        return "";
    }

    public static boolean isSlotAvailable(String apiResponse, CentreDetails centre) {
        boolean slotAvailable = false;
        JSONObject jsonObject = new JSONObject(apiResponse);
        JSONArray centreList = (JSONArray) jsonObject.get("centers");

        for (Object centreObject : centreList) {
            JSONObject currentCentre = (JSONObject) centreObject;
            if (currentCentre.get("address").toString().contains(centre.getAddress()) ||
                    (Integer) currentCentre.get("center_id") == centre.getCentreId()) {
                JSONArray sessionList = (JSONArray) currentCentre.get("sessions");

                for (Object sessionObject : sessionList) {
                    JSONObject session = (JSONObject) sessionObject;
                    if ((Integer) session.get("min_age_limit") == centre.getAge()
                            && (Integer) session.get("available_capacity") > 0) {
                        System.out.println("Centre Name: " + currentCentre.get("address"));
                        System.out.println("Date: " + session.get("date"));
                        System.out.println("Available Dose: " + session.get("available_capacity"));
                        slotAvailable = true;
                    }
                }
            }
        }
        return slotAvailable;
    }
}
