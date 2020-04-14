package sample.JavaFX.SearchAlbumScene;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class ImageItem extends Button {
    private ImageView imageView;

    public ImageItem(String path, String name){
        this.imageView = new ImageView(new Image(new File(path).toURI().toString()));
        setText(name);
        createButtonLayout();
        setImageViewProperties();
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
}
