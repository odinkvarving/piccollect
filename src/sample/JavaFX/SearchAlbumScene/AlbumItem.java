package sample.JavaFX.SearchAlbumScene;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    }

    private void createButtonLayout(){
        setContentDisplay(ContentDisplay.TOP);
        getChildren().add(this.imageView);
    }
}
