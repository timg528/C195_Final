package Helpers;

import Models.Appointment;
import Models.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This helper is designed to find appointments for a given customerID.
 */
public class findAppointments {

    /**
     * This is a helper method just to find all the appointments for a specified customer.
     * @param customerID The ID of a customer
     * @return Observable list of appointments for the specified customerID.
     */
    public static ObservableList<Appointment> getAppointmentsbyCustomer(int customerID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        for (Appointment a : Data.getAppointments()) {
            if (a.getCustomer() == customerID) {
                appointments.add(a);
            }
        }
        return appointments;
    }
}
