package Controllers;

import Helpers.DBConnection;
import Models.Appointment;
import Models.Customer;
import Models.User;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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



    public customerScreen(User user) {this.user = user; }


    public void initialize(URL url, ResourceBundle rb) {
        getAllCustomers();
        generateCustomersTable();

    }

    public static ObservableList<Customer> getAllCustomers() {
        try{
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostal = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int customerDivision = rs.getInt("Division_ID");

                Customer C = new Customer(customerID, customerName, customerAddress, customerPostal,
                        customerDivision, customerPhone);
                customers.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) { e.printStackTrace(); }

        return customers;
    }

    private void generateCustomersTable(){
        customersTable.setItems(customers);
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postal"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

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

    @FXML private void cancelButton(ActionEvent event) {
        returnToMain(event);
    }


}
