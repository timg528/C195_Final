package DAO.Appointments;

import Helpers.DBConnection;
import Helpers.timeConversion;
import Models.Appointment;
import Models.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;


public class AppointmentDAO {


    /**
     * This handles the creation of appointments in the database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customer_id
     * @param user_id
     * @param contact_id
     * @throws Exception
     */
    public static void createAppointment(String title, String description, String location, String type,
                                      Timestamp start, Timestamp end, int customer_id, int user_id,
                                      int contact_id)
            throws Exception {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, " +
                "Contact_ID)" +
                "VALUES (?,?,?,?,?,?," + // Title through End
                "CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?,?,?,?)";

        Timestamp appointmentStart = timeConversion.toUTC(start);
        Timestamp appointmentEnd = timeConversion.toUTC(end);

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, appointmentStart);
        ps.setTimestamp(6, appointmentEnd);
        ps.setInt(7, Data.getCurrentUser());
        ps.setInt(8, Data.getCurrentUser());
        ps.setInt(9, customer_id);
        ps.setInt(10,user_id);
        ps.setInt(11, contact_id);

        ps.executeUpdate();

    }

    /**
     * This handles reading the database to get all appointments
     * @return
     * @throws Exception
     */
    public static ObservableList<Appointment> getAllAppointments() throws Exception {
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


            Timestamp start = timeConversion.toLocal(appointmentStart);
            Timestamp end = timeConversion.toLocal(appointmentEnd);


            Appointment a = new Appointment(appointmentID, appointmentTitle, appointmentDescription,
                    appointmentLocation, appointmentType, start,
                    end, appointmentCustomer, appointmentUser, appointmentContact);

            allAppointments.add(a);
        }
        return allAppointments;
    }


    /**
     * This handles updating an appointment of the specified ID with the relevant data.
     * @param id
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customer_id
     * @param user_id
     * @param contact_id
     * @throws Exception
     */
    public static void updateAppointment(int id, String title, String description, String location,
                                         String type, Timestamp start, Timestamp end, int customer_id,
                                         int user_id, int contact_id )
            throws Exception {

        Timestamp appointmentStart = timeConversion.toUTC(start);
        Timestamp appointmentEnd = timeConversion.toUTC(end);

        String sql = "UPDATE appointments SET " +
                "Title = ?, " +
                "Description = ?, " +
                "Location = ?," +
                "Type = ?," +
                "Start = ?, " +
                "End = ?, " +
                "Last_Update = CURRENT_TIMESTAMP, " +
                "Last_Updated_By = ?, " +
                "Customer_ID = ?, " +
                "User_ID = ?, " +
                "Contact_ID = ? " +
                "WHERE Appointment_ID = ?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, appointmentStart);
        ps.setTimestamp(6, appointmentEnd);
        ps.setInt(7, Data.getCurrentUser());
        ps.setInt(8, customer_id);
        ps.setInt(9, user_id);
        ps.setInt(10, contact_id);
        ps.setInt(11, id);

        ps.executeUpdate();
    }

    /**
     * This handles deleting the appointment from the database, specified by the appointmentID.
     * @param appointmentID
     * @throws Exception
     */
    public static void deleteAppointment(Integer appointmentID) throws Exception {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, appointmentID);

        ps.executeUpdate();
    }
}
