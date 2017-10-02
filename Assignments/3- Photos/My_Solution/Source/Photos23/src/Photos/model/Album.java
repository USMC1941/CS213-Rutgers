package Photos.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

/**
 * This class represents an album.
 * @author William Chen
 * @author Chijun Sha
 */
public class Album implements Comparable<Album>, Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -6324409348554636338L;

    /**
     * Album name for result of searching tags
     */
    public static String ALBUM_NAME_SEARCH_BY_TAG	= " Tag Search Result";

    /**
     * Album name for result of searching dates
     */
    public static String ALBUM_NAME_SEARCH_BY_DATE	= " Date Search Result";

    /**
     * Album name when store model to file (since SimpleStringProperty could not be stored in file)
     */
    private String albumNameToKeep;

    /**
     * Name of the album
     */
    private  SimpleStringProperty albumName;

    /**
     * Number of photos
     */
    private  SimpleIntegerProperty photoCount;

    /**
     * earliest date time of picture taken
     */
    private  SimpleStringProperty startTime;

    /**
     * latest time for  picture taken
     */
    private  SimpleStringProperty endTime;

    /**
     * @return Name of album
     */
    public String getAlbumName() {
        return albumName.get();
    }

    /**
     * @param albumName Name of the album
     */
    public void setAlbumName(String albumName) {
        this.albumName.set(albumName);
    }

    /**
     * @return Number of photos
     */
    public int getPhotoCount() {
        return photoCount.get();
    }

    /**
     * @param photoCount Number of photos
     */
    public void setPhotoCount(int photoCount) {
        this.photoCount.set(photoCount);
    }

    /**
     * @return Earliest date time of picture taken
     */
    public String getStartTime() {
        return startTime.get();
    }

    /**
     * @param startTime Earliest date time of picture taken
     */
    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

    /**
     * @return Latest date time of picture taken
     */
    public String getEndTime() {
        return endTime.get();
    }

    /**
     * @param endTime Latest date time of picture taken
     */
    public void setEndTime(String endTime) {
        this.endTime.set(endTime);
    }

    /**
     * @param isStore Clean up before store to file or after retrieved from file
     */
    public void doCleanUp(boolean isStore) {
		if (isStore) {
			albumNameToKeep = getAlbumName();
			//
			albumName 	= null;
			photoCount 	= null;
			startTime 	= null;
			endTime 	= null;
		}
		else {
			albumName 	= new SimpleStringProperty(albumNameToKeep);
		    photoCount 	= new SimpleIntegerProperty();
		    startTime 	= new SimpleStringProperty();
		    endTime 	= new SimpleStringProperty();
		    //
		    albumNameToKeep = null;
		}
		//
		for (Photo p: photoList) {
			p.doCleanUp(isStore);
		}
	}
	
    /**
     * List of all the photos in the album
     */
    private List<Photo> photoList;

    /**
     * Index of the current photo
     */
    private int indexCurrentPhoto;


    /**
     * @param _albumName Name of the album
     */
    public Album(String _albumName) {
		albumName 			= new SimpleStringProperty(_albumName);
	    photoCount 			= new SimpleIntegerProperty(0);
	    startTime 			= new SimpleStringProperty("N/A");
	    endTime 			= new SimpleStringProperty("N/A");
	    //
	    photoList 			= new ArrayList<>();
	    indexCurrentPhoto 	= -1;
	}


    /**
     * @param _albumName Name of the album
     * @param photoLst List of photos
     */
    public Album(String _albumName, List<Photo> photoLst) {
		this(_albumName);
	    //
	    for (Photo p : photoLst) {
		    Photo newOne = new Photo(p);
		    photoList.add(newOne);
	    }
	    //
		if (photoList.size()>0) {
			indexCurrentPhoto = 0;
		}
	}

    /**
     * @param input Input index of current photo
     * @return index of current photo
     */
    public int setIndexCurrentPhoto(int input) {
		indexCurrentPhoto = input;
		return resetIndexCurrentPhoto();
	}

    /**
      * @return Index of current photo
     */
    private int resetIndexCurrentPhoto() {
		if (photoList.size()==0) {
		    indexCurrentPhoto 	= -1;
		}
		else {
			if (indexCurrentPhoto > photoList.size() - 1) {
				indexCurrentPhoto  = photoList.size() - 1;
			}
			else if (indexCurrentPhoto < 0) {
				indexCurrentPhoto = 0;
			}
		}
		//
		return indexCurrentPhoto;
	}

    /**
     * @return Index of current photo
     */
    public int getIndexCurrentPhoto() {
		return 	resetIndexCurrentPhoto();
	}


    /**
     * @param photo Input photo to be added
     */
    public void addPhotoToAlbum(Photo photo) {
    	photoList.add(photo);
    	indexCurrentPhoto = photoList.size() - 1;
	}


    /**
     * @param index Index of photo
     * @return Photo from index
     */
    public Photo getPhotoFromAlbum(int index) {
    	if (index < photoList.size() && index>=0) {
    		return photoList.get(index);
    	}
    	return null;
    }

    /**
     * @param photo Input photo
     * @return Index of the photo
     */
    public int getPhotoIndex(Photo photo) {
        return IntStream.range(0, photoList.size()).filter(i -> photo == photoList.get(i)).findFirst().orElse(-1);
    }


    /**
     * @param photo Input photo
     * @return the index of photo deleted
     */
    public int deletePhotoFromAlbum(Photo photo) {
    	int index = -1;
    	for (int i=0; i<photoList.size(); i++) {
    		if (photo==photoList.get(i)) {
    			index = i;
    			photoList.remove(index);
    			break;
    		}
    	}
    	return index;
    }


    /**
     * @param photoAt Photo: the place(index of photo in the list) that the new photo to add to 
     * @param photoAdd Photo to be added
     * @return Index of the photo to be added
     */
    public int addPhotoToAlbum(Photo photoAt, Photo photoAdd) {
    	int index;
        if (photoAt != null) {
            index = IntStream.range(0, photoList.size())
                    .filter(i -> photoAt == photoList.get(i))
                    .findFirst()
                    .orElse(-1);
        } else index = photoList.size();
        //
		photoList.add(index, photoAdd);
		//
    	return index;
    }


    /**
     * @return List of photos
     */
    public List<Photo> getPhotoList() {
    	return photoList;
    }

    /**
     * @return Size of list of photos
     */
    public int getSize() {
    	return photoList.size();
    }


    /**
     * @return Current photo
     */
    public Photo getCurrentPhoto() {
    	resetIndexCurrentPhoto();
        return indexCurrentPhoto >= 0 ? photoList.get(indexCurrentPhoto) : null;
    }


    /**
     * @param album Input album
     */
    @Override
	public int compareTo(Album album) {
		return getAlbumName().compareToIgnoreCase(album.getAlbumName());
	}


    /**
     * @return Album name
     */
    @Override
	public String toString() {
		return getAlbumName();
	}


    /**
     * @param tags Condition of tags to search
     * @return List of photos within given tags
     */
    public List<Photo> doSearchTag(TagSearchCondition tags) {
		BiPredicate<Photo,TagSearchCondition> bp = (p, c)->Helper.search(p.getTags(), c.getTags(), (t1,t2)->t1.compareTo(t2)==0);
		//
		return Helper.filter(photoList, tags, bp);
	}


    /**
     * @param dates Condition of dates to search
     * @return List of photos within given dates
     */
    public List<Photo> doSearchDate(TimeSearchCondition dates) {
		BiPredicate<Photo,TimeSearchCondition> bp = Photo::isWithinRange;
		//
		return Helper.filter(photoList, dates, bp);
	}


    /**
     * Sets photo count and the Earliest and latest times of all the photos for the album
     */
    public void setCounterDatetime() {
		if (photoList.size()==0) {
			setPhotoCount(0);
		    setStartTime("N/A");
		    setEndTime("N/A");
		}
		else {
			boolean start = true;
			int count 	= 0;
			long min	= 0;
			long max	= 0;
			for (Photo p: photoList) {
				if (start) {
					count = 1;
					min	= p.getDateOfPhoto();
					max	= p.getDateOfPhoto();
					start = false;
				}
				else {
					count++;
					long pd = p.getDateOfPhoto();
					if (pd > max) {
						max = pd;
					}
					if (pd < min) {
						min = pd;
					}
				}
			}
			//
			setPhotoCount(count);
		    setStartTime(Photo.epochToLocalTime(min));
		    setEndTime(Photo.epochToLocalTime(max));
		}
	}
}