package Controllers;

import DAO.Appointments.AppointmentDAO;
import Helpers.DBConnection;
import Models.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static Helpers.appointmentValidator.appointmentValidator;

/**
 * mainScreen Controller class
 * @author Tim Graham
 */

public class mainScreen implements Initializable {

    private boolean passesValidation;
    private final User user;
    //private final static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    private final ObservableList<String> hours = FXCollections.observableArrayList();
    private final ObservableList<String> minutes = FXCollections.observableArrayList();

    /**
     * Alright, so here we'll see all of our appointments in a tableview
     */

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML private TableColumn<Appointment, String> appointmentTitleColumn;
    @FXML private TableColumn<Appointment, String> appointmentDescriptionColumn;
    @FXML private TableColumn<Appointment, String> appointmentLocationColumn;
    @FXML private TableColumn<Appointment, Integer> appointmentContactColumn;
    @FXML private TableColumn<Appointment, String> appointmentTypeColumn;
    @FXML private TableColumn<Appointment, LocalDateTime> appointmentStartColumn;
    @FXML private TableColumn<Appointment, LocalDateTime> appointmentEndColumn;
    @FXML private TableColumn<Appointment, Integer> appointmentCustomerIDColumn;
    @FXML private TableColumn<Appointment, Integer> appointmentUserIDColumn;

    @FXML private TextField appointmentIDBox, appointmentTitleBox, appointmentTypeBox,
            appointmentDescriptionBox, appointmentLocationBox;
    @FXML private DatePicker appointmentStartDateBox, appointmentEndDateBox;
    @FXML private ComboBox<String> startHourBox, startMinuteBox, endHourBox, endMinuteBox;


    @FXML private ComboBox<Contact> contactBox;
    @FXML private ComboBox<Customer> customerBox;
    @FXML private ComboBox<User> userBox;


    public mainScreen(User user) {

        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generateComboBoxes();
        generateAppointmentsTable();
    }

    private void generateComboBoxes() {
        hours.addAll("0","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");

        minutes.addAll("0", "15", "30", "45");

        startHourBox.setItems(hours);
        startMinuteBox.setItems(minutes);
        endHourBox.setItems(hours);
        endMinuteBox.setItems(minutes);

        contactBox.setItems(Data.getContacts().sorted());
        customerBox.setItems(Data.getCustomers().sorted());
        userBox.setItems(Data.getUsers().sorted());


    }

    private void generateAppointmentsTable(){
        appointmentsTable.getItems().clear();
        appointmentsTable.setItems(Data.getAppointments());
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        appointmentUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("user"));

        appointmentsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    if (newValue != null) {
                        appointmentIDBox.setText(String.valueOf(newValue.getId()));
                        appointmentTitleBox.setText(String.valueOf(newValue.getTitle()));
                        appointmentTypeBox.setText(String.valueOf(newValue.getType()));
                        appointmentDescriptionBox.setText(String.valueOf(newValue.getDescription()));
                        appointmentLocationBox.setText(String.valueOf(newValue.getLocation()));
                        appointmentStartDateBox.setValue(newValue.getStart().toLocalDateTime().toLocalDate());
                        appointmentEndDateBox.setValue(newValue.getEnd().toLocalDateTime().toLocalDate());
                        startHourBox.setValue(String.valueOf(newValue.getStart().toLocalDateTime().
                                toLocalTime().getHour()));
                        startMinuteBox.setValue(String.valueOf(newValue.getStart().toLocalDateTime().
                                toLocalTime().getMinute()));
                        endHourBox.setValue(String.valueOf(newValue.getEnd().toLocalDateTime().toLocalTime().
                                getHour()));
                        endMinuteBox.setValue(String.valueOf(newValue.getEnd().toLocalDateTime().toLocalTime().
                                getMinute()));
                        contactBox.setValue(Data.getContact(newValue.getContact()));
                        customerBox.setValue(Data.getCustomer(newValue.getCustomer()));
                        userBox.setValue(Data.getUser(newValue.getUser()));



                    }

                }
        );
    }

    private void clearFields() {
        appointmentsTable.getSelectionModel().clearSelection();
        appointmentIDBox.clear();
        appointmentTitleBox.clear();
        appointmentDescriptionBox.clear();
        appointmentLocationBox.clear();
        appointmentStartDateBox.getEditor().clear();
        appointmentEndDateBox.getEditor().clear();
        startHourBox.setValue(null);
        startMinuteBox.setValue(null);
        endHourBox.setValue(null);
        endMinuteBox.setValue(null);
        contactBox.setValue(null);
        customerBox.setValue(null);
        userBox.setValue(null);
    }

    private void convertToTimestamps() {
        Timestamp start = Timestamp.valueOf(appointmentStartDateBox.getValue().toString() + " " +
                          startHourBox.getValue() + ":" + startMinuteBox.getValue() + ":00");

        Timestamp end = Timestamp.valueOf(appointmentEndDateBox.getValue().toString() + " " +
                        endHourBox.getValue() + ":" + endMinuteBox.getValue() + ":00");


    }





    // Buttons
    @FXML
    private void customerScreen(Event event) {
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
            } catch (IOException e) { e.printStackTrace();
        }

    }

    @FXML
    private void clearButton(Event event) {
        clearFields();

    }

    @FXML
    private void addAppointmentButton(Event event) throws Exception {
        Timestamp start = Timestamp.valueOf(appointmentStartDateBox.getValue().toString() + " " +
                startHourBox.getValue() + ":" + startMinuteBox.getValue() + ":00");

        Timestamp end = Timestamp.valueOf(appointmentEndDateBox.getValue().toString() + " " +
                endHourBox.getValue() + ":" + endMinuteBox.getValue() + ":00");

        passesValidation = appointmentValidator(
                appointmentTitleBox.getText(),
                appointmentDescriptionBox.getText(),
                appointmentLocationBox.getText(),
                appointmentTypeBox.getText(),
                start, end,
                customerBox.getSelectionModel().getSelectedItem().getId(),
                userBox.getSelectionModel().getSelectedItem().getId(),
                contactBox.getSelectionModel().getSelectedItem().getContactID()
        );

        if (passesValidation) {
            AppointmentDAO.addAppointment(
                    appointmentTitleBox.getText(),
                    appointmentDescriptionBox.getText(),
                    appointmentLocationBox.getText(),
                    appointmentTypeBox.getText(),
                    start, end,
                    customerBox.getSelectionModel().getSelectedItem().getId(),
                    userBox.getSelectionModel().getSelectedItem().getId(),
                    contactBox.getSelectionModel().getSelectedItem().getContactID()
            );
            Data.generateAppointments();
            generateAppointmentsTable();
        }


    }

    @FXML
    private void modifyAppointmentButton(Event event) throws Exception {

        Timestamp start = Timestamp.valueOf(appointmentStartDateBox.getValue().toString() + " " +
                startHourBox.getValue() + ":" + startMinuteBox.getValue() + ":00");

        Timestamp end = Timestamp.valueOf(appointmentEndDateBox.getValue().toString() + " " +
                endHourBox.getValue() + ":" + endMinuteBox.getValue() + ":00");

        passesValidation = appointmentValidator(
                appointmentTitleBox.getText(),
                appointmentDescriptionBox.getText(),
                appointmentLocationBox.getText(),
                appointmentTypeBox.getText(),
                start, end,
                customerBox.getSelectionModel().getSelectedItem().getId(),
                userBox.getSelectionModel().getSelectedItem().getId(),
                contactBox.getSelectionModel().getSelectedItem().getContactID()
                );

        if (passesValidation) {
            AppointmentDAO.updateAppointment(
                    Integer.parseInt(appointmentIDBox.getText()),
                    appointmentTitleBox.getText(),
                    appointmentDescriptionBox.getText(),
                    appointmentLocationBox.getText(),
                    appointmentTypeBox.getText(),
                    start, end,
                    customerBox.getSelectionModel().getSelectedItem().getId(),
                    userBox.getSelectionModel().getSelectedItem().getId(),
                    contactBox.getSelectionModel().getSelectedItem().getContactID()
            );
            Data.generateAppointments();
            generateAppointmentsTable();
        }
    }

    @FXML
    private void deleteAppointmentButton(Event event) {

    }

    @FXML
    private void exitButton(Event event) throws Exception {
        DBConnection.closeConnection();
        Platform.exit();
    }

}




