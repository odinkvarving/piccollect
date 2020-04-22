package sample.JavaFX.SearchAlbumScene;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Java.Album;
import sample.Java.AlbumDAO;
import sample.Java.DatabaseConnection;
import sample.Java.ImageV2;
import sample.JavaFX.ResponseDialogs.InformationDialog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * SearchAlbumController covers all methods which are needed to run AlbumScene properly.
 * The class implements Initializable
 */

public class SearchAlbumController implements Initializable {

    /**
     * These are the object variables of SearchAlbumController.
     * albumSearchInput stores the input from the user.
     * albumChoiceBox lists all albums.
     * albumSearchButton makes the controller display the album which the user searched for.
     * albumTableView displays all albums or the album the user has searched for.
     * nameColumn is a column for albumName in albumTableView.
     * albumColumn is a column which should display the first image of the album.
     * albumRegister is just a test register, which will be replaced by a database or something.
     * albums is an ArrayList containing all albums
     */
    @FXML
    private Pane windowMenuButtonsBox;
    @FXML
    private ChoiceBox albumChoiceBox;
    @FXML
    private ComboBox<String> albumComboBox;
    @FXML
    Button albumSearchButton;
    @FXML
    TableView<Album> albumTableView;
    @FXML
    VBox albumOverview;
    @FXML
    TableColumn<Album, String> nameColumn;
    @FXML
    TableColumn<Album, ArrayList<ImageV2>> albumColumn;
    @FXML
    ScrollPane albumsOverviewScrollPane;
    @FXML
    private StackPane imagePreviewPane;

    private ArrayList<Album> albums;
    private ArrayList<Album> albumsWithoutImageList;
    Image img = new Image(new File("src/sample/JavaFX/resources/imageNotFound.png").toURI().toString());
    ImageView imageNotFound = new ImageView(img);



    /**
     * This method runs when user presses "Search Album" in main scene. It initializes the class, and fills albumTableView with all albums in Database.
     * @param url Location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle Used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AlbumDAO albumDAO = new AlbumDAO(DatabaseConnection.getInstance().getEntityManagerFactory());
        albums = (ArrayList<Album>) albumDAO.getAlbumsAndTheirImages();
        albumsWithoutImageList = (ArrayList<Album>) albumDAO.getAlbums();
        loadAlbumComboBox();
        makeAlbumOverview();
        try {
            Node windowMenuButtonsNode = FXMLLoader.load(getClass().getResource("../WindowMenuButtons/WindowMenuButtons.fxml"));
            windowMenuButtonsBox.getChildren().add(windowMenuButtonsNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method runs when albumSearchButton is clicked. It will check if the album selected in albumComboBox is equal to any of the album names.
     * If it is, the method will clear the albumOverview, and only add the correct album to the albumOverview.
     * If none of the albums have the equivalent name, the method will display an alert.
     */
    @FXML
    private void handleAlbumSearchButtonClicked(){
        String pathImageNotFound = "src/sample/JavaFX/resources/imageNotFound.png";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Text field empty");
        alert.setContentText("You must type in a correct album name");

        String albumChoice = albumComboBox.getValue();
        boolean albumComboBoxIsEmpty = albumComboBox.getSelectionModel().isEmpty();

        if(albumComboBoxIsEmpty){
            alert.show();
        }
        else{
            for(Album album: albums){
                if(albumChoice.equals("none")){
                    alert.show();
                    break;
                }
                else if(albumChoice.equalsIgnoreCase(album.getAlbumName())){
                    AlbumItem albumItem;
                    albumOverview.getChildren().clear();
                    if(album.getImages().isEmpty()){
                        albumItem = new AlbumItem(pathImageNotFound, album.getAlbumName());
                    }
                    else{
                        albumItem = new AlbumItem(album.getImages().get(0).getFilePath(), album.getAlbumName());
                    }
                    albumOverview.getChildren().add(albumItem);

                    albumItem.setOnMouseClicked(e -> {
                        loadAllPicturesFromAlbum(album.getAlbumName());
                    });
                    break;
                }
            }
        }
    }

