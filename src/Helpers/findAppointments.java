package Helpers;

import Models.Appointment;
import Models.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class findAppointments {

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
