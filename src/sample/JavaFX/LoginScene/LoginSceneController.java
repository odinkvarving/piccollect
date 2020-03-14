package sample.JavaFX.LoginScene;

import javafx.scene.Node;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class LoginSceneController {

    public void handleLoginClick(ActionEvent event){
        Stage sourceStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}
