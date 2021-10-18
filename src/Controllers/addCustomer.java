package Controllers;

import Models.Customer;
import Models.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class addCustomer implements Initializable {

    private final User user;

    @FXML private Button createCustomerButton;
    @FXML private Button cancelButton;

    @FXML private TextField customerIDBox;
    @FXML private TextField customerNameBox;
    @FXML private TextField customerAddressBox;
    @FXML private TextField customerPhoneBox;
    @FXML private TextField postCodeBox;

    public void initialize(URL url, ResourceBundle rb) {

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
        }
    }

    @FXML private void cancelButton(ActionEvent event) {
        returnToMain(event);
    }


}
