package Controllers;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class reportScreen implements Initializable {

    @FXML private TextArea reportTextArea;

    public reportScreen(String reportType) {
        switch (reportType) {
            case "Appointments" : appointmentReport();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void appointmentReport() {
        StringBuilder report = new StringBuilder();
        HashMap<String, Integer> numberByType = Helpers.reports.appointmentbyType();
        report.append("Appointment Type\t\t\t: Count");
        numberByType.forEach((v,k) -> report.append(k + "\t\t\t: " + v ));
        System.out.println(report);
        //reportTextArea.setText(report.toString());


    }

    private void contactReport() {

    }

    private void userReport() {

    }

    private void returnToMain(Event event){
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

    @FXML private void exitButton(ActionEvent event) {returnToMain(event);}
}
