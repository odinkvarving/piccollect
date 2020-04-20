package sample.JavaFX.SearchAlbumScene;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;

import java.io.File;

public class AlbumItem extends Button {
    private ImageView imageView;

    private File imageNotFoundFile = new File("src/sample/JavaFX/resources/imageNotFound.png");
    private ImageView imageNotFound = new ImageView(new Image(imageNotFoundFile.toURI().toString()));

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

    private void createButtonLayout(){
        setContentDisplay(ContentDisplay.TOP);
        setPrefSize(150, 160);
        setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5px; -fx-border-style: solid; -fx-padding: 20px");
        setGraphic(imageView);
        setPadding(new Insets(20, 20, 20, 20));
    }

    private void createHoverEffect(){
        setOnMouseEntered(e -> {
            setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #000000; -fx-border-radius: 5px; -fx-border-style: solid; -fx-padding: 20px;-fx-cursor: hand");
        });
        setOnMouseExited(e -> {
            setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5px; -fx-border-style: solid; -fx-padding: 20px");
        });
    }

    private void setImageViewProperties(){
        this.imageView.setFitWidth(150);
        this.imageView.setFitHeight(150);
        this.imageView.isPickOnBounds();
        this.imageView.isPreserveRatio();
    }
}
