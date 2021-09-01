package com.sub;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.sub.view.Controller;

public class MainApp extends Application {
    Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setTitle("Tastee Sub");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Sandwich.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            Controller controller = fxmlLoader.getController();
            controller.setMainStage(mainStage);

            Scene scene = new Scene(anchorPane, 400, 300);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
