package com.app;

import com.app.view.CalcController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Calculator extends Application {
    Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/calc.fxml"));
            GridPane gridPane = fxmlLoader.load();
            Scene scene = new Scene(gridPane);

            CalcController calcController = fxmlLoader.getController();
            calcController.start();

            stage.setScene(scene);
            stage.setTitle("Calculator");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
