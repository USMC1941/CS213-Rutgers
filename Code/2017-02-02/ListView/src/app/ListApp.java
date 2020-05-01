package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.ListController;

public class ListApp extends Application {
   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws Exception {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/List.fxml"));
      AnchorPane root = (AnchorPane) loader.load();

      ListController listController = loader.getController();
      listController.start(primaryStage);

      Scene scene = new Scene(root, 200, 300);
      primaryStage.setScene(scene);
      primaryStage.show();
   }
}
