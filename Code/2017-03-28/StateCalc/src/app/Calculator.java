package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.CalcController;

import java.io.IOException;

public class Calculator extends Application {

   Stage mainStage;

   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws Exception {
      mainStage = primaryStage;

      try {
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/view/calc.fxml"));
         GridPane root  = loader.load();
         Scene    scene = new Scene(root);

         // start up the calculator
         CalcController calcController = loader.getController();
         calcController.start();

         mainStage.setScene(scene);
         mainStage.setTitle("Calculator");
         mainStage.setResizable(false);
         mainStage.show();
      }
      catch (IOException e) {
         e.printStackTrace();
      }

   }
}
