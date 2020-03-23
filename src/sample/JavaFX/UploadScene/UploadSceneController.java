package sample.JavaFX.UploadScene;

import com.drew.metadata.MetadataException;
import javafx.collections.ObservableList;
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
import sample.Java.Album;
import sample.Java.AlbumDAO;
import sample.Java.ImageV2;
import sample.Java.ImageV2DAO;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class UploadSceneController implements Initializable{

    @FXML
    private AnchorPane previewImagePane;
    @FXML
    private TextField imageNameTextField;
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

    //Variables that holds the current uploaded image and its path
    private String uploadImagePath = "";
    private ImageView uploadedImage;

    public UploadSceneController() throws SQLException {
    }

    /**
     * A file chooser that lets user select file to upload
     */
    @FXML
    private void browseImages() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif"));
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            uploadedImage = new ImageView(new Image(selectedFile.toURI().toString()));
            String uploadedImageURL = selectedFile.toURI().toString();
            previewImagePane.setStyle(
                    "-fx-background-image: url('" + uploadedImageURL + "');" +
                    "-fx-background-position: center center;" +
                    "-fx-background-repeat: no-repeat;" +
                    "-fx-background-origin: padding-box;" +
                    "-fx-background-size: contain;" + //or auto if the image should cover the background, but the size will increase aswell.
                    "-fx-background-radius: 15;" +
                    "-fx-border-radius: 15;");
            //previewImagePane.getChildren().add(uploadedImage);
            //uploadedImage.setFitHeight(uploadedImage.getImage().getHeight()/3);
            //uploadedImage.setFitWidth(uploadedImage.getImage().getWidth()/3);
            uploadImagePath = selectedFile.getPath();
        }
    }

    /**
     * Changes cursor to hand when hovers over mouse.
     */
    public void handleHovered() {
        browse.setCursor(Cursor.HAND);
    }

    /**
     * Method for handling when the upload button
     * is clicked. Collects input from scene and sends it to the database
     * @return a boolean
     */
    public boolean handleUploadButtonClicked() throws MetadataException {
        Boolean noTagsOk;
        if(uploadImagePath.equals("")){
            showAlertDialog("image");
            return false;
        }
        else if(imageNameTextField.getText().trim().equals("")){
            showAlertDialog("imageName");
            return false;
        }
        else if(collectListViewTags().size() == 0){
            noTagsOk = showConfirmationDialog();
            if(!noTagsOk){
                return false;
            }
        }
        String tags = "";
        ArrayList<String> tagsList = collectListViewTags();
        for(int i = 0; i < tagsList.size(); i ++){
            tags += tagsList.get(i) + " ";
        }
        //TODO gjør dette på penere måte, må nok ha try and catch greie ellrno greier
        EntityManagerFactory emf = getEntityManagerFactory();
        ImageV2DAO imageV2DAO = new ImageV2DAO(emf);
        imageV2DAO.storeNewImage(new ImageV2(imageNameTextField.getText(), tags, uploadImagePath));
        clearAllFields();
        return true;
    }

    /**
     * Method for handling when the "+" button is
     * clicked. Adds tag then clears the input field.
     */
    public void handleAddTagButtonClicked(){
        if(!tagTextField.getText().equals("")) {
            String tag = tagTextField.getText();
            tagListView.getItems().add(tag);
            tagTextField.clear();
        }
    }

    /**
     * Handles create album button clicked. Shows a dialog
     * where user can write the name of the new album.
     */
    public void handleCreateAlbumButton(){
        String albumName;
        TextInputDialog albumDialog = new TextInputDialog();
        albumDialog.setTitle("Create new album");
        albumDialog.setHeaderText("Create a new album");
        albumDialog.setContentText("Please enter album name: ");

        Optional<String> result = albumDialog.showAndWait();

        if(result.isPresent()){
            albumName = result.get();
            //TODO code to send new album to database
        }
    }

    /**
     * A method for collecting the tags in the listview
     * @return an arraylist with all the tags
     */
    public ArrayList<String> collectListViewTags(){
        ArrayList<String> tagsList = new ArrayList<>();
        ObservableList tags = tagListView.getItems();
        if(tags.size() != 0){
            tags.forEach(tag -> tagsList.add((String)tag));
        }
        return tagsList;
    }

    /**
     * A method for handling the backbutton.
     * Sends you back to mainscene.
     */
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

    /**
     * Shows alert dialog, used when user did not select an image
     */
    public void showAlertDialog(String alertType){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        if(alertType.equals("image")){
            alert.setContentText("You have to select and image!");
        }
        if(alertType.equals("imageName")){
            alert.setContentText("You have to enter image name!");
        }

        alert.showAndWait();
    }


    /**
     * Method that comes up when the listview with tags is empty
     * and user tries to upload. Asks them if they are sure they want
     * no tags
     * @return boolean representing yes or no from user
     */
    public boolean showConfirmationDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ooops!");
        alert.setHeaderText("Tag list is empty");
        alert.setContentText("Are you sure you don't want any tags?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Code to clear all input fields. Gets called when upload or cancel button is pressed
     */
    @FXML
    private void clearAllFields(){
        tagListView.getItems().clear();
        uploadImagePath = "";
        previewImagePane.getChildren().remove(uploadedImage);
        previewImagePane.setStyle("-fx-background-image: null");
    }

    /**
     * Method for loading all the albums from database into the album choice box
     */
    private void loadAlbumChoiceBox(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Piccollect");
        AlbumDAO albumDAO = new AlbumDAO(emf);
        ArrayList<Album> albums = (ArrayList<Album>) albumDAO.getAlbums();
        for(Album album : albums){
            albumChoiceBox.getItems().add(album.getAlbumName());
        }
    }

    private EntityManagerFactory getEntityManagerFactory(){
        return Persistence.createEntityManagerFactory("Piccollect");
    }


    /**
     * Initialize method that gets run when the scene is loaded.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAlbumChoiceBox();
    }
}
