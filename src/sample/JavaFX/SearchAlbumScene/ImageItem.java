package sample.JavaFX.SearchAlbumScene;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * ImageItem is a class which extends Button to apply a button's traits.
 * An ImageItem is an ImageView with the name below it.
 * We did the same with AlbumItem, and thought it could work here as well.
 */

public class ImageItem extends Button {

    /**
     * imageView is the ImageView which is presented in the given ImageItem.
     */
    private ImageView imageView;

    /**
     * Here we have a constructor which takes in the image path and the image name as parameters.
     * @param path: image path
     * @param name: image name
     */
    public ImageItem(String path, String name){
        this.imageView = new ImageView(new Image(new File(path).toURI().toString()));
        setText(name);
        createButtonLayout();
        setImageViewProperties();
    }

    /**
     * This method creates the layout of the button.
     * The method is responsible for how ImageItem looks.
     */
    private void createButtonLayout(){
        setContentDisplay(ContentDisplay.TOP);
        setPrefSize(150, 160);
        setStyle("-fx-background-color: #ffffff");
        setGraphic(imageView);
        setPadding(new Insets(20, 20, 20, 20));
    }

    /**
     * This method sets image view properties, such as width and height.
     */
    private void setImageViewProperties(){
        this.imageView.setFitWidth(150);
        this.imageView.setFitHeight(150);
        this.imageView.isPickOnBounds();
        this.imageView.isPreserveRatio();
    }
}
