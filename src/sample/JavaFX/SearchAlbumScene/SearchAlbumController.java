package sample.JavaFX.SearchAlbumScene;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class SearchAlbumController {
    @FXML
    TextField albumSearchInput;
    @FXML
    Button albumSearchButton;

    @FXML
    private void handleButtonAction(ActionEvent event){
        System.out.println("HALLA");
    }

}
