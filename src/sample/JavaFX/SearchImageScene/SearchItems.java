package sample.JavaFX.SearchImageScene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Java.ImageV2;

import java.io.File;
import java.util.Date;

public class SearchItems {

    private ImageView imageView;
    private String imageName;
    private String location;
    private Date date;
    private String tags;

    public SearchItems(ImageV2 imageV2){
        File imageFile = new File(imageV2.getFilePath());
        this.imageView = new ImageView(new Image(imageFile.toURI().getPath()));
        this.imageName = imageV2.getImageName();
        this.location = imageV2.getLocation();
        this.date = imageV2.getDate();
        this.tags = imageV2.getTags();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getImageName() {
        return imageName;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public String getTags() {
        return tags;
    }
}
