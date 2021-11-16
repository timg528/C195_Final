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

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;
import java.util.ResourceBundle;

/**
 * This class handles the login functions of the application
 */
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

    /**
     * This initializes the screen and calls Data.generateLocalData() to generate the localization data,
     * then Data.getRB() to pull the resource bundle for localization. This allows for the localization data
     * to be used on other pages should I wish to extend this in the future.
     * Finally, it uses the localization data to display the text on the screen.
     * @throws Exception If there is an issue
     */
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

    /**
     * This performs a simple exit of the program
     */
    @FXML
    private void exitProgramButton() {
        Platform.exit();
    }


    /**
     * This takes the values entered for username and password and first verifies that the username is 'test'
     * per the instructions. If it's not, it throws an error. If it is, it then opens a database connection
     * and searches for a user with a matching password. If the database finds it, the user is then directed to the
     * main screen. If not, an error message pops up.
     * For both successful and unsuccessful login attempts, accesslogger() is called and passed a boolean value
     * based on the success.
     * @param event This is the user action on the screen
     * @throws Exception if there's an issue
     */
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
                accessLogger(true);
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
                accessLogger(false);
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText(rb.getString("invalidcreds"));
                errorAlert.showAndWait();
            }
        } else {
            accessLogger(false);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(rb.getString("invaliduser"));
            errorAlert.setContentText(rb.getString("usetest"));
            errorAlert.showAndWait();
        }

    }

    /**
     * This handles writing to the access log file. It always records the ZonedTimeStamp of the access, the
     * username attempted, and whether the attempt was successful or not.
     * @param success This boolean is passed as true if the login attempt was successful, false if not.
     * @throws Exception In case of issues.
     */
    private void accessLogger(boolean success) throws Exception {
        PrintWriter pw = new PrintWriter(new FileOutputStream(new File("login_activity.txt"), true));
        pw.append("Access attempt: " + ZonedDateTime.of(LocalDateTime.now(), Data.getLocalTimezone()) +
                "\t\tUsername: " + usernameField.getText() +"\t\tSuccessful: " + success + "\n");
        pw.close();

    }


}
