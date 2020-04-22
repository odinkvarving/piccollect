package sample.JavaFX.SearchAlbumScene;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * AlbumItem is a class which extends Button to apply a button's traits.
 * An AlbumItem is an ImageView with the first image of the album, and the album name below it.
 */
public class AlbumItem extends Button {
    /**
     * imageView is the ImageView which is presented in the given AlbumItem.
     * imageNotFoundFile is the path to a default image (see below)
     * imageNotFound is the ImageView which is shown if there are no images in the given album
     */
    private ImageView imageView;
    private File imageNotFoundFile = new File("src/sample/JavaFX/resources/imageNotFound.png");
    private ImageView imageNotFound = new ImageView(new Image(imageNotFoundFile.toURI().toString()));

    /**
     * The constructor takes the path for image nr 0 and album name as parameters
     * @param path: image nr 0 path
     * @param albumName: album name
     */
    public AlbumItem(String path, String albumName){
        try{
            this.imageView = new ImageView(new Image(new File(path).toURI().toString()));
        }
        catch (NullPointerException e){
            this.imageView = imageNotFound;
        }
        setText(albumName);
        createButtonLayout();
        createHoverEffect();
        setImageViewProperties();

    }

    /**
     * This method creates the layout of the button.
     * The method is responsible for how AlbumItem looks.
     */
    private void createButtonLayout(){
        setContentDisplay(ContentDisplay.TOP);
        setPrefSize(150, 160);
        setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5px; -fx-border-style: solid; -fx-padding: 20px");
        setGraphic(imageView);
        setPadding(new Insets(20, 20, 20, 20));
    }

    /**
     * This method changes the look of the AlbumItem, when the cursor hovers over it
     */
    private void createHoverEffect(){
        setOnMouseEntered(e -> {
            setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #000000; -fx-border-radius: 5px; -fx-border-style: solid; -fx-padding: 20px;-fx-cursor: hand");
        });
        setOnMouseExited(e -> {
            setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5px; -fx-border-style: solid; -fx-padding: 20px");
        });
    }

    /**
     * This method sets image view properties, such as width and height
     */
    private void setImageViewProperties(){
        this.imageView.setFitWidth(150);
        this.imageView.setFitHeight(150);
        this.imageView.isPickOnBounds();
        this.imageView.isPreserveRatio();
    }
}
