package Photos.controller;

import Photos.model.Album;
import Photos.model.Photo;
import Photos.model.PhotosModel;
import Photos.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * This class is base of all controllers.
 * @author William Chen
 * @author Chijun Sha
 */
public class ControllerBase {

    /**
     * Help pop-up for admin
     */
    public static final String ADMIN_HELP_FXML 	= "/Photos/view/AdminHelp.fxml";

    /**
     * Help pop-up for photos app
     */
    public static final String PHOTOS_HELP_FXML = "/Photos/view/PhotosHelp.fxml";

    /**
     * File for storing data
     */
	public final static String DATA_FILE_PATH 	= "photo23.dat";


    /**
     * Login scene
     */
    protected static Scene sceneLogin 			= null;

    /**
     * Admin scene
     */
    public static Scene sceneAdmin 				= null;

    /**
     * User scene
     */
    public static Scene sceneUser 				= null;

    /**
     * Album scene
     */
    public static Scene sceneAlbum 				= null;

    /**
     * Controller for login
     */
    public static IController controllerLogin 	= null;

    /**
     * Controller for admin
     */
    public static IController controllerAdmin 	= null;

    /**
     * Controller for user
     */
    public static IController controllerUser 	= null;

    /**
     * Controller for album
     */
    public static IController controllerAlbum 	= null;

    /**
     * Login stage
     */
    public static Stage loginStage				= null;

    /**
     * Album stage
     */
    public static Stage albumStage				= null;


