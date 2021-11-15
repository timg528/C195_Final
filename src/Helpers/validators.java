package Helpers;

import Models.Appointment;
import Models.Data;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import java.sql.Timestamp;
import java.time.DayOfWeek;
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

        // Validates that the start occurs before the end
        if (apptStart.isAfter(apptEnd)) {
            String t = "Time travel does not yet exist!";
            String c = "The appointment's start time is after it's end!";
            popup(t, c);
            return false;
        }

        // Validates that the appointment begins during office hours
        if (apptStart.toLocalTime().isBefore(officeOpen) ||
                apptStart.toLocalTime().isAfter(officeClose)) {
            String t = "Appointment Start time is invalid!";
            String c = "The start time is outside of office hours!";
            popup(t,c);
            return false;
        }

        // Validates that the appointment ends during office hours
        if (apptEnd.toLocalTime().isBefore(officeOpen) ||
                apptEnd.toLocalTime().isAfter(officeClose)) {
            String t = "Appointment End time is invalid!";
            String c = "The end time is outside of office hours!";
            popup(t,c);
            return false;
        }

        // Validates that the appointment starts and ends on the same day EST (Not necessary)
        if (!apptStart.toLocalDate().isEqual(apptEnd.toLocalDate())) {
            String t = "Appointment ends on a different day!";
            String c = "The appointment's end is not the same day as it's start!";
            popup(t,c);
            return false;
        }

        // Validates that the appointment doesn't start on a weekend
        if (apptStart.toLocalDate().getDayOfWeek() == DayOfWeek.SATURDAY ||
                apptStart.toLocalDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
            String t = "The office is not open on weekends!";
            String c = "The appointment cannot be scheduled for a weekend";
            popup(t,c);
            return false;
        }

        return true;
    }

    public static boolean addValidator(String title, String description, String location,
                                       String type, Timestamp start, Timestamp end, int customer_id,
                                       int user_id, int contact_id) throws Exception {

        LocalDateTime apptStart = timeConversion.localToEST(start).toLocalDateTime();
        LocalDateTime apptEnd = timeConversion.localToEST(end).toLocalDateTime();

        if(appointmentValidator(title, description, location, type, start, end,
                customer_id, user_id, contact_id)){
            FilteredList<Appointment> customerAppointments = Data.getAppointments().filtered(
                    appointment -> appointment.getCustomer() == customer_id);

            for (Appointment a : customerAppointments) {
                LocalDateTime aStart = timeConversion.localToEST(a.getStart()).toLocalDateTime();
                LocalDateTime aEnd = timeConversion.localToEST(a.getEnd()).toLocalDateTime();

                if(!conflictChecker(apptStart, apptEnd, aStart, aEnd, a.getId())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean modifyValidator(int id, String title, String description, String location,
                                            String type, Timestamp start, Timestamp end, int customer_id,
                                            int user_id, int contact_id ) throws Exception {
        LocalDateTime apptStart = timeConversion.localToEST(start).toLocalDateTime();
        LocalDateTime apptEnd = timeConversion.localToEST(end).toLocalDateTime();

        LocalTime officeOpen = LocalTime.of(8,00);
        LocalTime officeClose = LocalTime.of(22,00);


        // Passes the appointment info to the appointmentValidator to perform the checks there
        if(!appointmentValidator(title, description, location, type, start, end,
                customer_id, user_id, contact_id)) {
            return false;
        }

        FilteredList<Appointment> customerAppointments = Data.getAppointments().filtered(
                appointment -> appointment.getCustomer() == customer_id);

        for (Appointment a : customerAppointments) {
            LocalDateTime aStart = timeConversion.localToEST(a.getStart()).toLocalDateTime();
            LocalDateTime aEnd = timeConversion.localToEST(a.getEnd()).toLocalDateTime();
            if (a.getId() != id){
                if(!conflictChecker(apptStart, apptEnd, aStart, aEnd, a.getId())){
                    return false;
                }
            }

        }
        return true;
    }

    public static boolean conflictChecker(LocalDateTime apptStart, LocalDateTime apptEnd,
                                          LocalDateTime aStart, LocalDateTime aEnd, int aID) throws Exception {

        // Check if the start and end times are the same
        if (apptStart.equals(aStart) && apptEnd.equals(aEnd)) {
            String t = "Overlap detected!";
            String c = "This appointment starts and ends at the same time as appointment " + aID;
            popup(t,c);
            return false;
        }

        // Now we check that the appStart is after aStart but before aEnd
        if (apptStart.isAfter(aStart) && apptStart.isBefore(aEnd)) {
            String t = "Overlap detected!";
            String c = "The start of this appointment overlaps appointment " + aID;
            popup(t,c);
            return false;
        }

        // Now to check that the appEnd is after aStart but before aEnd
        if (apptEnd.isAfter(aStart) && apptEnd.isBefore(aEnd)) {
            String t = "Overlap detected!";
            String c = "The end of this appointment overlaps appointment " + aID;
            popup(t,c);
            return false;
        }

        // Now to check that we're not overlapping appointment a
        if (apptStart.isBefore(aStart) && apptEnd.isAfter(aEnd)) {
            String t = "Overlap detected!";
            String c = "This appointment completely overlaps appointment " + aID;
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
