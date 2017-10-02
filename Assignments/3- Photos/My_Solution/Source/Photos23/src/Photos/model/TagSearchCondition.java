package Photos.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class helps to search through tags.
 * @author William Chen
 * @author Chijun Sha
 */
public class TagSearchCondition implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 3520630274431813396L;


    /**
     * @param isStore Clean up before store to file or after retrieve from file
     */
    public void doCleanUp(boolean isStore) {
		if (isStore) {
			listOfTagsToKeep = new ArrayList<>(listTags);
			listTags 		= null;
		}
		else {
			listTags 		= FXCollections.observableList(listOfTagsToKeep);
			listOfTagsToKeep = null;
		}
	}


    /**
     * List of tags
     */
    private ObservableList<Tag> listTags;

    
    /**
     * List of tags (For serialization)
     */
    private ArrayList<Tag> listOfTagsToKeep;


    /**
     * Initialize fields
     */
    public TagSearchCondition() {
	    listTags = FXCollections.observableArrayList();
	}


    /**
     * @return List of tags
     */
    public ObservableList<Tag> getTags() {
		return listTags;
	}


    /**
     * @param tagName Tag name
     * @param tagValue Tag value
	 * @return True if tag is added
     */
    public boolean addTag(String tagName, String tagValue) {
    	Tag t = new Tag(tagName, tagValue);
    	//
        return Helper.addOrRetrieveOrderedList(listTags, t);
    }
}