package edu.wgu.d387_sample_code.Localization;

import org.springframework.web.bind.annotation.CrossOrigin;
import java.time.*;
import java.time.format.DateTimeFormatter;

@CrossOrigin(origins = "http://localhost:4200")
public class ConvertTimezone {


    public static String getTime() {
        //datetime
        ZonedDateTime currentTime = ZonedDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        ZonedDateTime est = currentTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime mst = currentTime.withZoneSameInstant(ZoneId.of("America/Denver"));
        ZonedDateTime utc = currentTime.withZoneSameInstant(ZoneId.of("UTC"));

        return  "US EST:" + est.format(timeFormat) + " " + mst.format(timeFormat) + "US MST: " + utc.format(timeFormat) + " UTC";
    }

    public static void main(String[] args) {
        String message = getTime();
        System.out.println("Message: " + message);
    }

}