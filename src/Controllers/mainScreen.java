package Controllers;

import Helpers.DBConnection;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class mainScreen {
    /**
     * Alright, so here we'll see all of our appointments in a tableview
     */

    @FXML private TableView appointmentTable;
    @FXML private TableColumn appointmentIDColumn;
    @FXML private TableColumn appointmentTitleColumn;
    @FXML private TableColumn appointmentDescriptionColumn;
    @FXML private TableColumn appointmentLocationColumn;
    @FXML private TableColumn appointmentContactColumn;
    @FXML private TableColumn appointmentTypeColumn;
    @FXML private TableColumn appointmentStartColumn;
    @FXML private TableColumn appointmentEndColumn;
    @FXML private TableColumn appointmentCustomerIDColumn;
    @FXML private TableColumn appointmentUserIDColumn;


    private void loadTable(){
        // Lets start out by just populating the table

    }

    public static ObservableList<Appointments> getAllAppointments() {

        try{
            String sql = "SELECT * FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
