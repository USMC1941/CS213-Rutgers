package Photos.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents all the data for this application.
 * @author William Chen
 * @author Chijun Sha
 */
public class PhotosModel implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 2564420476827262671L;

    /**
     * @param isStore Clean up before store to file or after retrieve from file
     */
    public void doCleanUp(boolean isStore) {
        if (isStore) {
            listOfUsersToKeep = new ArrayList<>(listUsers);
            listUsers = null;
            //
            //
            for (User u : listOfUsersToKeep) {
                u.doCleanUp(true);
            }
        }
        else {
            listUsers = FXCollections.observableList(listOfUsersToKeep);
            listOfUsersToKeep = null;
            //
            for (User u : listUsers) {
                u.doCleanUp(false);
            }
        }
    }

    /**
     * List of users
     */
    private ObservableList<User> listUsers;

    /**
     * used when store model to file as ObservableList could not be Serialized
     */
    private ArrayList<User> listOfUsersToKeep;

    /**
     * Current user
     */
    private User currentUser;


    /**
     * Initializes fields
     */
    public PhotosModel() {
        listUsers = FXCollections.observableArrayList();
        listOfUsersToKeep = null;
        currentUser = null;
    }


    /**
     * @return List of users
     */
    public ObservableList<User> getListUsers() {
        return listUsers;
    }


    /**
     * @return Current user
     */
    public User getCurrentUser() {
        return currentUser;
    }


    /**
     * @param currentUser Current user
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }


    /**
     * @param userName Username
     */
    public void addUser(String userName) {
        User user = new User(userName);
        //
        Helper.addOrRetrieveOrderedList(listUsers, user);
    }



    public User deleteUser(String userName) {
        if (!userName.equalsIgnoreCase(User.ADMIN_USER)) {
            return Helper.delete(listUsers, userName, (t,k)->t.getUsername().equalsIgnoreCase(k));
        }
        else {
            return null;
        }
    }


    /**
     * @param i Index of user in the list to delete
     */
    public void deleteUser(int i) {
        if (i>=0 && i<listUsers.size()) {
            if (!listUsers.get(i).getUsername().equalsIgnoreCase(User.ADMIN_USER)) {
                listUsers.remove(i);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Deleting Admin.");
                alert.setContentText("Admin cannot be deleted.");
                alert.showAndWait();
            }
        }
    }


    /**
     * @param userName Username
     * @return User
     */
    public User getUser(String userName) {
        return listUsers.stream().filter(user->user.getUsername().equalsIgnoreCase(userName)).findFirst().orElse(null);
    }
}