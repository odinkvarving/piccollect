package sample.JavaFX.SearchAlbumScene;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Java.Album;

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
    //TableColumn<Album, ArrayList<Image>> albumColumn;
    //AlbumRegister albumRegister = new AlbumRegister(new ArrayList<Album>(new Album("Dog", new ArrayList<Image>())));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAlbumTableViewColumnValues();
    }

    @FXML
    private void handleAlbumSearchButtonClicked(){
        if(!albumSearchInput.getText().equals("")){
            String albumName = albumSearchInput.getText();
        }
    }

    private void setAlbumTableViewColumnValues(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<Album, String>("albumName"));
        //albumColumn.setCellValueFactory(new PropertyValueFactory<Album, ArrayList<Image>>("images"));
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
