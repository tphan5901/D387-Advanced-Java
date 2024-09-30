package edu.wgu.d387_sample_code.Localization;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeZoneConverter {

    public static String convertToEST(ZonedDateTime time) {
        return time.withZoneSameInstant(ZoneId.of("America/New_York"))
                .format(DateTimeFormatter.ofPattern("HH:mm")) + " EST";
    }

    public static String convertToMST(ZonedDateTime time) {
        return time.withZoneSameInstant(ZoneId.of("America/Denver"))
                .format(DateTimeFormatter.ofPattern("HH:mm")) + " MST";
    }

    public static String convertToUTC(ZonedDateTime time) {
        return time.withZoneSameInstant(ZoneId.of("UTC"))
                .format(DateTimeFormatter.ofPattern("HH:mm")) + " UTC";
    }
}