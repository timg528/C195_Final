package Controllers;

import Models.Appointment;

import Helpers.DBConnection;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * mainScreen Controller class
 * @author Tim Graham
 */

public class mainScreen implements Initializable {

    private final User user;
    private final static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * Alright, so here we'll see all of our appointments in a tableview
     */

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML private TableColumn<Appointment, String> appointmentTitleColumn;
    @FXML private TableColumn<Appointment, String> appointmentDescriptionColumn;
    @FXML private TableColumn<Appointment, String> appointmentLocationColumn;
    @FXML private TableColumn<Appointment, String> appointmentContactColumn;
    @FXML private TableColumn<Appointment, String> appointmentTypeColumn;
    @FXML private TableColumn<Appointment, LocalDateTime> appointmentStartColumn;
    @FXML private TableColumn<Appointment, LocalDateTime> appointmentEndColumn;
    @FXML private TableColumn<Appointment, Integer> appointmentCustomerIDColumn;
    @FXML private TableColumn<Appointment, Integer> appointmentUserIDColumn;

    @FXML private Button addAppointmentButton;
    @FXML private Button modifyAppointmentButton;
    @FXML private Button addCustomerButton;
    @FXML private Button modifyCustomerButton;
    @FXML private Button exitButton;

    public mainScreen(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllAppointments();
        generateAppointmentsTable();

    }

    private void generateAppointmentsTable(){
        appointmentsTable.setItems(appointments);
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        //appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact")); // Need to make call to contacts table
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        appointmentUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
    }

    public static ObservableList<Appointment> getAllAppointments() {
        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
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

                Appointment A = new Appointment(appointmentID, appointmentTitle, appointmentDescription,
                        appointmentLocation, appointmentType, appointmentStart,
                        appointmentEnd, appointmentCustomer, appointmentUser);

                appointments.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) { e.printStackTrace(); }
        return appointments;
    }

    @FXML private void customerScreen(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/customerScreen.fxml"));
            customerScreen controller = new customerScreen(user);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            } catch (IOException e) { e.printStackTrace(); }

        }
}




