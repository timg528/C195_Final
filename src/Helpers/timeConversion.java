package Helpers;

import Models.Data;

import java.sql.Timestamp;

import java.time.ZoneId;

/**
 * This class handles the conversion of timestamps in an arbitrary (UTC, EST, computer-local) timezone and
 * converts them to another arbitrary timezone based on the function called.
 * It passes Timestamps back and forth because I had already written most of the core functionality before handling
 * time changes, and was already comfortable manipulating timestamps.
 */
public class timeConversion {

    /**
     * This method takes a timestamp from the database (in UTC) and
     * converts it to a timestamp in the local timezone.
     * @param timestamp UTC from database passed to the method
     * @return Timestamp in computer-local time
     */
    public static Timestamp toLocal(Timestamp timestamp) {
        Timestamp local = Timestamp.valueOf(timestamp.toLocalDateTime().atZone(
                ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of(
                        Data.getLocalTimezone().getId())).toLocalDateTime());

        return local;
    }

    /**
     * This method takes a timestamp in the local timezone and returns
     * a timestamp in UTC for entry into the database.
     * @param timestamp Computer-local timestamp passed to the method
     * @return Timestamp in UTC time
     */
    public static Timestamp toUTC(Timestamp timestamp) {
        Timestamp UTC = Timestamp.valueOf(timestamp.toLocalDateTime().atZone(
                ZoneId.of(Data.getLocalTimezone().getId())).withZoneSameInstant(
                        ZoneId.of("UTC")).toLocalDateTime());

        return UTC;
    }

    /**
     * This method converts a timestamp in the local timezone and returns
     * a timestamp in EST for use in validation.
     * @param timestamp Computer-local timestamp
     * @return Timestamp based on EST time
     */
    public static Timestamp localToEST(Timestamp timestamp) {
        Timestamp EST = Timestamp.valueOf(timestamp.toLocalDateTime().atZone(
                ZoneId.of(Data.getLocalTimezone().getId())).withZoneSameInstant(
                        ZoneId.of("-05:00")).toLocalDateTime());
        return EST;
    }

    /**
     * This method takes a timestamp in UTC and returns a timestamp in EST.
     * @param timestamp in UTC time
     * @return timestamp in EST time

     */
    public static Timestamp utcToEST(Timestamp timestamp) {
        Timestamp EST = Timestamp.valueOf(timestamp.toLocalDateTime().atZone(
                ZoneId.of("UTC")).withZoneSameInstant(
                ZoneId.of("-05:00")).toLocalDateTime());
        return EST;
    }
}
