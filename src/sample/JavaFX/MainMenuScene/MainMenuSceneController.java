package sample.JavaFX.MainMenuScene;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * MainMenuSceneController controls the MainMenuScene.
 * The class implements Initializable.
 */

public class MainMenuSceneController implements Initializable {

    /**
     * These are the object variables which is used in the controller.
     * uploadImageButton is a button the user can click to go to SploadImageScene
     * searchImageButton is a button the user can click to go to SearchImageScene
     * albumsButton is a button the user can click to go to SearchAlbumScene
     * mapButton is a button the user can click to go to MapScene
     * title is a label for the title of the application
     * titleAddition is a label for the title additions below the title
     * uploadScene is a scene where the user can upload an image
     * searchScene is a scene where the user can search for an image
     * albumScene is a scene where the user can search and browse images
     * mapScene is a scene where the user can use a map to find images
     * xOffset is a variable for making it possible to drag the window in x-axis
     * yOffset is a variable for making it possible to drag the window in y-axis
     */
    @FXML
    private AnchorPane mainMenuPane;
    @FXML
    private Pane windowMenuButtonsBox;
    @FXML
    private Button uploadImageButton;
    @FXML
    private Button searchImageButton;
    @FXML
    private Button albumsButton;
    @FXML
    private Button mapButton;
    @FXML
    private Label title;
    @FXML
    private Label titleAddition;
    private Scene uploadScene;
    private Scene searchScene;
    private Scene albumScene;
    private Scene mapScene;
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * handleUploadImageButton method displays uploadScene and makes it draggable.
     */
    public void handleUploadImageButton(){
        //Initialzes the upload scene
        FXMLLoader uploadSceneLoader = new FXMLLoader(getClass().getResource("../UploadScene/UploadScene.fxml"));
        try {
            Parent uploadSceneRoot = uploadSceneLoader.load();
            Stage stage = (Stage) uploadImageButton.getScene().getWindow();
            makeSceneDraggable(uploadSceneRoot, stage);
            stage.getScene().setRoot(uploadSceneRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * handleSearchSceneButton method displays the searchScene and makes it draggable.
     */
    public void handleSearchSceneButton(){
        //Initializes search image scene
        FXMLLoader searchSceneLoader = new FXMLLoader(getClass().getResource("../SearchImageScene/SearchScene.fxml"));
        try {
            Parent searchSceneRoot = searchSceneLoader.load();
            Stage stage = (Stage) searchImageButton.getScene().getWindow();
            makeSceneDraggable(searchSceneRoot, stage);
            stage.getScene().setRoot(searchSceneRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * handleAlbumsButton method displays the albumScene and makes it draggable
     */
    public void handleAlbumsButton(){
        FXMLLoader albumSceneLoader = new FXMLLoader(getClass().getResource("../SearchAlbumScene/SearchAlbum.fxml"));
        try {
            Parent albumSceneRoot = albumSceneLoader.load();
            Stage stage = (Stage) albumsButton.getScene().getWindow();

            makeSceneDraggable(albumSceneRoot, stage);
            stage.getScene().setRoot(albumSceneRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads in the MapScene and makes it draggable
     */
    public void handleMapButton(){
        FXMLLoader mapSceneLoader = new FXMLLoader(getClass().getResource("../MapScene/MapScene.fxml"));
        try {
            Parent mapSceneRoot = mapSceneLoader.load();
            Stage stage = (Stage) mapButton.getScene().getWindow();
            makeSceneDraggable(mapSceneRoot, stage);
            stage.getScene().setRoot(mapSceneRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for making the scene draggable
     * @param root
     * @param stage
     */
    public void makeSceneDraggable(Parent root, Stage stage){
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    /**
     * Method for making the MainMenuScene draggable. Attached to the main pane in the FXML file.
     */
    public void makeMainMenuPaneMovable(){
        Stage stage;
        stage = (Stage) mainMenuPane.getScene().getWindow();
        makeSceneDraggable(stage.getScene().getRoot(), stage);
    }

    /**
     * Initialize where we load in the custom window-menubuttons-
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Node windowMenuButtonsNode = FXMLLoader.load(getClass().getResource("../WindowMenuButtons/WindowMenuButtons.fxml"));
            windowMenuButtonsBox.getChildren().add(windowMenuButtonsNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
