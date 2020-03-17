package sample.JavaFX.MainMenuScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuSceneController implements Initializable {


    @FXML
    Button uploadImageButton;
    @FXML
    Button searchImageButton;
    @FXML
    Button albumsButton;
    @FXML
    Button exitButton;

    Scene uploadScene;
    Scene searchScene;
    Scene albumScene;


    public void handleUploadImageButton(){
        Stage stage = (Stage) uploadImageButton.getScene().getWindow();
        stage.setScene(uploadScene);
    }
    public void handleSearchSceneButton(){
        Stage stage = (Stage) uploadImageButton.getScene().getWindow();
        stage.setScene(searchScene);
    }
    public void handleAlbumsButton(){
        Stage stage = (Stage) uploadImageButton.getScene().getWindow();
        stage.setScene(albumScene);
    }
    public void handleExitButton(){
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initialzes the upload scene
        /**FXMLLoader uploadSceneLoader = new FXMLLoader(getClass().getResource("path to uploadScene"));
        try {
            uploadScene = new Scene(uploadSceneLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }**/
        //Initializes search image scene
        FXMLLoader searchSceneLoader = new FXMLLoader(getClass().getResource("../SearchImages/SearchImages.fxml"));
        try {
            searchScene = new Scene(searchSceneLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Initializes album scene
        FXMLLoader albumSceneLoader = new FXMLLoader(getClass().getResource("../SearchAlbumScene/SearchAlbum.fxml"));
        try {
            albumScene = new Scene(albumSceneLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
