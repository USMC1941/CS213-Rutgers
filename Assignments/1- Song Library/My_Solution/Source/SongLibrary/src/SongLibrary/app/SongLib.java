package SongLibrary.app;

import SongLibrary.model.SongLibraryModel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author William Chen
 * @author Chijun Sha
 */
public class SongLib extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// get root container
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/SongLibrary/view/SongLibrary.fxml"));
		Parent root = loader.load();
		// Create scene
		Scene scene = new Scene(root);

		// Put scene to stage
		primaryStage.setScene(scene);

		// Set properties of stage
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);

		// Render it
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void stop(){
        SongLibraryModel model = SongLibraryModel.getTheModel();
        //
        model.storeToFile();
	}
}
