package edu.wgu.d387_sample_code.Localization;

import org.springframework.web.bind.annotation.CrossOrigin;
import java.time.*;
import java.time.format.DateTimeFormatter;

@CrossOrigin(origins = "http://localhost:4200")
public class TZConvert {


    public static String getTime() {
        ZonedDateTime currentTime = ZonedDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        ZonedDateTime est = currentTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime mst = currentTime.withZoneSameInstant(ZoneId.of("America/Denver"));
        ZonedDateTime utc = currentTime.withZoneSameInstant(ZoneId.of("UTC"));

        String times = est.format(timeFormat) + "NA EST: " + mst.format(timeFormat) + "US MST: " + utc.format(timeFormat) + "UTC";

        return times;
    }

    public static void main(String[] args) {
        System.out.println(getTime());
    }
}