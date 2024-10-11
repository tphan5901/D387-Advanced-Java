package edu.wgu.d387_sample_code.Localization;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
public class ConvertTimezone {
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private static final Map<String, String> timeZones = Map.<String, String>of(
            "US EST", "America/New_York",
            "US MST", "America/Denver",
            "UTC", "UTC"
    );

    public static String getTime() {
        ZonedDateTime currentTime = ZonedDateTime.now();

        StringBuilder timeStringBuilder = new StringBuilder();
        timeZones.forEach((name, zoneId) -> {
            ZonedDateTime zonedTime = currentTime.withZoneSameInstant(ZoneId.of(zoneId));
            timeStringBuilder.append(name).append(": ").append(zonedTime.format(timeFormat)).append(" ");
        });

        return timeStringBuilder.toString().trim();
    }

    public static void main(String[] args) {
        String message = getTime();
        System.out.println("Message: " + message);
    }

}