package sample.Java;

import com.drew.lang.GeoLocation;
import com.drew.metadata.MetadataException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

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

    private String name;
    private GeoLocation location;
    private Date date;

    public Image(ArrayList<String> userTags, String filepath) throws MetadataException {
        this.file = new File(filepath);
        this.metaData = new ImageMetaData(file);

        this.width = metaData.getWidthFromMetadata();
        this.height = metaData.getHeightFromMetadata();

        this.tags = userTags;
        this.name = file.getName();
        this.date = metaData.getDateFromMetaData();
        this.location = metaData.getGeoDataFromMetadata();
    }

    /**
     * A method for checking if the image contains the tag
     * @return true or false based on
     */
    public boolean checkTag(String tag) {
        if (tag == null) {
            return false;
        }

        if(this.tags.contains(tag)){
            return true;
        }
        return false;
    }

    /**
     * Overwriting the equals method to compare two images
     * @param o
     * @return true or false
     */
    public boolean equals(Object o) {
        if (!(o instanceof Image)) {
            return false;
        }
        if(o == null) {
            return false;
        }
        Image image = (Image) o;
        return this.file.equals(image.getFile());
    }

    /**
     * Getters and setters for all the attributes
     * @return the attributes
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ImageMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(ImageMetaData metaData) {
        this.metaData = metaData;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
