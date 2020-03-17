package sample.JavaFX.MainMenuScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainMenuSceneController {


    @FXML
    Button uploadImageButton;
    @FXML
    Button searchImageButton;
    @FXML
    Button albumsButton;
    @FXML
    Button exitButton;


    public void handleUploadImageButton(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("path to uploadScene"));
            Stage stage = (Stage) uploadImageButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    public void handleSearchSceneButton(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../SearchImages/SearchImages.fxml"));
            // pathen til andre er: ../SearchImageScene/SearchScene.fxml
            Stage stage = (Stage) uploadImageButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    public void handleAlbumsButton(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../SearchAlbumScene/SearchAlbum.fxml"));
            Stage stage = (Stage) uploadImageButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
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
}
