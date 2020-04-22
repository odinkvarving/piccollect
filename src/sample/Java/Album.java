package sample.Java;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Album is a class that is used for making an Album-object, and is defined as an entity, so that objects of this class can be mapped to a relation database-table
 * @Entity Used for defining objects of this class as an entity
 * @Table Defines the name of the table that correlates with the name in our database
 * @Id Defines the primary key as an Id-column
 * @Column Used for defining information about the column
 * @GeneratedValue Defines how this value is generated
 * @ManyToMany Used to define what relationship this class has with other entities
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
     * We need an empty constructor for Album. If not, an error will occur right below "public class Album".
     */
    public Album(){}

    /**
     * We also have another constructor that takes albumName as a parameter.
     * @param albumName: Album name
     */
    public Album(String albumName){
        this.albumName = albumName;
    }

    /**
     * Below there are some getters and setters for each object variable. //DO WE NEED JAVADOC FOR EACH GETTER AND SETTER METHOD???
     * @return the equivalent object variable
     */
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
     * @param imageV2 Defines what image should be added
     */
    public void addImage(ImageV2 imageV2){
        this.images.add(imageV2);
        imageV2.getAlbums().add(this);
    }

    /**
     * Method for removing images from an album
     * @param imageV2 Defines what image should be removed
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

