package Controllers;

import Helpers.DBConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
    /**
     *  Requirement A1: Create a log-in form with the following capabilities:
     *    - Accepts a user ID and password and provides an appropriate error message
     *    - Determines the user's location (i.e., ZoneId) and displays it in a label on the log-in form
     *    - Displays the log-in form in English or French based on the user's computer language setting to translate
     *      all the text, labesl, buttons, and errors
     *    - Automatically translates error control messages into English or French based on the user's comp language
     *    - (Unlisted) Only accepts the "test" user to login to the application
     *      - User: "test", Pass: "test", User_ID: 1
     */
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button exitButton;

    @FXML
    private void exitProgramButton(ActionEvent event) {
        // I should use some better exit code here
        Platform.exit();
    }

    // Going to separate the login logic from the login button here so I can continue with the screens
    private void loginAction() {
        /*
        final String username = usernameField.getText();
        final String password = passwordField.getText();

        String selectUser = "Select User_ID FROM users WHERE User_Name = '"+username+"' AND Password = '"+password+"'";

        System.out.println(selectUser);

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectUser);


        ResultSet rs = ps.executeQuery();

        // Testing to learn Result Set
        System.out.println(rs.getInt("User_ID"));
        /**
         if (rs.getInt("User_ID") != 1) {
         Alert errorAlert = new Alert(Alert.AlertType.ERROR);
         errorAlert.setHeaderText("Invalid credentials");
         errorAlert.showAndWait();
         }
         **/

    }

    @FXML
    private void loginButton(ActionEvent event) throws Exception {
        // Temporary code just to keep moving along


    }


}
