package sample.JavaFX.SearchAlbumScene;


import com.drew.metadata.MetadataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    Button albumSearchButton;
    @FXML
    TableView<Album> albumTableView;
    @FXML
    TableColumn<Album, String> nameColumn;
    @FXML
    TableColumn<Album, ArrayList<ImageV2>> albumColumn;
    private AlbumDAO albumDAO = new AlbumDAO(DatabaseConnection.getInstance().getEntityManagerFactory());
    private ArrayList<Album> albums = (ArrayList<Album>) albumDAO.getAlbums();


    /**
     * This method runs when user presses "Search Album" in main scene. It initializes the class, and fills albumTableView with all albums in Database.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAlbumChoiceBox();
        setAlbumTableViewColumnValues();
        albumTableView.setItems(fillAlbumTableView());
    }

    /**
     * This method runs when albumSearchButton is clicked. It will check if the album selected in albumChoiceBox is equal to any of the album names.
     * If it is, the method will clear the albumTableView, and only add the correct album to the albumTableView.
     * If none of the albums have the equivalent name, the method will display an alert.
     */
    @FXML
    private void handleAlbumSearchButtonClicked(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Text field empty");
        alert.setContentText("You must type in a correct album name");

        String albumChoice = (String) albumChoiceBox.getValue();
        boolean albumChoiceBoxIsEmpty = albumChoiceBox.getSelectionModel().isEmpty();

        if(albumChoiceBoxIsEmpty){
            alert.show();
        }
        else{
            for(Album album: albums){
                if(albumChoice.equals("none")){
                    alert.show();
                    break;
                }
                if(albumChoice.equals(album.getAlbumName())){
                    albumTableView.getItems().clear();
                    albumTableView.getItems().add(album);
                    System.out.println(albumChoice);
                    break;
                }
            }
        }
    }

    /**
     * This method sets values to the columns in albumTableView.
     */
    private void setAlbumTableViewColumnValues(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<Album, String>("albumName"));
        //albumColumn.setCellValueFactory(new PropertyValueFactory<Album, ArrayList<ImageV2>>("images"));
    }

    /**
     * This method will make an ObservableList containing all albums in the Database.
     * @return albums: ObservableList of albums
     */
    private ObservableList<Album> fillAlbumTableView(){
        ObservableList<Album> albums = FXCollections.observableArrayList();
        //albumRegister.getAlbums().forEach(a -> albums.add(a));
        return albums;
    }

    /**
     * Method for loading all the albums from database into the album choice box
     */
    private void loadAlbumChoiceBox(){
        for(Album album : albums){
            albumChoiceBox.getItems().add(album.getAlbumName());
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
