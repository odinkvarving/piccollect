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
        loadAlbumChoiceBox();
        makeAlbumOverview();
        //setAlbumTableViewColumnValues();
        //albumTableView.setItems(fillAlbumTableView());
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
                else if(albumChoice.equals(album.getAlbumName())){
                    albumOverview.getChildren().clear();
                    if(album.getImages().isEmpty()){
                        albumOverview.getChildren().addAll(new Label(album.getAlbumName(), imageNotFound));
                    }
                    else{
                        albumOverview.getChildren().addAll(new Label(album.getAlbumName()), album.getImages().get(0).getImage());
                    }
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

    private void makeAlbumOverview(){
        /*GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        int albumAmount = albums.size();
        int counter = 0;
        Album album;
        for(int i = 0; i < albums.size()/3; i++){
            for(int j = 0; j < 3; i++){
                album = albums.get(counter);
                gridPane.add(album, j, i);
                counter++;
            }
        }*/

        String pathImageNotFound = "src/sample/JavaFX/resources/imageNotFound.png";
        int albumAmount = albums.size();
        int counter = 0;
        for(int i = 0; i < albumAmount/3; i++){
            HBox albumRow = new HBox();
            for(int j = 0; j < 3; j++){
                AlbumItem albumItem;
                if(albums.get(counter).getImages().isEmpty()){
                    albumItem = new AlbumItem(pathImageNotFound, albums.get(counter).getAlbumName());

                }
                else{
                    albumItem = new AlbumItem(albums.get(counter).getImages().get(0).getFilePath(), albums.get(counter).getAlbumName());
                }
                albumRow.getChildren().add(albumItem);

                /*albumRow.getChildren().add(new Label(albums.get(counter).getAlbumName()));
                if(albums.get(counter).getImages().isEmpty()) {
                    ImageView imgView = new ImageView(img);
                    albumRow.getChildren().add(imgView); //error here
                }
                else{
                    try{
                        albumRow.getChildren().add(albums.get(counter).getImages().get(0).getImage());
                    }
                    catch(NullPointerException e){
                        ImageView imgView = new ImageView(img);
                        albumRow.getChildren().add(imgView);
                    }

                }*/
                /*try{
                    Image image = new Image(albums.get(counter).getImages().get(0).getFilePath());
                    ImageView imageView = new ImageView(image);
                    albumRow.getChildren().addAll(albumName, imageView);
                }
                catch(Exception e){

                }*/

                counter++;
            }
            albumOverview.getChildren().add(albumRow);
        }
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
