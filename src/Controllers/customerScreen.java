package Controllers;

import Helpers.DBConnection;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class customerScreen implements Initializable {

    private final User user;
    private final static ObservableList<Customer> customers = FXCollections.observableArrayList();

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



    public customerScreen(User user) {this.user = user; }


    public void initialize(URL url, ResourceBundle rb) {
        generateCustomersTable();
        loadCountryBox();


    }

    private void generateCustomersTable(){
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
        );
    }


    private void loadCountryBox() {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        countries = Data.getCountries();

        countryBox.setItems(countries);
    }

    @FXML private void selectDivision() {

       int countryID = countryBox.getSelectionModel().getSelectedItem().getCountryID();
       stateBox.setItems(Data.getDivisions()
               .filtered(d -> d.getCountryID() == countryID)
               .sorted());

    }



    // Button Actions
    private void returnToMain(Event event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/mainScreen.fxml"));
            mainScreen controller = new mainScreen(user);

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
    @FXML private void createButton(ActionEvent event) {

    }
    @FXML private void updateButton(ActionEvent event) {

    }

    @FXML private void clearButton(ActionEvent event) {

    }

}
