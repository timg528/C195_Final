package Controllers;

import DAO.Customers.CustomerDAO;
import Models.*;
import Helpers.findAppointments;
import DAO.Appointments.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This class handles creating, reading, updating, and deleting customers.
 */
public class customerScreen implements Initializable {

    @FXML private Button createCustomerButton;
    @FXML private Button cancelButton;

    @FXML private TableView<Customer> customersTable;
    @FXML private TableColumn<Customer, Integer> customerIDColumn;
    @FXML private TableColumn<Customer, String> customerNameColumn;
    @FXML private TableColumn<Customer, String> customerAddressColumn;
    @FXML private TableColumn<Customer, String> customerPostCodeColumn;
    @FXML private TableColumn<Customer, String> customerPhoneColumn;




    @FXML private TextField customerIDBox;
    @FXML private TextField customerNameBox;
    @FXML private TextField customerAddressBox;
    @FXML private TextField customerPhoneBox;
    @FXML private TextField postCodeBox;
    @FXML private ComboBox<Country> countryBox;
    @FXML private ComboBox<Division> stateBox;



    public customerScreen() {}


    /**
     * This initializes the page by running the generateCustomersTable and loadCountryBox methods.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        generateCustomersTable();
        loadCountryBox();


    }

    /**
     * This method handles filling the customer tableview with customer data. Users can select
     * dustomers in order to modify or delete them, or use their data as a template for creating a new
     * customer in the database.
     * <p><b>
     * This method features a Lambda function that adds a listener to the tableview so that
     * when a user clicks on a customer, the text and combo boxes are filled out using that customer's
     * data.
     * </b>
     * </p>
     */
    private void generateCustomersTable(){
        customersTable.getSelectionModel().clearSelection();
        customersTable.setItems(Data.getCustomers());
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postal"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        /**
         * Lambda Expression to dynamically fill in the text fields whenever a row
         * in the tableview is selected
         */

        customersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    if (newValue != null) {
                        customerIDBox.setText(String.valueOf(newValue.getId()));
                        customerNameBox.setText(newValue.getName());
                        customerAddressBox.setText(newValue.getAddress());
                        postCodeBox.setText(newValue.getPostal());
                        customerPhoneBox.setText(newValue.getPhone());
                        stateBox.setValue(Data.getDivision(newValue.getDivision()));
                        countryBox.setValue(Data.getCountry(
                                Data.getDivision(
                                        newValue.getDivision()).getCountryID()));
                    }
                }

        );
    }


    /**
     * This handles loading the country box from the Data model
     */
    private void loadCountryBox() {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        countries = Data.getCountries();

        countryBox.setItems(countries);
    }

    /**
     * This handles filtering the options available in the division box through a filtered stream
     */
    @FXML private void selectDivision() {
        if (countryBox.getSelectionModel().getSelectedItem() != null) {
            int countryID = countryBox.getSelectionModel().getSelectedItem().getCountryID();
            stateBox.setItems(Data.getDivisions()
                    .filtered(d -> d.getCountryID() == countryID)
                    .sorted());
        }

    }

    /**
     * This method handles clearing out the text and combo boxes. It was necessary to separate it out from the
     * clearButton as there are other actions which require the fields to clear.
     */
    private void clearFields() {
        customersTable.getSelectionModel().clearSelection();
        customerIDBox.clear();
        customerNameBox.clear();
        customerAddressBox.clear();
        postCodeBox.clear();
        customerPhoneBox.clear();
        stateBox.setValue(null);
        countryBox.setValue(null);
    }



    // Button Actions

    /**
     * This handles returning to the main page. I made it separate in case there were any actions
     * other than clicking the cancel button that would require sending the user back to main.
     * @param event
     */
    private void returnToMain(Event event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/mainScreen.fxml"));
            mainScreen controller = new mainScreen(false);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {e.printStackTrace();}
    }


    // On-screen Buttons

    /**
     * This allows the user of the program to leave the customer screen
     * @param event
     */
    @FXML private void cancelButton(ActionEvent event) {
        returnToMain(event);
    }

    /**
     * This method first checks that all of the boxes are filled, except for the customerID box,
     * then passes those values to the CustomerDAO.createCustomer which creates the customer in the database.
     * It then calls the Data.generateCustomers method to refresh the Data object and refreshes the tableview.
     * @param event
     * @throws Exception
     */
    @FXML private void createButton(ActionEvent event) throws Exception {
        if (!customerNameBox.getText().isEmpty() &&
        !customerAddressBox.getText().isEmpty() &&
        !postCodeBox.getText().isEmpty() &&
        !customerPhoneBox.getText().isEmpty() &&
        stateBox.getValue() != null &&
        countryBox.getValue() != null) {
            int divisionID = stateBox.getSelectionModel().getSelectedItem().getDivisionID();

            CustomerDAO.createCustomer(customerNameBox.getText(), customerAddressBox.getText(),
                    postCodeBox.getText(),customerPhoneBox.getText(),
                    divisionID);
            customersTable.getSelectionModel().clearSelection();
            Data.generateCustomers();
            generateCustomersTable();

        }


    }

    /**
     * This updates the selected customer after verifying that all the fields and boxes have values.
     * Once it's validated, it calls the CustomerDAO.updateCustomer with the values, then calls
     * Data.generateCustomers and refreshes the tableview.
     * @param event
     * @throws Exception
     */
    @FXML private void updateButton(ActionEvent event) throws Exception {
        if (!customerIDBox.getText().isEmpty() &&
                !customerNameBox.getText().isEmpty() &&
                !customerAddressBox.getText().isEmpty() &&
                !postCodeBox.getText().isEmpty() &&
                !customerPhoneBox.getText().isEmpty() &&
                stateBox.getValue() != null &&
                countryBox.getValue() != null) {
            int divisionID = stateBox.getSelectionModel().getSelectedItem().getDivisionID();

            CustomerDAO.updateCustomer(Integer.parseInt(customerIDBox.getText()),
                    customerNameBox.getText(), customerAddressBox.getText(),
                    postCodeBox.getText(), customerPhoneBox.getText(),
                    divisionID);

            Data.generateCustomers();
            generateCustomersTable();
        }
    }

    /**
     * This allows the user to clear all the fields and tableview selection
     * @param event
     */
    @FXML private void clearButton(ActionEvent event) {
        clearFields();
    }

    /**
     * This allows the user to delete a customer.
     * It first finds all the appointments by that customer and presents a confirmation box asking the user
     * if they want to delete those appointments and that customer. If the user answers in the affirmative,
     * the method then loops through all the appointments by the customer and calls deleteAppointment on each,
     * then it deletes the customer. Finally, it refreshes the Data object's appointment and customer lists before
     * refreshing the tableview.
     * @param event
     * @throws Exception
     */
    @FXML private void deleteButton(ActionEvent event) throws Exception {
        if (customerIDBox.getText() != null) {
            ObservableList<Appointment> appointments = FXCollections.observableArrayList();
            appointments = findAppointments.getAppointmentsbyCustomer(
                    Integer.parseInt(customerIDBox.getText()));

            String title = "Are you sure you wish to delete this customer?";
            String context = "This will delete customer "+
                    customerIDBox.getText() +
                    " and "+
                    appointments.stream().count() + " appointments. Are you sure?";

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle(title);
            confirmation.setContentText(context);
            Optional<ButtonType> confirm = confirmation.showAndWait();

            if (confirm.get() == ButtonType.OK) {
                for (Appointment a : appointments) {
                    AppointmentDAO.deleteAppointment(a.getId());
                }
                Data.generateAppointments();

                CustomerDAO.deleteCustomer(Integer.parseInt(customerIDBox.getText()));

                Data.generateCustomers();
                generateCustomersTable();

            }
        }
    }

}
