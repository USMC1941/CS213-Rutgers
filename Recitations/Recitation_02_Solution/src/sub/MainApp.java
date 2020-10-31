package sub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sub.view.Controller;

import java.io.IOException;

public class MainApp extends Application {
   Stage mainStage;

   public void start(Stage stage) {
      mainStage = stage;
      mainStage.setTitle("Tastee Sub");

      try {
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("view/Sandwich.fxml"));
         AnchorPane pane = loader.load();

         Controller controller = loader.getController();
         controller.setMainStage(mainStage);

         Scene scene = new Scene(pane, 400, 300);
         mainStage.setScene(scene);
         mainStage.show();
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {
      launch(args);
   }
}
