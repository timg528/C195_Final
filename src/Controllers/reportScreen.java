package Controllers;


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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.w3c.dom.Text;


import java.net.URL;
import java.time.Month;
import java.util.HashMap;
import java.util.ResourceBundle;

public class reportScreen implements Initializable {

    private final ObservableList<String> reportTypes = FXCollections.observableArrayList();
    private String reportType;

    @FXML private TextArea reportText;
    @FXML private Button exitButton;
    @FXML private ComboBox<String> reportBox;

    public reportScreen(String reportType) {
        this.reportType = reportType;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportText.setText("");
        switch (reportType) {
            case "Appointments" :
                reportText.setText("");
                appointmentReport();
        }

    }
/*
    private void populateComboBox() {
        reportTypes.addAll("Appointments", "Contact Schedule", "User Load");
    }

    @FXML private void generateReport() {

    }
*/
    private void appointmentReport() {
        StringBuilder report = new StringBuilder();
        HashMap<String, Integer> numberByType = Helpers.reports.appointmentbyType();
        HashMap<Month, Integer> numberByMonth = Helpers.reports.appointmentByMonth();
        report.append("Count\t\t\t: Type\n");
        numberByType.forEach((v,k) -> report.append(k + "\t\t\t: " + v + "\n" ));
        report.append("\n\n\n#######################\n\n\n");
        report.append("Count\t\t\t: Type\n");
        numberByMonth.forEach((v,k) -> report.append(k + "\t\t\t: " + v + "\n"));
        System.out.println(report);
        reportText.setText(report.toString());




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

    @FXML private void backOut(ActionEvent event) {returnToMain(event);}
}
