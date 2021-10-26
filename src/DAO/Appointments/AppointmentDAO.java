package DAO.Appointments;

import Helpers.DBConnection;
import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AppointmentDAO {

    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception {
        String sql = "SELECT * from appointments";
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        allAppointments.clear();

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            Timestamp appointmentStart = rs.getTimestamp("Start");
            Timestamp appointmentEnd = rs.getTimestamp("End");
            int appointmentCustomer = rs.getInt("Customer_ID");
            int appointmentUser = rs.getInt("User_ID");
            int appointmentContact = rs.getInt("Contact_ID");

            Appointment a = new Appointment(appointmentID, appointmentTitle, appointmentDescription,
                    appointmentLocation, appointmentType, appointmentStart,
                    appointmentEnd, appointmentCustomer, appointmentUser, appointmentContact);

            allAppointments.add(a);
        }
        return allAppointments;
    }
}
