package sample.Java;

import com.drew.metadata.MetadataException;

import java.util.ArrayList;
import java.util.Collections;

public class test {
    public static void main(String[] args) throws MetadataException {

        FileChooser f1 = new FileChooser();
        ArrayList<String> tags = new ArrayList<>();
        ArrayList<String> tags1 = new ArrayList<>();
        tags.add("picklerick");
        tags1.add("picklerick");
        tags1.add("test");

        Image image = new Image(tags,f1.browseFiles());
        Image image1 = new Image(tags,f1.browseFiles());


        ArrayList<Image> images = new ArrayList<Image>();
        images.add(image);
        images.add(image1);

        Album album = new Album("album", images);

        System.out.println(image.getName());
        System.out.println(image1.getName());
        System.out.println(album.findImagesByTag("picklerick"));
        System.out.println(album.findImagesByTag("test"));
    }
}
