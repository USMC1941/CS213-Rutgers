package Photos.controller;

import Photos.model.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.List;


/**
 * This class helps to control user functionality.
 * @author William Chen
 * @author Chijun Sha
 */
public class UserController extends ControllerBase implements EventHandler<MouseEvent>, IController, ChangeListener<Album> {

    /**
     * TableView of albums
     */
    @FXML TableView<Album> 	table;

    /**
     * Column of album names
     */
    @FXML TableColumn 	colalbumName;

	@FXML TableColumn 	colphotoCount;
	@FXML TableColumn 	colstartTime;
	@FXML TableColumn 	colendTime;

    /**
     * ListView of tags (used for searching)
     */
	@FXML
	ListView<Tag> listTag;

    /**
     * Textfield for tag name
     */
    @FXML TextField tagName;

    /**
     * Textfield for tag value
     */
    @FXML TextField tagValue;

    /**
     * Textfield for new album name
     */
    @FXML TextField newAlbumName;

    /**
     * Start date for searching date range
     */
    @FXML DatePicker startDate;

    /**
     * End date for searching date range
     */
    @FXML DatePicker endDate;


    /**
     * Initializes for the current user before shown
     */
    public void initBeforeShow() {
    	User user = getModel().getCurrentUser();
    	ObservableList<Album> albumList = user.getListAlbums();
    	//
    	listTag.setItems(user.getTags().getTags());
        if (user.getTags().getTags().size() > 0) {
        	listTag.getSelectionModel().select(0);
        }
    	//
    	for (Album a : albumList) {
    		a.setCounterDatetime();
    	}
    	//
    	table.setItems(albumList);
    	//
        if (user.getListAlbums().size() > 0) {
        	table.getSelectionModel().select(0);
        }
    	//
    	table.refresh();
	}

	
	public UserController() {
	}


