package sample.JavaFX.UploadScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class UploadSceneController{

    @FXML
    private AnchorPane previewImagePane;
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
    Label browse;

    @FXML
    private Button backButton;

    private String uploadImagePath;

    @FXML
    private String browseImages() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif"));
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            ImageView previewImage = new ImageView(new Image(selectedFile.toURI().toString()));
            previewImagePane.getChildren().add(previewImage);
            previewImage.setFitHeight(previewImage.getImage().getHeight()/3);
            previewImage.setFitWidth(previewImage.getImage().getWidth()/3);
        }
        return null;
    }

    public void handleHovered() {
        browse.setCursor(Cursor.HAND);
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

    public void handleBackButtonClicked(){
        FXMLLoader mainSceneLoader = new FXMLLoader(getClass().getResource("../MainMenuScene/MainMenu.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene;
        try {
            scene = new Scene(mainSceneLoader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
