package SongLibrary.controller;

import SongLibrary.model.SongItem;
import SongLibrary.model.SongLibraryModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author William Chen
 * @author Chijun Sha
 */
public class SongLibraryController implements ChangeListener<SongItem> {

    SongLibraryModel model = new SongLibraryModel();

    @FXML ListView<SongItem> listView;
	@FXML Button add;
	@FXML Button delete;
    @FXML Button update;
    @FXML Button help;
	@FXML TextField name;
	@FXML TextField artist;
    @FXML TextField album;
    @FXML TextField year;


    @FXML
    public void initialize() {
        SongLibraryModel.setTheModel(model);
        //
        listView.setItems(model.getItemsInList());
        //
        listView.getSelectionModel().selectedItemProperty().addListener(this);
        //
        if (model.getItemCount() > 0) {
            listView.getSelectionModel().select(0);
        }
    }


    public void Help() throws IOException {
    	Stage window = new Stage();
    	Parent root = FXMLLoader.load(getClass().getResource("/SongLibrary/view/help.fxml"));
    	window.initModality(Modality.APPLICATION_MODAL);
		// Create scene
		Scene scene = new Scene(root);
		window.setScene(scene);
		// Set properties of stage
		window.setTitle("Help");
		window.setResizable(false);
		// Render it
		window.show();
    }

    
	public void doAdd() {
        if (name.getText() == null || name.getText().trim().equals("") || artist.getText() == null || artist.getText().trim().equals("")) {
            Alert warning = new Alert(Alert.AlertType.WARNING, "Song and/or artist name is empty.", ButtonType.OK);
            warning.showAndWait();
            return;
        }
        //
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add the song to the library?", ButtonType.YES, /*ButtonType.NO,*/ ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() != ButtonType.YES) {
            return;
        }
        //
        int index = model.addSong(name.getText().trim(), artist.getText().trim(), album.getText().trim(), year.getText().trim());
        //
        if (index >= 0) {
            listView.getSelectionModel().select(index);
        }
        else {
            Alert warning = new Alert(Alert.AlertType.WARNING, "Cannot add this song to the library due to duplicate.", ButtonType.OK);
            warning.showAndWait();
        }
    }


    public void doUpdate() {
        if (name.getText() == null || name.getText().trim().equals("") || artist.getText() == null || artist.getText().trim().equals("")) {
            Alert warning = new Alert(Alert.AlertType.WARNING, "Song and/or artist name is empty.", ButtonType.OK);
            warning.showAndWait();
            return;
        }
        //
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to update the song?", ButtonType.YES, /*ButtonType.NO,*/ ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() != ButtonType.YES) {
            return;
        }
        //
        int index = listView.getSelectionModel().getSelectedIndex();
        //
        if (index >= 0) {
            int newIndex = model.updateSong(index, name.getText().trim(), artist.getText().trim(), album.getText().trim(), year.getText().trim());
            //
            if (newIndex < 0) {
                Alert warning = new Alert(Alert.AlertType.WARNING, "Cannot update this song in the library due to duplicate.", ButtonType.OK);
                warning.showAndWait();
            }
            if (newIndex != index) {
                listView.getSelectionModel().select(newIndex);
            }
            else {
                // Name and artist not changed -> selected is not changed
            }
        }
        else {
            Alert warning = new Alert(Alert.AlertType.WARNING, "Should never be here.", ButtonType.OK);
            warning.showAndWait();
        }
    }

    
    
    public void doDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete the song?", ButtonType.YES, /*ButtonType.NO,*/ ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() != ButtonType.YES) {
            return;
        }
        //
        int size = model.getItemCount();
        int index = listView.getSelectionModel().getSelectedIndex();
        //
        if (index >= 0) {
            boolean isDeleted = model.deleteSongItem(index);
            //
            if (isDeleted) {
                if (size > index + 1) {                     // Item after index
                    listView.getSelectionModel().select(index);
                }
                else if (size >= 2) {                       // Nothing after
                    listView.getSelectionModel().select(index - 1);             // Delete one before
                }
                else {
                    // No more items left
                }
            }
            else {
                Alert warning = new Alert(Alert.AlertType.WARNING, "Should never be here.", ButtonType.OK);
                warning.showAndWait();
            }
        }
    }


    @Override
    public void changed(ObservableValue<? extends SongItem> observable, SongItem oldValue, SongItem newValue) {
        if (newValue != null) {
            name.setText(newValue.getName());
            artist.setText(newValue.getArtist());
            album.setText(newValue.getAlbum());
            year.setText(newValue.getYear());
        }
        else {
            name.setText("");
            artist.setText("");
            album.setText("");
            year.setText("");
        }
    }
}
