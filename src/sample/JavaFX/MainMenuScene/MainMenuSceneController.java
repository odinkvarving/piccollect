package sample.JavaFX.MainMenuScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.JavaFX.MapScene.MapSceneV2;
import sample.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuSceneController implements Initializable {

    /**
     * These are the object variables which is used in the controller.
     * uploadImageButton
     * searchImageButton
     * albumsButton
     * exitButton
     * title
     * titleAddition
     * uploadScene
     * searchScene
     * albumScene
     */
    @FXML
    private Pane windowMenuButtonsBox;
    @FXML
    private Button uploadImageButton;
    @FXML
    private Button searchImageButton;
    @FXML
    private Button albumsButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label title;
    @FXML
    private Label titleAddition;

    private Scene uploadScene;
    private Scene searchScene;
    private Scene albumScene;

    /**
     * handleUploadImageButton method displays uploadScene.
     */
    public void handleUploadImageButton(){
        //Initialzes the upload scene
        FXMLLoader uploadSceneLoader = new FXMLLoader(getClass().getResource("../UploadScene/UploadScene.fxml"));
        try {
            uploadScene = new Scene(uploadSceneLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) uploadImageButton.getScene().getWindow();
        stage.setScene(uploadScene);
    }

    /**
     * handleSearchSceneButton method displays the searchScene.
     */
    public void handleSearchSceneButton(){
        //Initializes search image scene
        FXMLLoader searchSceneLoader = new FXMLLoader(getClass().getResource("../SearchImageScene/SearchScene.fxml"));
        try {
            searchScene = new Scene(searchSceneLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) uploadImageButton.getScene().getWindow();
        stage.setScene(searchScene);
    }

    /**
     * handleAlbumsButton method displays the albumScene.
     */
    public void handleAlbumsButton(){//Initializes album scene
        FXMLLoader albumSceneLoader = new FXMLLoader(getClass().getResource("../SearchAlbumScene/SearchAlbum.fxml"));
        try {
            albumScene = new Scene(albumSceneLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }        Stage stage = (Stage) uploadImageButton.getScene().getWindow();
        stage.setScene(albumScene);
    }

    /**
     * handleExitButton method displays an alert which asks the user if he/she wants to exit the application.
     * If the user clicks "OK", the application shuts down.
     * If the user clicks "CANCEL" or closes the dialog box, the user will still be at mainMenuScene.
     */
    public void handleExitButton(){
        MapSceneV2 mapSceneV2 = new MapSceneV2();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        Scene scene = new Scene(mapSceneV2.getMapView());

        stage.setScene(scene);
        /**
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Application");
        alert.setHeaderText("Exit");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
         **/
    }

    /**
     * initialize method loads and initializes the different fxml scenes.
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
