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
import java.util.Locale;
import java.util.ResourceBundle;


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

    @FXML private Label usernameLabel, passwordLabel;
    @FXML private Button loginButton, exitButton;

    ResourceBundle rb;

    public void initialize() throws Exception {
        Data.generateLocalData();
        rb = Data.getRB();

        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        systemTimezone.setText(rb.getString("timezone") + ": \n" +
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
                errorAlert.setHeaderText(rb.getString("invalidcreds"));
                errorAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(rb.getString("invaliduser"));
            errorAlert.setContentText(rb.getString("usetest"));
            errorAlert.showAndWait();
        }



    }


}
