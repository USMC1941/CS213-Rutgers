package SongLibrary.model;

/**
 * @author William Chen
 * @author Chijun Sha
 */
public class SongItem {

    public static String makeKey(String _name, String _artist) {
        return (_name + "\t" + _artist).toUpperCase();
    }

    String name;
    String artist;
    String album;
    String year;

    public SongItem (String _name, String _artist, String _album, String _year) {
        name = _name;
        artist = _artist;
        album = _album;
        year = _year;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getKey () {
        return makeKey(name, artist);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return name;
    }

    
    public String toStringForStorage() {
        return name + "\t" + artist + "\t" + album + "\t" + year  + "\t" + "VERIFY_SONG_ENTRY\n";
    }


    
    public int compareTo(SongItem songItem) {
        return this.getKey().compareTo(songItem.getKey());
    }
}
