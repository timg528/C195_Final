package Controllers;

import Helpers.DBConnection;
import Models.Data;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;


public class Login {
    /**
     *  Requirement A1: Create a log-in form with the following capabilities:
     *    - Accepts a user ID and password and provides an appropriate error message
     *    - Determines the user's location (i.e., ZoneId) and displays it in a label on the log-in form
     *    - Displays the log-in form in English or French based on the user's computer language setting to translate
     *      all the text, labels, buttons, and errors
     *    - Automatically translates error control messages into English or French based on the user's comp language
     *    - (Unlisted) Only accepts the "test" user to login to the application
     *      - User: "test", Pass: "test", User_ID: 1
     */
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label systemTimezone;

    public void initialize() throws Exception {
        Data.generateLocalData();
        systemTimezone.setText("System Timezone: " +
                Data.getLocalTimezone().toString() +
                " (" + Data.getLocalTimezone().getRules().getOffset(Instant.now()) + ")");

    }

    @FXML
    private void exitProgramButton() {
        Platform.exit();
    }


    @FXML
    private void loginButton(ActionEvent event) throws Exception {

        if (usernameField.getText().equals("test")) {
            String selectUser = "Select User_ID FROM users WHERE User_Name = ? AND Password = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectUser);

            ps.setString(1, usernameField.getText());
            ps.setString(2, passwordField.getText());


            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Data.setCurrentUser(rs.getInt("User_ID"));
                Data.generateAll();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/mainScreen.fxml"));
                mainScreen controller = new mainScreen();

                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Invalid Credentials");
                errorAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid Username");
            errorAlert.setContentText("You must use test as the username");
            errorAlert.showAndWait();
        }



    }


}
