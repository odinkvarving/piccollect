package sample.JavaFX.ResponseDialogs;

import javafx.scene.control.Alert;

public class InformationDialog {

    public static void showInformationDialog(String title, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        alert.showAndWait();
    }
}
