package SongLibrary.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;

/**
 * @author William Chen
 * @author Chijun Sha
 */
public class SongLibraryModel {
	
    private final static String FILENAME = "SongLibrary_WC_CS.txt";
    
    public static SongLibraryModel theModel = null;

    public static void setTheModel(SongLibraryModel inputModel) {
        theModel = inputModel;
    }

    public static SongLibraryModel getTheModel () {
        return theModel;
    }

    Map<String, SongItem> mapOfSongs        = null;
    ObservableList<SongItem> itemsInList    = null;


    public SongLibraryModel () {
        mapOfSongs = new HashMap<>();
        itemsInList = FXCollections.observableArrayList();
        //
        retrieveFromFile();
    }


    public int addSong(String name, String artist, String album, String year) {
        String key = SongItem.makeKey(name, artist);
        //
        SongItem oneSong = mapOfSongs.get(key);
        //
        if (oneSong == null) {
            oneSong = new SongItem(name, artist, album, year);
            mapOfSongs.put(key, oneSong);
            return indexInsertedSorted(oneSong);
        }
        else {
            return -1;
        }
    }


    public int updateSong(int index, String name, String artist, String album, String year) {
        String oldKey = itemsInList.get(index).getKey();
        String newKey = SongItem.makeKey(name, artist);
        //
        SongItem oneSongWithOldKey = mapOfSongs.get(oldKey);
        //
        if (oneSongWithOldKey == null) {
            return -1;
        }
        else {
            if (oldKey.equals(newKey)) {
                //oneSongWithOldKey.setName(name);          //not changed as keys are the same
                //oneSongWithOldKey.setArtist(artist);
                oneSongWithOldKey.setAlbum(album);
                oneSongWithOldKey.setYear(year);
                return index;
            }
            else {
                SongItem songItemWithNewKey = mapOfSongs.get(newKey);
                if (songItemWithNewKey == null) {
                    oneSongWithOldKey.setName(name);
                    oneSongWithOldKey.setArtist(artist);
                    oneSongWithOldKey.setAlbum(album);
                    oneSongWithOldKey.setYear(year);
                    //
                    mapOfSongs.remove(oldKey);
                    mapOfSongs.put(newKey, oneSongWithOldKey);
                    //
                    itemsInList.remove(index);
                    return indexInsertedSorted(oneSongWithOldKey);
                }
                else {
                    return -1;
                }
            }
        }
    }


    public boolean deleteSongItem (int index) {
        String key = itemsInList.get(index).getKey();
        //
        SongItem songItem = mapOfSongs.get(key);
        //
        if (songItem != null) {
            mapOfSongs.remove(key);
            itemsInList.remove(index);
            return true;
        }
        else {
            return false;
        }
    }


    private int indexInsertedSorted (SongItem input) {
        if (itemsInList.isEmpty()) {
            itemsInList.add(input);
            return 0;
        }
        else {
            for (int i=0; i < itemsInList.size(); i++) {
                if (input.compareTo(itemsInList.get(i)) < 0) {
                    itemsInList.add(i, input);
                    return i;
                }
            }
            //
            itemsInList.add(input);
            return itemsInList.size() - 1;
        }
    }


    public ObservableList<SongItem> getItemsInList() {
        return itemsInList;
    }

    public int getItemCount () {
        return itemsInList.size();
    }


    public void storeToFile () {
        try {
        	Writer writer = new FileWriter(FILENAME);
        	//
        	for (SongItem oneSong : mapOfSongs.values()) {
        		writer.write(oneSong.toStringForStorage());
        	}
        	//
        	writer.close();
        }
        catch(IOException i) {
            i.printStackTrace();
        }
    }
    
    
    public void retrieveFromFile() {
        try {
        	String line;
        	//
        	FileReader fileReader = new FileReader(FILENAME);
        	BufferedReader bufferedReader = new BufferedReader(fileReader);
        	//
        	while ((line = bufferedReader.readLine()) != null ) {
        		String[] temp = line.split("\\t");
        		if (temp.length == 5) {
        			if (temp[4].equals("VERIFY_SONG_ENTRY")) {
        				addSong(temp[0], temp[1], temp[2], temp[3]);
        			}
        		}
        	}
        	//
        	bufferedReader.close();
        	fileReader.close();
        }
        catch(IOException i) {
            //i.printStackTrace();
        }
    }
}
