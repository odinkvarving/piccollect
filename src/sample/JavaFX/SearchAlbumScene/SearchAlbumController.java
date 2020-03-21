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
import sample.Java.AlbumRegister;
import sample.Java.Image;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class SearchAlbumController implements Initializable {
    @FXML
    TextField albumSearchInput;
    @FXML
    Button albumSearchButton;
    @FXML
    TableView<Album> albumTableView;
    @FXML
    TableColumn<Album, String> nameColumn;
    @FXML
    TableColumn<Album, ArrayList<Image>> albumColumn;
    AlbumRegister albumRegister = new AlbumRegister();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAlbumTableViewColumnValues();
        albumTableView.setItems(fillAlbumTableView());
    }

    @FXML
    private void handleAlbumSearchButtonClicked(){
        for(Album album: albumRegister.getAlbums()){
            if(albumSearchInput.getText().equals(album.getAlbumName())){
                //albums.add(album);
                albumTableView.getItems().clear();
                albumTableView.getItems().add(album);
                break;
            }
        }
        //String albumName = albumSearchInput.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Text field empty");
        alert.setContentText("You must type in a correct album name");
        alert.show();
    }

    private void setAlbumTableViewColumnValues(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<Album, String>("albumName"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<Album, ArrayList<Image>>("images"));
    }

    private ObservableList<Album> fillAlbumTableView(){
        ObservableList<Album> albums = FXCollections.observableArrayList();
        albumRegister.getAlbums().forEach(a -> albums.add(a));
        return albums;
    }

    private void getAlbums(){
        ObservableList<Album> albums = FXCollections.observableArrayList();

    }

    @FXML
    private Button backButton;

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
