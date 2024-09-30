package edu.wgu.d387_sample_code.Localization;

import org.springframework.web.bind.annotation.CrossOrigin;
import java.time.*;

@CrossOrigin(origins = "http://localhost:4200")
public class TimezoneConvert {

    public static String getTime() {
        ZonedDateTime currentTime = ZonedDateTime.now();

        // Convert the current time to EST, MST, and UTC
        String estTime = TimeZoneConverter.convertToEST(currentTime);
        String mstTime = TimeZoneConverter.convertToMST(currentTime);
        String utcTime = TimeZoneConverter.convertToUTC(currentTime);

        return "Time in EST: " + estTime + ", MST: " + mstTime + ", UTC: " + utcTime;
    }

    public static void main(String[] args) {
        System.out.println(getTime());
    }
}