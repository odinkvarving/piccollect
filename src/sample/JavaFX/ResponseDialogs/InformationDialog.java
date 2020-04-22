package sample.JavaFX.ResponseDialogs;

import javafx.scene.control.Alert;

/**
 * InformationDialog is a class which makes a dialog window.
 * This class can be used whenever an information dialog box is needed, because it creates a general information dialog box.
 */

public class InformationDialog {

    /**
     * This method is used to create the dialog box
     * @param title: Title of the alert
     * @param contentText: The text of the alert
     */
    public static void showInformationDialog(String title, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        alert.showAndWait();
    }
}
