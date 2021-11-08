package Helpers;

import javafx.scene.control.Alert;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class appointmentValidator {

    public static boolean appointmentValidator(String title, String description, String location,
                                          String type, Timestamp start, Timestamp end, int customer_id,
                                          int user_id, int contact_id ) throws Exception {
        LocalDateTime apptstart = start.toLocalDateTime();
        LocalDateTime apptend = end.toLocalDateTime();

        if (apptstart.isAfter(apptend)) {
            String t = "Appointment Start/End invalid!";
            String c = "The appointment's start time is after it's end!";
            popup(t, c);
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
