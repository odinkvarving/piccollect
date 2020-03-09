package sample.Java;

import java.io.File;
import java.util.ArrayList;

public class Image {
    //An arrayList with tags that contains tags from user to
    // filter images.
    ArrayList<String> tags;
    //A string with the path to the file
    File file;
    //An object with metaData to the picture
    ImageMetaData metaData;


    public Image(ArrayList<String> userTags, String filepath){
        this.tags = userTags;
        this.file = new File(filepath);

    }

}
