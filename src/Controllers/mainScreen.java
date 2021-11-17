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


    /**
     * The mainscreen class. I ended up leaving it empty and using other classes, but retained it just in case I might need it.
     */
    public mainScreen() {
    }

    /**
     * This initializes the main screen by setting the toggles and calling other methods.
     * @param url
     * @param resourceBundle
     */
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

    /**
     * I keep the combobox initialization in this method to keep it organized.
     */
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

    /**
     * This handles selecting whether all appointments or appointments by month or week are shown.
     * <p><b>
     *     The lambda expressions here are used in a stream filter to convert the appointment start timestamp to
     *     a LocalDateTime and compare the month/week to the one selected.
     * </b>
     * </p>
     */
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


    /**
     * This handles filling the tableview with appointments
     * <p><b>
     *     It also features a Lambda to handle filling in the text fields and combo boxes when the user
     *     selects an appointment in the tableview.
     * </b></p>
     */
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

    /**
     * This handles the act of clearing the textfields and combo boxes. This is separated out from the Clear button
     * because other functions like creating, updating, and deleting should also clear out the selection and fields.
     */
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

    /**
     * This method checks that the user selected a reportType in the appropriate combobox, then opens the reportScreen
     * with the value in that combo box.
     * @param event The user clicking the button
     * @throws Exception In case something breaks
     */
    @FXML
    private void generateReport(Event event) throws Exception {
        if (reportTypeBox.getValue() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/reportScreen.fxml"));
            reportScreen controller = new reportScreen(reportTypeBox.getValue());

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } else {
            String t = "No report type selected";
            String c = "Please select a report type and try again";
            popup(t,c);
        }




    }

    /**
     * This opens the customer screen should the user wish to view or work with customer data
     * @param event
     */
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

    /**
     * This is the button to clear the selection and text fields and combo boxes
     * @param event
     */
    @FXML
    private void clearButton(Event event) {
        clearFields();

    }

    /**
     * This method listens for a user click, then verifies that the appropriate fields and boxes are filled in,
     * then it sends the information to a validator to ensure the appointment times are valid. If that passes,
     * this method then passes the information to the appointmentDAO to create the appointment. After that, it calls
     * the methods to update the Data object and refresh the table view.
     * @param event
     * @throws Exception
     */
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
            } else {
            String t = "Not all information filled in";
            String c = "Please double check the fields and dropdowns and try again";
            popup(t,c);
        }



    }
    /**
     * This method listens for a user click, then verifies that the appropriate fields and boxes are filled in,
     * then it sends the information to a validator to ensure the appointment times are valid. If that passes,
     * this method then passes the information to the appointmentDAO to update the appointment. After that, it calls
     * the methods to update the Data object and refresh the table view.
     * @param event
     * @throws Exception
     */
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
        } else {
            String t = "No appointment selected";
            String c = "Please select an appointment to update";
            popup(t,c);
        }
    }

    /**
     * This listens for a button click, if the appointmentIDBox has text in it, it'll throw up a confirmation
     * box asking the user if they're sure they want to delete the appointment. If the user answers in the affirmative,
     * the method calls the AppointmentDAO.deleteAppointment to delete the appointment. Then it regenerates the
     * Data object's appointment list, refreshes the tableview, and clears the selection and fields.
     * @param event
     * @throws Exception
     */
    @FXML
    private void deleteAppointmentButton(Event event) throws Exception {
        if (appointmentIDBox.getText() != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Are you sure you wish to delete this?");
            confirmation.setContentText("Click to delete " + appointmentTypeBox.getText() +
                            "appointment " + appointmentIDBox.getText());
            Optional<ButtonType> confirm = confirmation.showAndWait();

            if (confirm.get() == ButtonType.OK) {
                AppointmentDAO.deleteAppointment(Integer.parseInt(appointmentIDBox.getText()));
            }

            Data.generateAppointments();
            generateAppointmentsTable();
            clearFields();

        } else {
            String t = "No appointment selected";
            String c = "Select an appointment to delete";
            popup(t,c);
        }
    }


    /**
     * This button closes the database connection and exits the program
     * @param event
     * @throws Exception
     */
    @FXML
    private void exitButton(Event event) throws Exception {
        DBConnection.closeConnection();
        Platform.exit();
    }

    /**
     * This handles the logic of checking for an impending appointment - one that starts within 15 minutes of the user
     * accessing the mainscreen.
     */
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

    /**
     * This handles most of the popups in the mainscreen.
     * @param title The string of text to display as the title
     * @param content The string of text to display as the content
     */
    private void popup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}




