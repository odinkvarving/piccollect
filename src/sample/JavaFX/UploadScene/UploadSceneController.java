package sample.JavaFX.UploadScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class UploadSceneController{

    @FXML
    private ChoiceBox albumChoiceBox;
    @FXML
    private Button createAlbumButton;
    @FXML
    private TextField tagTextField;
    @FXML
    private Button addTagButton;
    @FXML
    private ListView tagListView;
    @FXML
    private Button uploadButton;
    @FXML
    private Button cancelButton;


    @FXML
    Button dragAndDropArea;

    @FXML
    private void browseImages() {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif"));
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            System.out.println(selectedFile.getAbsolutePath());
        }
    }

    public void handleUploadButtonClicked(){

    }

    public void handleAddTagButtonClicked(){
        if(!tagTextField.getText().equals("")) {
            String tag = tagTextField.getText();
            tagListView.getItems().add(tag);
            tagTextField.clear();
        }
    }

    public void handleCreateAlbumButton(){
        String albumName;
        TextInputDialog albumDialog = new TextInputDialog();
        albumDialog.setTitle("Create new album");
        albumDialog.setHeaderText("Create a new album");
        albumDialog.setContentText("Please enter album name: ");

        Optional<String> result = albumDialog.showAndWait();

        if(result.isPresent()){
            albumName = result.get();
        }
    }

}
