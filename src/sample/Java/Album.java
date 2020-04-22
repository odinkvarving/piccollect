package sample.Java;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Album is a class that is used for making an Album-object, and is defined as an entity, so that objects of this class can be mapped to a relation database-table
 * <br>The @Entity is used for defining objects of this class as an entity
 * <br>The @Table defines the name of the table that correlates with the name in our database
 * <br>The @Id defines the primary key as an Id-column
 * <br>The @Column is used for defining information about the column
 * <br>The @GeneratedValue defines how this value is generated
 * <br>The @ManyToMany is used to define what relationship this class has with other entities
 */
@Entity
@Table(name="album_table")
public class Album {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "album_name")
    private String albumName;

    @ManyToMany(mappedBy = "albums")
    private List<ImageV2> images = new ArrayList<>();

    /**
     * Empty constructor
     */
    public Album(){}

    /**
     * Parameterized constructor
     * @param albumName the name of the album
     */
    public Album(String albumName){
        this.albumName = albumName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<ImageV2> getImages() {
        return images;
    }

    /**
     * Method for adding images to an album
     * @param imageV2 the image that will be added
     */
    public void addImage(ImageV2 imageV2){
        this.images.add(imageV2);
        imageV2.getAlbums().add(this);
    }

    /**
     * Method for removing images from an album
     * @param imageV2 the image that will be removed
     */
    public void removeImage(ImageV2 imageV2){
        this.images.remove(imageV2);
        imageV2.getAlbums().remove(this);
    }

    public void setImages(List<ImageV2> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return albumName;
    }
}

