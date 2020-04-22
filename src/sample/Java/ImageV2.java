package sample.Java;


import com.drew.lang.GeoLocation;
import com.drew.metadata.MetadataException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.persistence.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ImageV2 is a class that is used for making an ImageV2-object, and is defined as an entity, so that objects of this class can be mapped to a relation database-table
 * @Entity Used for defining objects of this class as an entity
 * @Table Defines the name of the table that correlates with the name in our database
 * @Id Defines the primary key as an Id-column
 * @Column Used for defining information about the column
 * @GeneratedValue Defines how this value is generated
 * @ManyToMany Used to define what relationship this class has with other entities
 */
@Entity
@Table(name = "image_table")
public class ImageV2 {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="image_name")
    private String imageName;

    @Column(name="tags")
    private String tags;

    @Column(name="width")
    private int width;

    @Column(name="height")
    private int height;

    @Column(name="filepath")
    private String filePath;

    @Column(name="location")
    private String location;

    @Column(name="date")
    private Date date;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Album> albums = new ArrayList<>();

    public ImageV2(){}

    /**
     * The constructor for this class
     * @param imageName the name of the image
     * @param tags the tags of the image
     * @param filePath the file-path of the image
     * @throws MetadataException the exception that is thrown
     * Some of the object-variables is defined using methods from external libraries - like location and date, which uses the GeoConverter API and the ImageMetaData API
     * There is also created a ImageMetaData-object using the same filepath, so that we gain access to the metadata stored in the image.
     */

    public ImageV2(String imageName, String tags, String filePath) throws MetadataException {
        ImageMetaData imageMetaData = new ImageMetaData(new File(filePath));
        this.imageName = imageName;
        this.tags = tags;
        this.filePath = filePath;
        this.width = imageMetaData.getWidthFromMetadata();
        this.height = imageMetaData.getHeightFromMetadata();
        try {
            this.location = GeoConverter.reverseGeocoder(imageMetaData.getGeoDataFromMetadata().getLatitude(), imageMetaData.getGeoDataFromMetadata().getLongitude());
        }catch (NullPointerException e){
            this.location = "No location found";
        }
        this.date = imageMetaData.getDateFromMetaData();
    }

    /**
     * Method for creating an ImageView, which is used to display the image in a certain way
     * @return a pre-defined image if no file is found, and the image itself if it is found
     */
    public ImageView getImage(){
        Image image;
        ImageView imageView;
        try {
            FileInputStream input = new FileInputStream(filePath);
            image = new Image(input, 50, 50, true, false);
            imageView = new ImageView(image);

        } catch (FileNotFoundException e) {
            Image imageError = new Image(new File("src/sample/JavaFX/resources/imageNotFound.png").toURI().toString(), 50, 50, true, false);
            ImageView imageNotFound = new ImageView(imageError);
            return imageNotFound;
        }
        return imageView;
    }

    /**
     * Method for adding the image to a album
     * @param album the album that is added
     */
    public void addAlbum(Album album){
        this.albums.add(album);
        album.getImages().add(this);
    }

    /**
     * Method for removing an image from a specified album
     * @param album the album that is removed
     */
    public void removeAlbum(Album album){
        this.albums.remove(album);
        album.getImages().remove(this);
    }

    /**
     * Method for extracting the GeoLocation from an image
     * @return the GeoLocation of the image
     */
    public GeoLocation getGeoLocation(){
        ImageMetaData imageMetaData;
        try {
            imageMetaData = new ImageMetaData(new File(filePath));
            return imageMetaData.getGeoDataFromMetadata();
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public List<Album> getAlbums() {
        return albums;
    }
}
