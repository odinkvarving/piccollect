package sample.JavaFX.WindowMenuButtons;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class WindowMenuButtonsController {



    @FXML
    private Button exitButton;
    @FXML
    private Button sizeButton;
    @FXML
    private Button hideButton;



    public void handleExitButton(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Application");
        alert.setHeaderText("Exit");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    public void handleSizeButton(){

    }

    public void handleHideButton(){
        Stage stage = (Stage) hideButton.getScene().getWindow();
        stage.hide();
    }
}
