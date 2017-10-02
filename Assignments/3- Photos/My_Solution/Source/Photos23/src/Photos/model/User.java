package Photos.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class represents an user.
 * @author William Chen
 * @author Chijun Sha
 */
public class User implements Comparable<User>, Serializable {

    /**
     * Admin user
     */
    public static final String ADMIN_USER = "admin";

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -2849110358758549664L;

    /**
     * @param isStore Clean up before store to file or after retrieve from file
     */
    public void doCleanUp(boolean isStore) {
		if (isStore) {
			listOfAlbumsToKeep = new ArrayList<>(listAlbums);
			listAlbums = null;
			//
			for (Album a : listOfAlbumsToKeep) {
				a.doCleanUp(true);
			}
		}
		else {
			listAlbums = FXCollections.observableList(listOfAlbumsToKeep);
			listOfAlbumsToKeep = null;
			//
			for (Album a : listAlbums) {
				a.doCleanUp(false);
			}
		}
		//
		tags.doCleanUp(isStore);
	}

    /**
     * Username
     */
    private String username;

    /**
     * List of albums
     */
    private ObservableList<Album> listAlbums;

    /**
     * List of albums for serialization
     */
    private ArrayList<Album> listOfAlbumsToKeep;

    /**
     * Current album
     */
    private Album currentAlbum;

    /**
     * Tags for searching
     */
    private TagSearchCondition tags;

    /**
     * Range of dates for searching
     */
    private TimeSearchCondition dates;


    /**
     * @param _username Username
     */
    public User(String _username) {
		username 		= _username;
		listAlbums 		= FXCollections.observableArrayList();
		listOfAlbumsToKeep = null;
		currentAlbum 	= null;
		//
		tags 			= new TagSearchCondition();
		dates 			= new TimeSearchCondition();
	}


    /**
     * @param username Username
     * @return New album added
     */
    public Album addAlbum(String username) {
		Album album = new Album(username);
		//
        boolean isFound = IntStream.range(0, listAlbums.size())
                .anyMatch(i -> listAlbums.get(i).getAlbumName().equalsIgnoreCase(album.getAlbumName()));
        //
		if (!isFound) {
			listAlbums.add(album);
			return album;
		}
		else {
			return null;
		}
	}


    /**
     * @param album Input album. Overwrite used for search results
     */
    public void addOrOverwriteAlbum(Album album) {
        IntStream.range(0, listAlbums.size())
                .filter(i -> listAlbums.get(i).getAlbumName().equalsIgnoreCase(album.getAlbumName()))
                .findFirst()
                .ifPresent(i -> listAlbums.remove(i));
		//
		listAlbums.add(album);
	}

    /**
     * @param index Index of album to update
     * @param albumName New album name
     */
    public void updateAlbumName(int index, String albumName) {
		if (index >= 0 && index < listAlbums.size()) {
            boolean isFound = IntStream.range(0, listAlbums.size())
                    .filter(i -> i != index)
                    .anyMatch(i -> listAlbums.get(i).getAlbumName().equalsIgnoreCase(albumName));
            //
			if (!isFound) {
				listAlbums.get(index).setAlbumName(albumName);
			}
		}
	}


    /**
     * @return Current album
     */
    public Album getCurrentAlbum() {
		return currentAlbum;
	}

    /**
     * @param currentAlbum Current album
     */
    public void setCurrentAlbum(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }

    /**
     * @return Tags for searching
     */
    public TagSearchCondition getTags() {
        return tags;
    }


    /**
     * @param index Index of tag to delete
     */
    public void deleteTagSearchConditionTag(int index) {
		ObservableList<Tag> tagList = tags.getTags();
		if (tagList.size() > 0 && index >= 0 && index < tagList.size()) {
			tagList.remove(index);
		}
	}

    /**
     * @return Range of dates
     */
    public TimeSearchCondition getDates() {
        return dates;
    }

    /**
     * @return List of albums
     */
    public ObservableList<Album> getListAlbums() {
		return listAlbums;
	}


    /**
     * @return Username
     */
    public String getUsername() {
		return username;
	}


	public Album deleteAlbum(String albumName) {
		return Helper.delete(listAlbums, albumName, (t,k)->t.getAlbumName().equalsIgnoreCase(k));
	}


    /**
     * @param albumName Album name to search for
     * @return Album requested
     */
    public Album getAlbum(String albumName) {
		return Helper.get(listAlbums, albumName, (t,k)->t.getAlbumName().equalsIgnoreCase(k));
	}

    /**
     * @param photo Photo to copy
     * @param targetAlbumName Album to copy to
     */
    public void copyPhoto(Photo photo, String targetAlbumName) {
		Photo newOne = new Photo(photo);
		//
		Album targetAlbum = getAlbum(targetAlbumName);
		//
		targetAlbum.addPhotoToAlbum(newOne);
	}

    /**
     * @param photo Photo to move
     * @param targetAlbumName Album to move to
     */
    public void movePhoto(Photo photo, String targetAlbumName) {
		Album targetAlbum = getAlbum(targetAlbumName);
		//
		currentAlbum.deletePhotoFromAlbum(photo);
		//
		targetAlbum.addPhotoToAlbum(photo);
	}


    /**
     * @return Username
     */
    @Override
	public String toString() {
		return username;
	}


    /**
     * @param arg0 Input username
     * @return Checks whether username is equal or not
     */
    @Override
	public int compareTo(User arg0) {
		return username.compareToIgnoreCase(arg0.username);
	}


    /**
     * @return List of photos with specified tags
     */
    public List<Photo> doSearchByTag() {
		List<Photo> lst = new ArrayList<>();
		//
		for (Album a : listAlbums) {
            if (!a.getAlbumName().equalsIgnoreCase(Album.ALBUM_NAME_SEARCH_BY_DATE) && !a.getAlbumName()
                    .equalsIgnoreCase(Album.ALBUM_NAME_SEARCH_BY_TAG)) {
                lst.addAll(a.doSearchTag(tags));
            }
        }
		//
		return lst;
	}


    /**
     * @return List of photos within range of dates
     */
    public List<Photo> doSearchByDate() {
		List<Photo> lst = new ArrayList<>();
		//
		for (Album a : listAlbums) {
            if (!a.getAlbumName().equalsIgnoreCase(Album.ALBUM_NAME_SEARCH_BY_DATE) && !a.getAlbumName()
                    .equalsIgnoreCase(Album.ALBUM_NAME_SEARCH_BY_TAG)) {
                lst.addAll(a.doSearchDate(dates));
            }
        }
		//
		return lst;
	}
}
