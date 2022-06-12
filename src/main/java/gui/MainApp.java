package gui;

import fuzzy.summaries.ThirdFormMultiSubjectSummary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DatabaseInitialize;

import java.io.IOException;


public class MainApp extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        DatabaseInitialize.databaseInitialize();
        launch();
}


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("loadingScreen"));
        stage.setScene(scene);
        stage.setTitle("Linguistic summaries of hotel reservations database");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }

}
