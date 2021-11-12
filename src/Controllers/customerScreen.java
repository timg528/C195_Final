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


    public void initialize(URL url, ResourceBundle rb) {
        generateCustomersTable();
        loadCountryBox();


    }

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


    private void loadCountryBox() {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        countries = Data.getCountries();

        countryBox.setItems(countries);
    }

    @FXML private void selectDivision() {
        if (countryBox.getSelectionModel().getSelectedItem() != null) {
            int countryID = countryBox.getSelectionModel().getSelectedItem().getCountryID();
            stateBox.setItems(Data.getDivisions()
                    .filtered(d -> d.getCountryID() == countryID)
                    .sorted());
        }

    }

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
    private void returnToMain(Event event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/mainScreen.fxml"));
            mainScreen controller = new mainScreen();

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
    @FXML private void cancelButton(ActionEvent event) {
        returnToMain(event);
    }

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

    @FXML private void clearButton(ActionEvent event) {
        clearFields();
    }

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
