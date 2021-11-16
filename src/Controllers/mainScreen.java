package Controllers;

import DAO.Appointments.AppointmentDAO;
import Helpers.DBConnection;
import Helpers.validators;
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
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * mainScreen Controller class
 * @author Tim Graham
 */

public class mainScreen implements Initializable {

    private boolean passesValidation;

    private final ObservableList<String> hours = FXCollections.observableArrayList();
    private final ObservableList<String> minutes = FXCollections.observableArrayList();
    private final ObservableList<String> reportTypes = FXCollections.observableArrayList();


    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();


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
    @FXML private DatePicker appointmentStartDateBox, appointmentEndDateBox, dateFilter;
    @FXML private ComboBox<String> startHourBox, startMinuteBox, endHourBox, endMinuteBox;


    @FXML private ComboBox<Contact> contactBox;
    @FXML private ComboBox<Customer> customerBox;
    @FXML private ComboBox<User> userBox;

    @FXML private ComboBox<String> periodSelection, reportTypeBox;
    @FXML private RadioButton allRadio, weekRadio, monthRadio;
    final ToggleGroup period = new ToggleGroup();



    public mainScreen() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dateFilter.setValue(ZonedDateTime.now().toLocalDate());
        generateComboBoxes();
        generateAppointmentsTable();

        impendingAppointment();

