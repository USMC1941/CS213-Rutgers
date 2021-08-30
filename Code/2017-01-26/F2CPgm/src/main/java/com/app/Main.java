package com.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            GridPane root = makeGridPane();
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static GridPane makeGridPane() {
        // All widgets
        Text fText = new Text("Fahrenheit");
        Text cText = new Text("Celsius");
        TextField f = new TextField();
        TextField c = new TextField();
        Button f2c = new Button(">>>");
        Button c2f = new Button("<<<");

        GridPane gridPane = new GridPane();
        gridPane.add(fText, 0, 0);
        gridPane.add(f2c, 1, 0);
        gridPane.add(cText, 2, 0);
        gridPane.add(f, 0, 1);
        gridPane.add(c2f, 1, 1);
        gridPane.add(c, 2, 1);

        f.setPrefColumnCount(5);
        f.setPromptText("-40.0");
        c.setPrefColumnCount(5);
        c.setPromptText("-40.0");
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        GridPane.setValignment(fText, VPos.BOTTOM);
        GridPane.setValignment(cText, VPos.BOTTOM);

        // event handling
        f2c.setOnAction(
                e -> {
                    float fval = Float.parseFloat(f.getText());
                    float cval = (fval - 32) * 5 / 9;
                    c.setText(String.format("%5.1f", cval));
                });

        c2f.setOnAction(
                e -> {
                    float cval = Float.parseFloat(c.getText());
                    float fval = cval * 9 / 5 + 32;
                    f.setText(String.format("%5.1f", fval));
                });

        return gridPane;
    }
}
