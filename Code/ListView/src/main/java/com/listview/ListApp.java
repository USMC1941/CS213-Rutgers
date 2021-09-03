package com.listview;

import com.listview.view.ListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ListApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/List.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();

        ListController listController = fxmlLoader.getController();
        listController.start(primaryStage);

        Scene scene = new Scene(anchorPane, 200, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
