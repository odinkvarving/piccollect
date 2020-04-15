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

public class MainMenuSceneController implements Initializable {

    /**
     * These are the object variables which is used in the controller.
     * uploadImageButton
     * searchImageButton
     * albumsButton
     * mapButton
     * title
     * titleAddition
     * uploadScene
     * searchScene
     * albumScene
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

    /**
     * All the different scenes besides the MainMenuScene
     */
    private Scene uploadScene;
    private Scene searchScene;
    private Scene albumScene;
    private Scene mapScene;

    /**
     * Variables for making it possible to drag the window
     */
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * handleUploadImageButton method displays uploadScene and makes it draggable.
     */
    public void handleUploadImageButton(){
        //Initialzes the upload scene
        FXMLLoader uploadSceneLoader = new FXMLLoader(getClass().getResource("../UploadScene/UploadScene.fxml"));
        try {
            uploadScene = new Scene(uploadSceneLoader.load());
            Stage stage = (Stage) uploadImageButton.getScene().getWindow();
            makeSceneDraggable(uploadScene.getRoot(), stage);
            stage.setScene(uploadScene);
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
            searchScene = new Scene(searchSceneLoader.load());
            Stage stage = (Stage) searchImageButton.getScene().getWindow();
            makeSceneDraggable(searchScene.getRoot(), stage);
            stage.setScene(searchScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * handleAlbumsButton method displays the albumScene and makes it draggable
     */
    public void handleAlbumsButton(){//Initializes album scene
        FXMLLoader albumSceneLoader = new FXMLLoader(getClass().getResource("../SearchAlbumScene/SearchAlbum.fxml"));
        try {
            albumScene = new Scene(albumSceneLoader.load());
            Stage stage = (Stage) albumsButton.getScene().getWindow();
            makeSceneDraggable(albumScene.getRoot(), stage);
            stage.setScene(albumScene);
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
            mapScene = new Scene(mapSceneLoader.load());
            Stage stage = (Stage) mapButton.getScene().getWindow();
            makeSceneDraggable(mapScene.getRoot(), stage);
            stage.setScene(mapScene);
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
