package sample.Java;

import com.drew.metadata.MetadataException;

import java.util.ArrayList;
import java.util.Collections;

public class test {
    public static void main(String[] args) throws MetadataException {

        FileChooser fileChooser = new FileChooser();

        ArrayList<String> tags = new ArrayList<>();
        ArrayList<String> tags1 = new ArrayList<>();
        tags.add("picklerick");
        tags1.add("picklerick");
        tags1.add("test");

        String path = fileChooser.browseFiles();
        if(path ==null) {
            System.out.println("you did not choose an image");
        }
        Image image = new Image(tags,path);
        // image1 = new Image(tags,fileChooser.browseFiles());

        ArrayList<Image> images = new ArrayList<Image>();
        images.add(image);
        //images.add(image1);

        Album album = new Album("album", images);

        System.out.println(image.getName());
        //System.out.println(image1.getName());
        System.out.println(image.getDate());
    }
}
