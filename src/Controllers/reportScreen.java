package Controllers;


import Models.Appointment;
import Models.Contact;
import Models.Data;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


import java.net.URL;
import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class reportScreen implements Initializable {

    private String reportType;

    @FXML private TextArea reportText;
    @FXML private Button exitButton;
    @FXML private ComboBox<String> reportBox;

    /**
     * This takes the reportType parameter and adds it to the reportscreen object.
     * @param reportType The string representing the type of report to be run.
     */
    public reportScreen(String reportType) {
        this.reportType = reportType;

    }

    /**
     * This generates the report text by calling a method based on what the reportType is.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        switch (reportType) {
            case "Appointments" : appointmentReport(); break;
            case "Contact Schedule" : contactReport(); break;
            case "User Load" : userReport(); break;
        }

    }


    /**
     * This gets called for the first type of report and displays the count of appointments by Type and by Month.
     */
    private void appointmentReport() {
        Set<String> types = new HashSet<>();
        Set<Month> months = new HashSet<>();
        HashMap<String, Integer> numberByType = new HashMap<>();
        HashMap<Month, Integer> numberByMonth = new HashMap<>();

        for (Appointment appointment: Data.getAppointments()) {
            months.add(appointment.getStart().toLocalDateTime().getMonth());
            types.add(appointment.getType());
        }

        for (String type: types) {
            int count = 0;
            for (Appointment appointment: Data.getAppointments()) {

                if (type.equals(appointment.getType())) {
                    count++;
                }
                numberByType.put(type, count);
            }
        }
        for (Month month: months) {
            int count = 0;
            for (Appointment appointment: Data.getAppointments()) {
                if (month.equals(appointment.getStart().toLocalDateTime().getMonth())) {
                    count++;
                }
                numberByMonth.put(month, count);
            }
        }

        reportText.appendText("Count \t\tType\n");
        numberByType.forEach((k,v) -> reportText.appendText(v + "\t\t" + k + "\n"));
        reportText.appendText("\n\n\nCount \t\tMonth\n");
        numberByMonth.forEach((k,v) -> reportText.appendText(v + "\t\t" + k + "\n"));

    }

    /**
     * This displays the appointments per Contact in the organization.
     */
    private void contactReport() {
        for (Contact contact: Data.getContacts()) {
            reportText.appendText("Contact:" + contact.getContactName() + "\n");
            for (Appointment appointment: Data.getAppointments()) {
                if (appointment.getContact() == contact.getContactID()) {
                    reportText.appendText("Appointment ID: " + appointment.getId() +
                            " \tTitle: " + appointment.getTitle() +
                            "\tType: " + appointment.getType() +
                            "\tDescription: " + appointment.getDescription() +
                            "\tStart: " + appointment.getStart() +
                            "\tEnd: " + appointment.getEnd() +
                            "\tCustomer: " + appointment.getCustomer() + "\n");
                }
                System.out.println("\n\n");
            }
        }
    }

    /**
     * This displays the appointments per User in the organization.
     */
    private void userReport() {
        for (User user: Data.getUsers()) {
            reportText.appendText("User: " + user.getUsername() + "\n");
            for (Appointment appointment: Data.getAppointments()) {
                if (appointment.getUser() == user.getId()) {
                    reportText.appendText("Appointment ID: " + appointment.getId() +
                            " \tTitle: " + appointment.getTitle() +
                            "\tType: " + appointment.getType() +
                            "\tDescription: " + appointment.getDescription() +
                            "\tStart: " + appointment.getStart() +
                            "\tEnd: " + appointment.getEnd() +
                            "\tCustomer: " + appointment.getCustomer() + "\n");
                }
                System.out.println("\n\n");
            }
        }

    }

    /**
     * This handles returning to the main screen when the user is done viewing their report.
     * @param event
     */
    @FXML private void backOut(ActionEvent event) {
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
}
