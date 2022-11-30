package notifier.helpers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    public static LocalDateTime getDateTimeFromMilliseconds(long milliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.of("GMT+3"));
    }

    public static String localDateTimeToString(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
    }

    public static String localDateTimeToStringFileName(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern("HH-mm-ss_dd.MM.yyyy"));
    }

    public static String millisecondsToHoursMinSec(long milliseconds){
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

       return  String.format("%d:%02d:%02d", hours, minutes % 60, seconds % 60);
    }

}