    /**
     * Initializes controller
     */
    @FXML
    public void initialize() {
    	table.setEditable(true);
    	//
    	colalbumName.setCellFactory(TextFieldTableCell.<Album>forTableColumn());
    	colalbumName.setOnEditCommit(
            new EventHandler<CellEditEvent<Album, String>>() {
                @Override
                public void handle(CellEditEvent<Album, String> t) {
            		String newAlbumName = t.getNewValue().trim();
            		//
            		if (newAlbumName.length()>0) {
            			User user = getModel().getCurrentUser();
            			//
            			int i = table.getSelectionModel().getSelectedIndex();
            			//
            			user.updateAlbumName(i, newAlbumName);
                        //((Album) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAlbumName(new_albumname);
            		}
            		//
        			table.refresh();
                }
            }
        );
    	//
    	table.setRowFactory(tableView -> {
            final TableRow<Album> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem removeMenuItem = new MenuItem("Remove");
            removeMenuItem.setOnAction(event -> table.getItems().remove(row.getItem()));
            contextMenu.getItems().add(removeMenuItem);
           // Set context menu on row, but use a binding to make it only show for non-empty rows:
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                    .then((ContextMenu)null)
                    .otherwise(contextMenu)
            );
            return row ;
        });
    	//
    	endDate.setValue(LocalDate.now());
    	startDate.setValue(endDate.getValue().minusDays(30));
    	//
    	//listTag.getSelectionModel().selectedItemProperty().addListener(this);
    	//
    	table.getSelectionModel().selectedItemProperty().addListener(this);
    	//
    	table.setOnMouseClicked(event -> {
			if (!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() != 1) {
				if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    //Node node = ((Node) event.getTarget()).getParent();
                    //
                    gotoAlbumFromUser();
                }
			}
			else {
                //Node node = ((Node) event.getTarget()).getParent();
            }
		});
    	
    }

    
    
	@Override
	public void handle(MouseEvent arg0) {
	}


    /**
     * Logs off user
     */
    public void doLogoff() {
	    gotoLoginFromUser();
	}


    /**
     * Exits user
     */
    public void doExit() {
		Platform.exit();
	}


    /**
     * Adds one tag (for tags searching)
     */
    public void doAddUserTag() {
		String name = tagName.getText().trim();
		String value = tagValue.getText().trim();
		//
        User user = getModel().getCurrentUser();
        //
        if (name.length()>0 && value.length()>0) {
            //
            boolean ret = user.getTags().addTag(name, value);
            //
            if (ret) {
                listTag.refresh();
                //
                tagName.setText("");
                tagValue.setText("");
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Not complete");
                alert.setContentText("Duplicate Name and Value Pair. Try again.");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not complete");
            alert.setContentText("Name and Value Fields are empty. Enter again");
            alert.showAndWait();
        }
	}


    /**
     * Deletes one tag used for tag searching
     */
    public void doDeleteUserTag() {
		User user = getModel().getCurrentUser();
		//
		int index = listTag.getSelectionModel().getSelectedIndex();
		//
		user.deleteTagSearchConditionTag(index);
		//
		listTag.refresh();
	}


    /**
     * Searches photos given date range
     */
    public void doSearchDate() {
		User user = getModel().getCurrentUser();
		//
		if (startDate.getValue()==null || endDate.getValue()==null) {
		    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please set search condition (dates) first.", ButtonType.YES/*, ButtonType.NO, ButtonType.CANCEL*/);
		    alert.showAndWait();
		}
		else {
			user.getDates().setStartDate(startDate.getValue());
			user.getDates().setEndDate(endDate.getValue());
			//
			List<Photo> lst = user.doSearchByDate();
			//
			String message;
			if (lst.size()>0) {
				Album newOne = new Album(Album.ALBUM_NAME_SEARCH_BY_DATE, lst);
				newOne.setCounterDatetime();
				user.addOrOverwriteAlbum(newOne);
				message = "Album '" + Album.ALBUM_NAME_SEARCH_BY_DATE + "' is created for search results. It will be replaced in next search. To keep it, simply change its name.";
				//
			    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES/*, ButtonType.NO, ButtonType.CANCEL*/);
			    alert.showAndWait();
				//
	    		user.setCurrentAlbum(newOne);
	    		gotoAlbumFromUser();
			}
			else {
				message = "No photo matched";
				//
			    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES/*, ButtonType.NO, ButtonType.CANCEL*/);
			    alert.showAndWait();
			}
		}
	}


    /**
     * Searches through tags
     */
    public void doSearchTag() {
		User user = getModel().getCurrentUser();
		//
		TagSearchCondition tagCondition = user.getTags();
		//
		if (tagCondition.getTags().size()==0) {
		    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please set search condition (tags) first.", ButtonType.YES/*, ButtonType.NO, ButtonType.CANCEL*/);
		    alert.showAndWait();
		}
		else {
			List<Photo> lst = user.doSearchByTag();
			//
			String message;
			if (lst.size()>0) {
				Album newOne = new Album(Album.ALBUM_NAME_SEARCH_BY_TAG, lst);
				newOne.setCounterDatetime();
				user.addOrOverwriteAlbum(newOne);
				message = "Album '" + Album.ALBUM_NAME_SEARCH_BY_TAG + "' is created for search results. It will be replaced in next search. To keep it, simply change its name.";
				//
			    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES/*, ButtonType.NO, ButtonType.CANCEL*/);
			    alert.showAndWait();
				//
	    		user.setCurrentAlbum(newOne);
	    		gotoAlbumFromUser();
			}
			else {
				message = "No photo matched";
				//
			    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES/*, ButtonType.NO, ButtonType.CANCEL*/);
			    alert.showAndWait();
			}
		}
	}


    /**
     * Adds a new album
     */
    public void doAddNewAlbum() {
		User user = getModel().getCurrentUser();
		//
		String album_name = newAlbumName.getText().trim();
		if (album_name.length()>0) {
			user.addAlbum(album_name);
	    	table.refresh();
			//
			newAlbumName.setText("");
		}
	}

    
    /**
     * Event handler: set current album when selection in table view changed
     */
    @Override
	public void changed(ObservableValue<? extends Album> observable, Album oldValue, Album newValue) {
    	PhotosModel model = getModel();
    	User user = model.getCurrentUser();
    	//
    	if (newValue!=null) {
    		user.setCurrentAlbum(newValue);
    	}
	}


    /**
     * Shows help for photos application
     */
    public void doHelp() {
		Help(PHOTOS_HELP_FXML);
	}
}