        allRadio.setToggleGroup(period);
        weekRadio.setToggleGroup(period);
        monthRadio.setToggleGroup(period);
        allRadio.setSelected(true);

    }

    private void generateComboBoxes() {

        hours.addAll("0","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");

        minutes.addAll("0", "15", "30", "45");

        reportTypes.addAll("Appointments", "Contact Schedule", "User Load");

        startHourBox.setItems(hours);
        startMinuteBox.setItems(minutes);
        endHourBox.setItems(hours);
        endMinuteBox.setItems(minutes);

        contactBox.setItems(Data.getContacts().sorted());
        customerBox.setItems(Data.getCustomers().sorted());
        userBox.setItems(Data.getUsers().sorted());

        reportTypeBox.setItems(reportTypes);

    }

    @FXML private void periodSelector() {
        WeekFields weekFields = WeekFields.of(Locale.US);
        Month selectedMonth = dateFilter.getValue().getMonth();
        int selectedWeek = dateFilter.getValue().get(weekFields.weekOfWeekBasedYear());

        if (allRadio.isSelected()) {
            appointments = Data.getAppointments();
            appointmentsTable.setItems(appointments);
        }
        if (monthRadio.isSelected()) {
            appointments = Data.getAppointments().filtered(a -> a.getStart().toLocalDateTime()
                    .getMonth() == selectedMonth);
            appointmentsTable.setItems(appointments);
        }
        if (weekRadio.isSelected()) {

            int thisWeek = LocalDateTime.now().get(weekFields.weekOfWeekBasedYear());
            appointments = Data.getAppointments().filtered(
                    appointment -> appointment.getStart().toLocalDateTime()
                            .get(weekFields.weekOfWeekBasedYear()) == selectedWeek);
            appointmentsTable.setItems(appointments);

        }
    }




    private void generateAppointmentsTable(){


        allRadio.setSelected(true);
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
        appointmentTypeBox.clear();
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

    // Buttons

    /*
    f.  Write code that generates accurate information in each of the following reports and will display the reports
     in the user interface:
Note: You do not need to save and print the reports to a file or provide a screenshot.

•  the total number of customer appointments by type and month

•  a schedule for each contact in your organization that includes appointment ID, title, type and description, start
date and time, end date and time, and customer ID

•  an additional report of your choice that is different from the two other required reports in this prompt and from
the user log-in date and time stamp that will be tracked in part C
     */
    @FXML
    private void generateReport(Event event) throws Exception {
        if (reportTypeBox.getValue() != null) {
            switch (reportTypeBox.getValue()){
                case "Appointments" : Helpers.reports.appointmentReport();
                case "Contact Schedule" : Helpers.reports.contactSchedule();
                case "User Load": Helpers.reports.userReport();
        }


        }

    }

    @FXML
    private void customerScreen(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/customerScreen.fxml"));
            customerScreen controller = new customerScreen();

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
        if (!appointmentTitleBox.getText().isEmpty() &&
                !appointmentDescriptionBox.getText().isEmpty() &&
                !appointmentLocationBox.getText().isEmpty() &&
                !appointmentTypeBox.getText().isEmpty()  &&
                appointmentStartDateBox.getValue() != null &&
                !startHourBox.getValue().isEmpty() &&
                !startMinuteBox.getValue().isEmpty() &&
                appointmentEndDateBox.getValue() != null &&
                !endHourBox.getValue().isEmpty() &&
                !endMinuteBox.getValue().isEmpty() &&
                customerBox.getValue() != null &&
                userBox.getValue() != null &&
                contactBox.getValue() != null) {

                Timestamp start = Timestamp.valueOf(appointmentStartDateBox.getValue().toString() + " " +
                        startHourBox.getValue() + ":" + startMinuteBox.getValue() + ":00");

                Timestamp end = Timestamp.valueOf(appointmentEndDateBox.getValue().toString() + " " +
                        endHourBox.getValue() + ":" + endMinuteBox.getValue() + ":00");

                passesValidation = validators.addValidator(
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
                    AppointmentDAO.createAppointment(
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



    }

    @FXML
    private void modifyAppointmentButton(Event event) throws Exception {

        if (!appointmentIDBox.getText().isEmpty() &&
                !appointmentTitleBox.getText().isEmpty() &&
                !appointmentDescriptionBox.getText().isEmpty() &&
                !appointmentLocationBox.getText().isEmpty() &&
                !appointmentTypeBox.getText().isEmpty()  &&
                appointmentStartDateBox.getValue() != null &&
                !startHourBox.getValue().isEmpty() &&
                !startMinuteBox.getValue().isEmpty() &&
                appointmentEndDateBox.getValue() != null &&
                !endHourBox.getValue().isEmpty() &&
                !endMinuteBox.getValue().isEmpty() &&
                customerBox.getValue() != null &&
                userBox.getValue() != null &&
                contactBox.getValue() != null) {

            Timestamp start = Timestamp.valueOf(appointmentStartDateBox.getValue().toString() + " " +
                    startHourBox.getValue() + ":" + startMinuteBox.getValue() + ":00");

            Timestamp end = Timestamp.valueOf(appointmentEndDateBox.getValue().toString() + " " +
                    endHourBox.getValue() + ":" + endMinuteBox.getValue() + ":00");

            passesValidation = validators.modifyValidator(
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
    }

    public void deleteAppointment(int appointmentID) throws Exception {
        AppointmentDAO.deleteAppointment(appointmentID);
    }

    @FXML
    private void deleteAppointmentButton(Event event) throws Exception {
        if (appointmentIDBox.getText() != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Are you sure you wish to delete this?");
            confirmation.setContentText("Click to delete " + appointmentTypeBox.getText() +
                            "appointment " + appointmentIDBox.getText());
            Optional<ButtonType> confirm = confirmation.showAndWait();

            if (confirm.get() == ButtonType.OK) {
                deleteAppointment(Integer.parseInt(appointmentIDBox.getText()));
            }

            Data.generateAppointments();
            generateAppointmentsTable();
            clearFields();

        }
    }



    @FXML
    private void exitButton(Event event) throws Exception {
        DBConnection.closeConnection();
        Platform.exit();
    }

    private void impendingAppointment() {
        boolean impAppt = false;

        for (Appointment appointment : Data.getAppointments()) {
            if (Duration.between(LocalDateTime.now(), appointment.getStart().toLocalDateTime()).toMinutes() <= 15 &&
                    Duration.between(LocalDateTime.now(), appointment.getStart().toLocalDateTime()).toMinutes() >= 0) {
                String t = "Appointment within 15 minutes";
                String c = "Appointment " + appointment.getId() + " starts today at " +
                        appointment.getStart();
                popup(t,c);
                impAppt = true;
            }
        }
        if (!impAppt) {
            String t = "No appointments within 15 minutes";
            String c = t;
            popup(t,c);
        }
    }

    private void popup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}




