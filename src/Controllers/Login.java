package Controllers;

import Helpers.DBConnection;
import Models.Data;
import Models.User;
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
import java.sql.SQLException;

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
    @FXML private Label systemTimezone;

    public void initialize() throws Exception {
        Data.generateLocalData();
        systemTimezone.setText("Timezone: "+ Data.getLocalTimezone().getId());

    }



    @FXML
    private void exitProgramButton(ActionEvent event) {
        // I should use some better exit code here
        Platform.exit();
    }

    // I should separate out the credential checking here
    private void checkCredentials() throws SQLException {


    }

    @FXML
    private void loginButton(ActionEvent event) throws Exception {

        // Static values for testing
        int userID = 1;
        String userName = "test";
        String Password = "test";

        final String username = usernameField.getText();
        final String password = passwordField.getText();

        String selectUser = "Select User_ID FROM users WHERE User_Name = '"+username+"' AND Password = '"+password+"'";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectUser);


        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            User user = new User(rs.getInt("User_ID"), userName);
            System.out.println("User_ID = "+user.getId());

            Data.setCurrentUser(user.getId());
            Data.generateAll();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/mainScreen.fxml"));
            mainScreen controller = new mainScreen(user);

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











    }


}
