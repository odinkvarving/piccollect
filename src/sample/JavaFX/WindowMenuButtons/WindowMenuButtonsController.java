package sample.JavaFX.WindowMenuButtons;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * WindowsMenyButtonsController controls the window-menu-buttons
 */

public class WindowMenuButtonsController {

    /**
     * exitButton is a button the user can click to exit the application
     * sizeButton is a button the user can click to minimize or enlarge the application
     * hideButton is a button the user can click to hide the application
     */
    @FXML
    private Button exitButton;
    @FXML
    private Button sizeButton;
    @FXML
    private Button hideButton;


    /**
     * Method for showing a dialog that gets confirmation about closing the program. And closes the program
     * if the user selects ok.
     */
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

    /**
     * Makes the program fullscreen if the program is small and vise versa.
     */
    public void handleSizeButton(){
        Stage stage = (Stage) sizeButton.getScene().getWindow();
        if(stage.isFullScreen()){
            stage.setFullScreen(false);
        }
        else {
            stage.setFullScreen(true);
        }
    }

    /**
     * Hides the program.
     */
    public void handleHideButton(){
        Stage stage = (Stage) hideButton.getScene().getWindow();
        stage.setIconified(true);
    }
}
