package f2c.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class F2CApp extends Application {

   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws Exception {

      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/f2c/view/F2C.fxml"));

      GridPane root = (GridPane) loader.load();

      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Fahrenheit-Celsius");
      primaryStage.setResizable(false);
      primaryStage.show();
   }

}