    /**
     * A method for displaying every album in the Database.
     * The display is a VBox with multiple HBoxes, depending on how many albums the Database contains.
     * Each albumRow contains up to 3 albumItems.
     * If there are no pictures in an album, the albumItem displays a default image: imageNotFound, and albumName.
     * Else, the albumItem displays the first image in the album and albumName.
     */
    private void makeAlbumOverview(){
        AlbumDAO albumDAO = new AlbumDAO(DatabaseConnection.getInstance().getEntityManagerFactory());
        String pathImageNotFound = "src/sample/JavaFX/resources/imageNotFound.png";
        int amountRows = albumsWithoutImageList.size()/3;
        int difference = 0;
        if(albumsWithoutImageList.size()%3 != 0){
            difference = 1;
        }
        int counter = 1;
        for(int i = 0; i < (amountRows+difference); i++){
            HBox albumRow = new HBox();
            albumRow.setAlignment(Pos.CENTER);
            albumRow.setSpacing(30);
            albumRow.setPrefWidth(500);
            int rowCounter = 0;
            while(counter < albumsWithoutImageList.size() && rowCounter < 3){
                AlbumItem albumItem;
                if(!(counter >= albumsWithoutImageList.size())) {
                    try {
                        albumItem = new AlbumItem(albumDAO.getAlbumsFirstImagePath(albumsWithoutImageList.get(counter)), albumsWithoutImageList.get(counter).getAlbumName());
                    }catch (Exception e){
                            albumItem = new AlbumItem("", albumsWithoutImageList.get(counter).getAlbumName());
                    }
                    albumRow.getChildren().add(albumItem);
                    int finalCounter = counter;
                    albumItem.setOnMouseClicked(e -> {
                        loadAllPicturesFromAlbum(albumsWithoutImageList.get(finalCounter).getAlbumName());
                    });
                }
                counter++;
                rowCounter++;
            }
            if(albumRow.getChildren().size() < 3){
                albumRow.setAlignment(Pos.CENTER_LEFT);
            }
            albumOverview.getChildren().add(albumRow);
        }
    }

    /**
     * Method for resetting albumOverview. The method clears albumOverview and runs the method makeAlbumOverview().
     */
    @FXML
    private void handleAlbumResetButtonClicked(){
        albumOverview.getChildren().clear();
        makeAlbumOverview();
    }

    /**
     * Method for loading all the albums from database into the album choice box
     */
    private void loadAlbumComboBox(){
        for(Album album : albumsWithoutImageList){
            albumComboBox.getItems().add(album.getAlbumName());
        }
    }

    /**
     * Method for showing all images in a chosen album. If the album contains zero images, the user will be notified by text in albumOverview.
     * @param albumName: albumName is used to check what album was clicked
     */
    private void loadAllPicturesFromAlbum(String albumName){
        for(int j = 0; j < albums.size(); j ++){
            if(albumName.equals(albums.get(j).getAlbumName())) {
                    albumOverview.getChildren().clear();
                    int amountRows = albums.get(j).getImages().size() / 3;
                    int difference = 0;
                    int counter = 0;
                    if (albums.get(j).getImages().size() % 3 != 0) {
                        difference = 1;
                    }
                    for (int i = 0; i < (amountRows + difference); i++) {
                        HBox imageRow = new HBox();
                        int rowCounter = 0;
                        while (counter < albums.get(j).getImages().size() && rowCounter < 3) {
                            ImageItem imageItem;
                            if (albums.get(j).getImages().get(counter).getImageName().equals("") || albums.get(j).getImages().get(counter).getImageName() == null) {
                                imageItem = new ImageItem(albums.get(j).getImages().get(counter).getFilePath(), "No name", imagePreviewPane);
                            } else {
                                imageItem = new ImageItem(albums.get(j).getImages().get(counter).getFilePath(), albums.get(j).getImages().get(counter).getImageName(), imagePreviewPane);
                            }
                            imageRow.getChildren().add(imageItem);
                            counter++;
                            rowCounter++;
                        }
                        albumOverview.getChildren().add(imageRow);
                    }
            }
        }
    }

    /**
     * A back button which will take the user back to the home page.
     */
    @FXML
    private Button backButton;

    /**
     * This method will take the user back to the home page when backButton is clicked.
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
}
