package sample.JavaFX.SearchAlbumScene;


import com.drew.metadata.MetadataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Java.Album;
import sample.Java.AlbumDAO;
import sample.Java.DatabaseConnection;
import sample.Java.ImageV2;
import sample.Main;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


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
    private AlbumDAO albumDAO = new AlbumDAO(DatabaseConnection.getInstance().getEntityManagerFactory());
    private ArrayList<Album> albums = (ArrayList<Album>) albumDAO.getAlbums();
    Image img = new Image(new File("src/sample/JavaFX/resources/imageNotFound.png").toURI().toString());
    ImageView imageNotFound = new ImageView(img);


    /**
     * This method runs when user presses "Search Album" in main scene. It initializes the class, and fills albumTableView with all albums in Database.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAlbumComboBox();
        makeAlbumOverview();
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
                else if(albumChoice.equals(album.getAlbumName())){
                    AlbumItem albumItem;
                    albumOverview.getChildren().clear();
                    if(album.getImages().isEmpty()){
                        albumItem = new AlbumItem(pathImageNotFound, album.getAlbumName());
                    }
                    else{
                        albumItem = new AlbumItem(album.getImages().get(0).getFilePath(), album.getAlbumName());
                    }
                    albumOverview.getChildren().add(albumItem);
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
        String pathImageNotFound = "src/sample/JavaFX/resources/imageNotFound.png";
        int amount = albums.size()/3;
        int difference = 0;
        if(albums.size()%3 != 0){
            difference = 1;
        }
        int counter = 1;
        for(int i = 0; i < (amount+difference); i++){
            HBox albumRow = new HBox();
            int rowCounter = 0;
            while(counter < albums.size() && rowCounter < 3){
                AlbumItem albumItem;
                if(albums.get(counter).getImages().isEmpty()){
                    albumItem = new AlbumItem(pathImageNotFound, albums.get(counter).getAlbumName());
                }
                else{
                    albumItem = new AlbumItem(albums.get(counter).getImages().get(0).getFilePath(), albums.get(counter).getAlbumName());
                }
                albumRow.getChildren().add(albumItem);
                counter++;
                rowCounter++;
            }
            albumOverview.getChildren().add(albumRow);
        }
    }

    @FXML
    private void handleAlbumResetButtonClicked(){
        albumOverview.getChildren().clear();

    }

    /**
     * Method for loading all the albums from database into the album choice box
     */
    private void loadAlbumComboBox(){
        for(Album album : albums){
            albumComboBox.getItems().add(album.getAlbumName());
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
        Scene scene;
        try {
            scene = new Scene(mainSceneLoader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
