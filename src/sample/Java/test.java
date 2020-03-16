package sample.Java;

import java.util.ArrayList;
import java.util.Collections;

public class test {
    public static void main(String[] args) {

        Image image = new Image(new ArrayList<String>(Collections.singleton("picklerick")), ":\\Users\\haava\\OneDrive\\Dokumenter\\Progging\\Piccollect\\piccollect\\src\\sample");

        ArrayList<Image> images = new ArrayList<Image>();
        images.add(image);

        Album album = new Album("album", images);

        System.out.println(image.getMetaData());
        System.out.println(album.findImagesByTag("picklerick"));
    }
}
