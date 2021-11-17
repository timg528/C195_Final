package Helpers;

import Models.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class timeConversion {

    /**
     * This method takes a timestamp from the database (in UTC) and
     * converts it to a timestamp in the local timezone.
     * @param timestamp
     * @return
     * @throws Exception
     */
    public static Timestamp toLocal(Timestamp timestamp) throws Exception {
        Timestamp local = Timestamp.valueOf(timestamp.toLocalDateTime().atZone(
                ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of(
                        Data.getLocalTimezone().getId())).toLocalDateTime());

        return local;
    }

    /**
     * This method takes a timestamp in the local timezone and returns
     * a timestamp in UTC for entry into the database.
     * @param timestamp
     * @return
     * @throws Exception
     */
    public static Timestamp toUTC(Timestamp timestamp) throws Exception {
        Timestamp UTC = Timestamp.valueOf(timestamp.toLocalDateTime().atZone(
                ZoneId.of(Data.getLocalTimezone().getId())).withZoneSameInstant(
                        ZoneId.of("UTC")).toLocalDateTime());

        return UTC;
    }

    /**
     * This method converts a timestamp in the local timezone and returns
     * a timestamp in EST for use in validation.
     * @param timestamp
     * @return
     * @throws Exception
     */
    public static Timestamp localToEST(Timestamp timestamp) throws Exception {
        Timestamp EST = Timestamp.valueOf(timestamp.toLocalDateTime().atZone(
                ZoneId.of(Data.getLocalTimezone().getId())).withZoneSameInstant(
                        ZoneId.of("-05:00")).toLocalDateTime());
        return EST;
    }

    /**
     * This method takes a timestamp in UTC and returns a timestamp in EST.
     * @param timestamp
     * @return
     * @throws Exception
     */
    public static Timestamp utcToEST(Timestamp timestamp) throws Exception {
        Timestamp EST = Timestamp.valueOf(timestamp.toLocalDateTime().atZone(
                ZoneId.of("UTC")).withZoneSameInstant(
                ZoneId.of("-05:00")).toLocalDateTime());
        return EST;
    }
}