    /**
     * @param fml Filename for help pop-up
     */
    public static void Help(String fml) {
    	Parent root;
		try {
			root = FXMLLoader.load(ControllerBase.class.getResource(fml));
			//
	    	Stage window = new Stage();
	    	//
	    	window.initModality(Modality.APPLICATION_MODAL);
			// Create scene
			Scene scene = new Scene(root);
			window.setScene(scene);
			//
			// Set properties of stage
			window.setTitle("Help");
			window.setResizable(false);
			// Render it
			window.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    }


    /**
     * @param primaryStage Primary stage of application
     * @throws Exception In case stage does not start
     */
    public static void start(Stage primaryStage) throws Exception {
		{	FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ControllerBase.class.getResource("/Photos/view/Login.fxml"));
			Parent root = loader.load();
			sceneLogin = new Scene(root);
			controllerLogin = loader.getController();
		}
		//
		{	FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ControllerBase.class.getResource("/Photos/view/Admin.fxml"));
			Parent root = loader.load();
			sceneAdmin = new Scene(root);
			controllerAdmin = loader.getController();
		}
		//
		{	FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ControllerBase.class.getResource("/Photos/view/User.fxml"));
			Parent root = loader.load();
			sceneUser = new Scene(root);
			controllerUser = loader.getController();
		}
		//
		{	FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ControllerBase.class.getResource("/Photos/view/Album.fxml"));
			Parent root = loader.load();
			sceneAlbum = new Scene(root);
			controllerAlbum = loader.getController();
		}
		//
		primaryStage.setTitle("Not yet");
		primaryStage.setResizable(false);
		//
		loginStage = primaryStage;
		//
		albumStage = new Stage();
		primaryStage.setTitle("Not yet");
		albumStage.setResizable(false);
		//
		gotoLogin();
	}

    /**
     * Model for photos
     */
    private static PhotosModel model;

    /**
     * @return Model. Retrieve model from file. If could not create new model
     */
    public static PhotosModel getModel() {
        if (model == null) {
            try {
                FileInputStream fileIn = new FileInputStream(DATA_FILE_PATH);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                model = (PhotosModel)in.readObject();
                model.doCleanUp(false);
                in.close();
                fileIn.close();
            }
            catch(IOException | ClassNotFoundException i) {
                model = null;
                //i.printStackTrace();
            }
            //
            if (model==null) {
                model = new PhotosModel();
                //
                model.addUser("admin");
                model.addUser("stock");
                //
                model.setCurrentUser(model.getUser("stock"));
                //
                User user = model.getCurrentUser();
                //
                Album album1 = user.addAlbum("ocean");
                album1.addPhotoToAlbum(Photo.createPhoto("stockphotos/ocean1.jpg", null));
                album1.addPhotoToAlbum(Photo.createPhoto("stockphotos/ocean2.jpg", null));
                album1.addPhotoToAlbum(Photo.createPhoto("stockphotos/ocean3.jpg", null));
                album1.addPhotoToAlbum(Photo.createPhoto("stockphotos/ocean4.jpg", null));
                album1.addPhotoToAlbum(Photo.createPhoto("stockphotos/ocean5.jpg", null));
                //
                Album album2 = user.addAlbum("snake");
                album2.addPhotoToAlbum(Photo.createPhoto("stockphotos/snake1.jpg", null));
                album2.addPhotoToAlbum(Photo.createPhoto("stockphotos/snake2.jpg", null));
                album2.addPhotoToAlbum(Photo.createPhoto("stockphotos/snake3.jpg", null));
                album2.addPhotoToAlbum(Photo.createPhoto("stockphotos/snake4.jpg", null));
                album2.addPhotoToAlbum(Photo.createPhoto("stockphotos/snake5.jpg", null));
            }
        }
        //
        return model;
    }


    /**
     * Helps store the PhotosModel to file
     */
    public static void storeModelToFile() {
		 if (model!=null) {
	        model.doCleanUp(true);
			 //
	         try {
	            FileOutputStream fileOut = new FileOutputStream(DATA_FILE_PATH);
	            ObjectOutputStream out = new ObjectOutputStream(fileOut);
	            out.writeObject(model);
	            out.close();
	            fileOut.close();
	         }
	         catch (IOException i) {
	             i.printStackTrace();
	         }
		 }
	 }


    /**
     * Goes to the album stage for users
     */
    public void gotoAlbumFromUser() {
        albumStage.setScene(sceneAlbum);
        controllerAlbum.initBeforeShow();
        albumStage.setTitle("Album " + model.getCurrentUser().getCurrentAlbum().getAlbumName());
        albumStage.show();
    }


    /**
     * Goes to the admin stage for admin
     */
    public static void gotoAdminFromLogin() {
		loginStage.setScene(sceneAdmin);
		//
		controllerAdmin.initBeforeShow();
		loginStage.setTitle("Welcome to the Admin Tool!");
		loginStage.show();
    }

    /**
     * Goes to login stage from admin
     */
    public static void gotoLoginFromAdmin() {
    	gotoLogin();
    }

    /**
     * Goes to login stage from albums
     */
    public static void gotoLoginFromAlbum() {
    	albumStage.hide();
		//
    	gotoLogin();
    }

    /**
     * Goes to login stage from user
     */
    public static void gotoLoginFromUser() {
    	albumStage.hide();
		//
    	gotoLogin();
    }

    /**
     * Goes to the login stage
     */
    private static void gotoLogin() {
    	loginStage.setScene(sceneLogin);
		//
		controllerLogin.initBeforeShow();
		loginStage.setTitle("Welcome To Photos App!");
		loginStage.show();
		//
		// Put scene to stage
		//primaryStage.setScene(sceneLogin);
		// Set properties of stage
		//primaryStage.setTitle("Welcome To Photos App!");
		// Render it
		//primaryStage.show();
    }


    /**
     * If user logs in, goes to user
     */
    public static void gotoUserFromLogin() {
		loginStage.hide();
		//
		gotoUser();
    }

    /**
     * Given admin, go to user stage
     */
    public static void gotoUserFromAdmin() {
		loginStage.hide();
		//
		gotoUser();
    }

    /**
     * Go to user stage
     */
    public static void gotoUserFromAlbum() {
		gotoUser();
	}

    /**
     * Go to user stage
     */
    private static void gotoUser() {
		albumStage.setScene(sceneUser);
		//
		controllerUser.initBeforeShow();
		albumStage.setTitle("Welcome " + model.getCurrentUser().getUsername() + "!");
		albumStage.show();
	}
}
