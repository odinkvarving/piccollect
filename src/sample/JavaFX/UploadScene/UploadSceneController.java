package sample.JavaFX.UploadScene;

import com.drew.metadata.MetadataException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Java.*;
import sample.JavaFX.ResponseDialogs.InformationDialog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class UploadSceneController implements Initializable{

    @FXML
    private Pane windowMenuButtonBox;
    @FXML
    private AnchorPane previewImagePane;
    @FXML
    private AnchorPane dragAreaInfoBox;
    @FXML
    private Button browse;
    @FXML
    private Button closeImageButton;
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
    private Button backButton;

    //Variables that holds the current uploaded image and its path and tags
    private String uploadImagePath = "";
    private ImageView uploadedImage;
    private String tags = "";


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
            dragAreaInfoBox.setVisible(false);
            closeImageButton.setVisible(true);
            uploadImagePath = selectedFile.getPath();
        }
    }

    /**
     * A method to handle when some files are dropped in the pane where you can drop files
     * @param dragEvent
     */
    public void handleFilesDropped(DragEvent dragEvent){
        Dragboard db = dragEvent.getDragboard();
        Boolean success = false;
        if(db.hasFiles()){
            File uploadedFile = new File(db.getFiles().get(0).getAbsolutePath());
            uploadedImage = new ImageView(new Image(uploadedFile.toURI().toString()));

            String uploadedImageURL = uploadedFile.toURI().toString();
            previewImagePane.setStyle(
                    "-fx-background-image: url('" + uploadedImageURL + "');" +
                            "-fx-background-position: center center;" +
                            "-fx-background-repeat: no-repeat;" +
                            "-fx-background-origin: padding-box;" +
                            "-fx-background-size: contain;" + //or auto if the image should cover the background, but the size will increase aswell.
                            "-fx-background-radius: 15;" +
                            "-fx-border-radius: 15;");
            uploadImagePath = uploadedFile.getPath();
            dragAreaInfoBox.setVisible(false);
            closeImageButton.setVisible(true);
            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    /**
     * Method for accepting files to be dropped in the pane
     * @param dragEvent
     */
    public void handleFilesDragged(DragEvent dragEvent){
        if(dragEvent.getGestureSource() != previewImagePane && dragEvent.getDragboard().hasFiles()){
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }

    public void handleCloseImageButtonClicked(){
        uploadedImage = null;
        previewImagePane.getChildren().remove(uploadedImage);
        previewImagePane.setStyle("-fx-background-image: null");
        uploadImagePath = "";
        closeImageButton.setVisible(false);
        dragAreaInfoBox.setVisible(true);
    }


    /**
     * Method for handling when the upload button
     * is clicked. Clear all fields if operation is successful
     * @return a boolean
     */
    public void handleUploadButtonClicked(){
        boolean uploadingSuccessful = false;


        boolean inputsOk = checkIfInputsAreOk();
        if(inputsOk){
            collectImageTags();
            uploadingSuccessful = uploadImage();
        }

        if(uploadingSuccessful){
            InformationDialog.showInformationDialog("Image uploaded", "Your image is now uploaded!");
            clearAllFields();
        }

    }

    /**
     * Checks if the inputs are ok, if not show a explaining dialog
     * @return boolean
     */
    private boolean checkIfInputsAreOk(){
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
        return true;
    }

    /**
     * Collects all tags from the listview and put them into a string
     */
    private void collectImageTags(){
        ArrayList<String> tagsList = collectListViewTags();
        for(int i = 0; i < tagsList.size(); i ++){
            tags += tagsList.get(i) + " ";
        }
    }

    /**
     * Uploads the image to the database
     * @return
     */
    private boolean uploadImage(){
        ImageV2DAO imageV2DAO = new ImageV2DAO(DatabaseConnection.getInstance().getEntityManagerFactory());

        ImageV2 uploadImage = null;
        try {
            uploadImage = new ImageV2(imageNameTextField.getText(), tags, uploadImagePath);
            imageV2DAO.storeNewImage(uploadImage, (Album) albumChoiceBox.getValue());
            return true;
        } catch (MetadataException e) {
            InformationDialog.showInformationDialog("Error", "Oops! Something went wrong. Check all fields and try again.");
            return false;
        }
    }



    /**
     * Method for handling when the "+" button is
     * clicked. Adds tag then clears the input field.
     */
    public void handleAddTagButtonClicked(){
        addTagToList();
    }

    private void addTagToList(){
        if(!tagTextField.getText().equals("")) {
            String tag = tagTextField.getText();
            tagListView.getItems().add(tag);
            tagTextField.clear();
        }
    }

    private void initializeTagTextFieldListener(){
        tagTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent keyEvent){
                if(keyEvent.getCode() == KeyCode.ENTER){
                    addTagToList();
                }
            }
        });
    }

    /**
     * Handles create album button clicked. Shows a dialog
     * where user can write the name of the new album.
     */
    public void handleCreateAlbumButton(){
        String albumName;
        AlbumDAO albumDAO = new AlbumDAO(DatabaseConnection.getInstance().getEntityManagerFactory());

        TextInputDialog albumDialog = new TextInputDialog();
        albumDialog.setTitle("Create new album");
        albumDialog.setHeaderText("Create a new album");
        albumDialog.setContentText("Please enter album name: ");

        Optional<String> result = albumDialog.showAndWait();

        if(result.isPresent() && !result.get().equals("")){
            albumName = result.get();
            boolean operationSuccessful = albumDAO.storeNewAlbum(new Album(albumName));
            if(operationSuccessful) {
                InformationDialog.showInformationDialog("Album created", "Album created successfully!");
                ArrayList<Album> albumList = (ArrayList<Album>) albumDAO.getAlbums();
                reloadAlbumChoiceBox(albumList);
            }
            else {
                InformationDialog.showInformationDialog("Creating album failed", "Oops. Something went wrong!");
            }
        }
    }


    /**
     * Method for reloading the album choicebox
     * @param albums new list to be loaded into choicebox
     */
    private void reloadAlbumChoiceBox(ArrayList<Album> albums){
        albumChoiceBox.getItems().clear();
        for(Album album : albums){
            albumChoiceBox.getItems().add(album);
        }
        albumChoiceBox.setValue(albums.get(albums.size()-1));
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
        try {
            stage.getScene().setRoot(mainSceneLoader.load());
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
        tags = "";
        previewImagePane.getChildren().remove(uploadedImage);
        previewImagePane.setStyle("-fx-background-image: null");
        imageNameTextField.clear();
        dragAreaInfoBox.setVisible(true);
        closeImageButton.setVisible(false);
    }

    /**
     * Method for loading all the albums from database into the album choice box
     */
    private void loadAlbumChoiceBox(){
        AlbumDAO albumDAO = new AlbumDAO(DatabaseConnection.getInstance().getEntityManagerFactory());
        ArrayList<Album> albums = (ArrayList<Album>) albumDAO.getAlbums();
        for(Album album : albums){
            albumChoiceBox.getItems().add(album);
        }
        albumChoiceBox.setValue(albums.get(0));
    }


    /**
     * Initialize method that gets run when the scene is loaded.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTagTextFieldListener();
        closeImageButton.setVisible(false);
        loadAlbumChoiceBox();
        try {
            Node windowMenuButtonsNode = FXMLLoader.load(getClass().getResource("../WindowMenuButtons/WindowMenuButtons.fxml"));
            windowMenuButtonBox.getChildren().add(windowMenuButtonsNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
