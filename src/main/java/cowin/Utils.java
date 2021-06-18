package cowin;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Ramesh Gupta
 * created on 20/05/21
 */
public class Utils {

    public static List<Header> getHeaderList() {
        List<Header> headerList = new ArrayList<>();

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("Accept-Language", "en-US,en;q=0.5");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Origin", "https://www.cowin.gov.in");
        headers.put("Connection", "keep-alive");
        headers.put("Referer", "https://www.cowin.gov.in/");
        headers.put("TE", "Trailers");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:88.0) Gecko/20100101 Firefox/88.0");
        headers.put("Host", "cdn-api.co-vin.in");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            Header header = new BasicHeader(entry.getKey(), entry.getValue());
            headerList.add(header);
        }
        return headerList;
    }

    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        return format.format(calendar.getTime());
    }

    public static String getCurrentTimeInSecond() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return format.format(calendar.getTime());
    }

    /*
     * Alert you for 10 times before shutting down.
     */
    public static void alertMe() {
        int count = 10;
        while (count > 0) {
            AudioFilePlayer filePlayer = new AudioFilePlayer();
            filePlayer.play(CowinApp.MP3_PATH);
            count--;
        }
    }
}
