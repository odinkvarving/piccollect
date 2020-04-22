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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * MainMenuSceneController controls the MainMenuScene.
 * The class implements Initializable.
 * the @FXML tells the compiler that this variable is connected to an FXML-file
 */

public class MainMenuSceneController implements Initializable {

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
     * Method for what happens when UploadImageButton is clicked. Displays uploadScene and makes it draggable.
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
     * Method for what happens when SearchSceneButton is clicked. Displays the searchScene and makes it draggable.
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
     * Method for what happens when AlbumsButton is clicked. Displays the albumScene and makes it draggable
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
     * Method for what happens when MapButton is clicked. Loads in the MapScene and makes it draggable
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
     * @param root the Parent object - Base class for all nodes that have children in the scene graph
     * @param stage the stage that is dragged
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
     * @param url the URL used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the ResourceBundle used to localize the root object, or null if the root object was not localized
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
