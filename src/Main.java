package sample;

import Helpers.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {

        try{
            DBConnection.openConnection();
        } catch (Exception e){
            e.printStackTrace();
        }
        launch(args);
        try {
            DBConnection.closeConnection();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
