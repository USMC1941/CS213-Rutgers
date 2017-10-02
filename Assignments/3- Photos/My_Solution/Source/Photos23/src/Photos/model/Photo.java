package Photos.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * @author William Chen
 * @author Chijun Sha
 */
public class Photo implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 6582167387220829767L;

    /**
     * Format of thumbnail
     */
    private static final String THUMBNAIL_IMAGE_FORMAT 	= "png";

    /**
     * Width of thumbnail
     */
    private static final int THUMBNAIL_WIDTH = 120;

    /**
     * Height of thumbnail
     */
    private static final int THUMBNAIL_HEIGHT= 120;

    /**
     * Path to store thumbnails
     */
    private static final String	THUMBNAIL_PATH = "thumbnails";


    /**
     * create thumbnail folder
     */
    static {
        new File(THUMBNAIL_PATH).mkdir();
    }


    /**
     * @param input Input thumbname
     * @return Thumbnail file name
     */
    public static String getThumbnailFileName(String input) {
        return THUMBNAIL_PATH + "/" + input + "." + THUMBNAIL_IMAGE_FORMAT;
    }


    /**
     * @param filename File name
     * @param file Photo file
     * @return New photo created
     */
    public static Photo createPhoto(String filename, File file) {
        if (file==null) {
            file = new File(filename);
        }
        long lastModified = file.lastModified();
        String shortFileName = file.getName();
        int pos = shortFileName.indexOf('.');
        if (pos>0) {
            shortFileName = shortFileName.substring(0, pos);
        }
        //
        String thumbnail = String.valueOf(UUID.randomUUID());
        boolean ret = createThumbNail(filename, getThumbnailFileName(thumbnail), THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, THUMBNAIL_IMAGE_FORMAT);
        //
        if (ret) {
            return new Photo(filename, thumbnail, shortFileName, lastModified);
        }
        return null;
    }


    /**
     * @param photoFileName File name of origin photo
     * @param thumbnailFileName File name of thumbnail
     * @param width Width of thumbnail
     * @param height Height of thumbnail
     * @param thumbnailFormat Format of thumbnail
     * @return True if thumbnail is created
     */
    private static boolean createThumbNail(String photoFileName, String thumbnailFileName, int width, int height, String thumbnailFormat) {
        boolean ret = true;
        //
        Image image = null;
        try {
            image = new Image(new FileInputStream(photoFileName), width, height, true, true);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //
        if (image!=null) {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            //
            try {
                FileOutputStream fos = new FileOutputStream(thumbnailFileName);
                ImageIO.write(bufferedImage, thumbnailFormat, fos);
                fos.close();
            }
            catch (IOException e) {
                ret = false;
                e.printStackTrace();
            }
        }
        else {
            ret = false;
        }
        //
        return ret;
    }

    /**
     * @param isStore Clean up before store to file or after retrieved from file
     */
    public void doCleanUp(boolean isStore) {
        if (isStore) {
            listOfTagsToKeep = new ArrayList<>(listTags);
            listTags = null;
        }
        else {
            listTags = FXCollections.observableList(listOfTagsToKeep);
            listOfTagsToKeep = null;
        }
    }

    /**
     * File name of the photo
     */
    private String fileName;


    /**
     * Thumbnail name
     */
    private String thumbnail;


    /**
     * Caption that a photo has
     */
    private String caption;


    /**
     * Date the photo was taken
     */
    private long dateOfPhoto;

    /**
     * List of tags the photo contains
     */
    private ObservableList<Tag> listTags;

    /**
     * List of tags the photo contains (for serialization)
     */
    private ArrayList<Tag> listOfTagsToKeep;


    /**
     * @param _fileName File name of photo
     * @param _thumbnail Thumbnail created from photo
     * @param _caption Caption of photo
     * @param _dateOfPhoto Date of photo
     */
    private Photo(String _fileName, String _thumbnail, String _caption, long _dateOfPhoto) {
        fileName 		= _fileName;
        thumbnail		= _thumbnail;
        caption 		= _caption;
        dateOfPhoto		= _dateOfPhoto;
        //
        listTags 		= FXCollections.observableArrayList();
        listOfTagsToKeep = null;
    }

    /**
     * @return List of tags
     */
    public ObservableList<Tag> getTags() {
        return listTags;
    }

    /**
     * @param photo Clone a photo. Including thumbnail file
     */
    public Photo(Photo photo) {
        this.fileName = photo.fileName;
        this.thumbnail = String.valueOf(UUID.randomUUID());
        this.caption = photo.caption;
        this.dateOfPhoto = photo.dateOfPhoto;
        //
        listTags 		= FXCollections.observableArrayList();
        for (Tag t: photo.listTags) {
            listTags.add(new Tag(t));
        }
        //
        try {
            Files.copy(new File(photo.getThumbnailFileName()).toPath(), new File(getThumbnailFileName()).toPath());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return File name of thumbnail
     */
    private String getThumbnailFileName() {
        return getThumbnailFileName(thumbnail);
    }


    /**
     * @return File name of photo
     */
    public String getFileName() {
        return fileName;
    }


    /**
     * @param fileName File name of photo
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    /**
     * @return Caption of photo
     */
    public String getCaption() {
        return caption;
    }


    /**
     * @param caption Caption of photo
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }


    /**
     * @return Date of photo taken
     */
    public long getDateOfPhoto() {
        return dateOfPhoto;
    }

    /**
     * @return Localtime of photo taken
     */
    public String getDateOfPhotoString() {
        return epochToLocalTime(dateOfPhoto);
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


    public Tag deleteTag(String tagName, String tagValue) {
        Tag t = new Tag(tagName, tagValue);
        //
        return Helper.delete(listTags, t, (tt,kk)->tt.compareTo(kk)==0);
    }

    /**
     * @param i Index of tag in list to delete
     */
    public void deleteTag(int i) {
        if (i>=0 && i<listTags.size()) {
            listTags.remove(i);
        }
    }

    /**
     * @param handler Handler for mouse events
     * @param style Style of Image View
     * @return Thumbnail Image View
     */
    public BorderPane getThumbnailImageView(EventHandler<MouseEvent> handler, String style) {
        Image image = new Image("File:"+getThumbnailFileName(), 0, 0, false, false);
        ImageView view = new ImageView(image);
        view.setFitWidth(THUMBNAIL_WIDTH);
        view.setFitHeight(THUMBNAIL_HEIGHT);
        //
        view.setOnMouseClicked(handler);
        view.setUserData(this);
        //
        Photo thisPhoto = this;
        TextField textfield = new TextField(getCaption());
        textfield.setPrefWidth(THUMBNAIL_WIDTH);
        textfield.setOnAction(event -> {
            TextField textField = (TextField) event.getSource();
            String temp = textField.getText().trim();
            if (temp.length()==0) {
                textField.setText(thisPhoto.getCaption());
            }
            else {
                thisPhoto.setCaption(temp);
            }
        });
        //
        VBox vbox = new VBox(4); 	// spacing = 4
        vbox.getChildren().addAll(view, textfield, new Label(getDateOfPhotoString()));
        //
        BorderPane viewWrapper = new BorderPane(vbox);
        viewWrapper.setStyle(style);
        //
        return viewWrapper;
    }

    /**
     * @param handler Handler for mouse events
     * @return Node for Image to render
     */
    public Node getImageNode(EventHandler<MouseEvent> handler) {
        ImageView image;
        Image imageR = null;
        try {
            imageR = new Image(new FileInputStream(getFileName()));
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        //
        image = new ImageView();
        //
        image.setFitWidth(650);
        image.setFitHeight(450);
        image.setPreserveRatio(true);
        image.setSmooth(true);
        image.setImage(imageR);
        image.setOnMouseClicked(handler);
        //
        VBox vbox = new VBox(2); 	// spacing = 2
        //
        vbox.getChildren().addAll(new Label("Caption: " + getCaption() + ". This photo was taken at " + getDateOfPhotoString()), image);
        //
        return vbox;
    }


    /**
     * @param dates Range of dates
     * @return True if within range of dates
     */
    public boolean isWithinRange(TimeSearchCondition dates) {
        ZoneId zoneId = ZoneId.systemDefault();
        //
        LocalDate startDate 	= dates.getStartDate();					//starting of startdate
        LocalDate endDate	 	= dates.getEndDate().plusDays(1);		//end of enddate
        //
        long start 	= startDate.atStartOfDay(zoneId).toEpochSecond();
        long end 	= endDate.atStartOfDay(zoneId).toEpochSecond();
        //
        Date dateOfPhoto = new Date(this.dateOfPhoto);
        LocalDateTime date = dateOfPhoto.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        long toCheck = date.atZone(zoneId).toEpochSecond();

        //
        return toCheck >= start && toCheck < end;
    }


    /**
     * @param time Input time
     * @return Last modified time converted to String
     */
    public static String epochToLocalTime(long time) {
        LocalDateTime datetime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        //
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //
        return datetime.format(formatter);
    }
}