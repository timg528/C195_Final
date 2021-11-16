package Helpers;

import Models.Appointment;
import Models.Data;

import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class reports {


    // This needs to return the total number of appointments by Type and by Month
    public static void appointmentReport() {
        Set<String> types = new HashSet<>();
        Set<Month> months = new HashSet<>();

        HashMap<String, Integer> numberByType = new HashMap<>();
        HashMap<Month, Integer> numberByMonth = new HashMap<>();

        for (Appointment appointment: Data.getAppointments()) {
            types.add(appointment.getType());
            months.add(appointment.getStart().toLocalDateTime().getMonth());
        }


        for (String type: types) {
            int count = 0;
            for (Appointment appointment: Data.getAppointments()) {

                if (type.equals(appointment.getType())) {
                    count++;
                }
                numberByType.put(type, count);
            }
        }

        for (Month month: months) {
            int count = 0;
            for (Appointment appointment: Data.getAppointments()) {
                if (month.equals(appointment.getStart().toLocalDateTime().getMonth())) {
                    count++;
                }
                numberByMonth.put(month, count);
            }
        }

        // Testing
        numberByMonth.forEach((k,v) -> System.out.println(k + ": " + v));
        System.out.println("########");
        numberByType.forEach((k,v) -> System.out.println(k + ": " + v));
    }
    // This needs to return a schedule for each contact which includes:
    // * Appointment ID, Title, Type, Description, Start/End date time, and Customer_ID
    public static void contactSchedule() {

    }

    // This needs to return the number of appointments for each user
    public static void userReport() {

    }
}
