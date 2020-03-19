package sample.JavaFX.UploadScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Java.Image;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class UploadSceneController {

    @FXML
    private String browseImages() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif"));
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        }
        return null;
    }


}
