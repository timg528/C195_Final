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
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button exitButton;

    @FXML
    private void exitProgramButton(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void loginButton(ActionEvent event) throws Exception {
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


}
