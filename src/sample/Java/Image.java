package sample.Java;

import com.drew.metadata.MetadataException;

import java.io.File;
import java.util.ArrayList;

public class Image {
    //An arrayList with tags that contains tags from user to
    // filter images.
    private ArrayList<String> tags;
    //A string with the path to the file
    private File file;
    //An object with metaData to the picture
    private ImageMetaData metaData;

    private int width;
    private int height;

    public Image(ArrayList<String> userTags, String filepath) throws MetadataException {
        this.tags = userTags;
        this.file = new File(filepath);
        this.metaData = new ImageMetaData(file);

        this.width = metaData.getWidthFromMetadata();
        this.height = metaData.getHeightFromMetadata();
    }
}
