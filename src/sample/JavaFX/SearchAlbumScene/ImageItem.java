package sample.JavaFX.SearchAlbumScene;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.File;

public class ImageItem extends Button {
    private ImageView imageView;
    private ImageView imageViewCopy;

    public ImageItem(String path, String name, StackPane stackPane){
        this.imageView = new ImageView(new Image(new File(path).toURI().toString()));
        this.imageViewCopy = new ImageView(new Image(new File(path).toURI().toString(), 550, 550, true, true));
        setText(name);
        createButtonLayout();
        setImageViewProperties();
        setListenersOnButton(stackPane);
    }

    private void createButtonLayout(){
        setContentDisplay(ContentDisplay.TOP);
        setPrefSize(150, 160);
        setStyle("-fx-background-color: #ffffff");
        setGraphic(imageView);
        setPadding(new Insets(20, 20, 20, 20));
    }

    private void setImageViewProperties(){
        this.imageView.setFitWidth(150);
        this.imageView.setFitHeight(150);
        this.imageView.isPickOnBounds();
        this.imageView.isPreserveRatio();
    }

    private void setListenersOnButton(StackPane stackPane){
        setOnMouseEntered(e -> {
            setStyle("-fx-cursor: hand; -fx-background-color: #f2f2f2; -fx-background-radius: 5");
        });
        setOnMouseExited(e -> {
            setStyle("-fx-cursor: pointer; -fx-background-color: #ffffff");
        });
        setOnAction(e -> {
            createImagePreviewDialog(stackPane);
        });
    }

    private void createImagePreviewDialog(StackPane stackPane){
        JFXDialogLayout dialogLayout = new JFXDialogLayout();

        JFXButton doneButton = new JFXButton("Done");
        doneButton.setStyle("-fx-font-family: 'Montserrat Medium'");
        doneButton.setAlignment(Pos.CENTER);

        JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        doneButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            dialog.close();
            stackPane.toBack();
        });

        dialogLayout.setActions(doneButton);
        dialogLayout.setBody(imageViewCopy);

        stackPane.toFront();
        dialog.show();
    }

}
