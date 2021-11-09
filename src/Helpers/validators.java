package Helpers;

import javafx.scene.control.Alert;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class validators {

    public static boolean appointmentValidator(String title, String description, String location,
                                          String type, Timestamp start, Timestamp end, int customer_id,
                                          int user_id, int contact_id ) throws Exception {
        LocalDateTime apptStart = timeConversion.localToEST(start).toLocalDateTime();
        LocalDateTime apptEnd = timeConversion.localToEST(end).toLocalDateTime();

        LocalTime officeOpen = LocalTime.of(8,00);
        LocalTime officeClose = LocalTime.of(22,00);

        if (apptStart.isAfter(apptEnd)) {
            String t = "Time travel does not yet exist!";
            String c = "The appointment's start time is after it's end!";
            popup(t, c);
            return false;
        }

        if (apptStart.toLocalTime().isBefore(officeOpen) ||
                apptStart.toLocalTime().isAfter(officeClose)) {
            String t = "Appointment Start time is invalid!";
            String c = "The start time is outside of office hours!";
            popup(t,c);
            return false;
        }

        if (apptEnd.toLocalTime().isBefore(officeOpen) ||
                apptEnd.toLocalTime().isAfter(officeClose)) {
            String t = "Appointment End time is invalid!";
            String c = "The end time is outside of office hours!";
            popup(t,c);
            return false;
        }

        if (!apptStart.toLocalDate().isEqual(apptEnd.toLocalDate())) {
            String t = "Appointment ends on a different day!";
            String c = "The appointment's end is not the same day as it's start!";
            popup(t,c);
            return false;
        }

        return true;
    }

    private static void popup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
