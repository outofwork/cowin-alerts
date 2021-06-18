package cowin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Ramesh Gupta
 * created on 20/05/21
 */
public class CowinApp {

    public static final String MP3_PATH = "./remix.mp3";
    public static final int ALERT_INTERVAL = 15;

    public static void main(String[] args) {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        List<CentreDetails> centreDetailsList = new ArrayList<>();

        // Change the Centre here.
        // Add multiple Centre to track.
        centreDetailsList.add(new CentreDetails.CentreDetailsBuilder()
                .setAge(18)
                .setPinCode("273002")
                .setAddress("AIRFORCE")
                .setCentreId(625575)
                .build());

        executorService.scheduleAtFixedRate(new CowinAlert(centreDetailsList)
                , 0
                , ALERT_INTERVAL
                , TimeUnit.SECONDS);
    }
}
