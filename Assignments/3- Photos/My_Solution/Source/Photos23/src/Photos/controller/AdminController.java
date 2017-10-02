package Photos.controller;

import Photos.model.PhotosModel;
import Photos.model.User;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


/**
 * This class helps with the functionality of an admin account.
 * @author William Chen
 * @author Chijun Sha
 */
public class AdminController extends ControllerBase implements IController, ChangeListener<User> {

    /**
     * ListView for List of Users
     */
    @FXML
    ListView<User> listView;

    /**
     * TextField for name of user to be added
     */
    @FXML
    TextField name;

    public AdminController() {

    }

    /**
     * Clears the name field
     */
    public void initBeforeShow() {
        name.clear();
	}


    /**
     * Initializes the admin scene
     */
    public void initialize() {
    	PhotosModel model = getModel();
    	//
        listView.setItems(model.getListUsers());
        listView.getSelectionModel().selectedItemProperty().addListener(this);
        //
        if (model.getListUsers().size() > 0) {
            listView.getSelectionModel().select(0);
        }
    }


    /**
     * Deletes an user
     */
    public void doDelete() {
    	PhotosModel model = getModel();
    	//
    	int index = listView.getSelectionModel().getSelectedIndex();
    	if (index>=0) {
    		model.deleteUser(index);
    		//
    		listView.refresh();
    	}
    }


    /**
     * Adds a user
     */
    public void doAdd() {
    	String username = name.getText().trim();
    	//
    	PhotosModel model = getModel();
        if (!username.isEmpty()) {
        	User user = model.getUser(username);
        	//
            if (user!=null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Name should be unique case insensitive.");
                alert.setContentText("Another user with this name exists already.");
                alert.showAndWait();
            }
            else {
                model.addUser(username);
                name.clear();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Username");
            alert.setContentText("Username should not be empty");
            alert.showAndWait();
        }
    }


    /**
     * Goes to albums list for admin
     */
    public void gotoAlbumList() {
        gotoUserFromAdmin();
    }


    /**
     * Logs off admin
     */
    public void doLogoff() {
	    gotoLoginFromAdmin();
	}


    /**
     * Exits admin
     */
    public void doExit() {
		Platform.exit();
	}


    /**
     * Shows admin help
     */
    public void doHelp() {
		Help(ADMIN_HELP_FXML);
	}
	
	@Override
	public void changed(ObservableValue<? extends User> arg0, User arg1, User arg2) {
	}
}
