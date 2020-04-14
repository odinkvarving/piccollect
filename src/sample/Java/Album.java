package sample.Java;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Album class is a class which makes an Album object.
 */
@Entity
@Table(name="album_table")
public class Album {

    /**
     * Each album has its own unique id.
     */
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Each album has a name.
     */
    @Column(name = "album_name")
    private String albumName;


    /**
     * Each album has a List containing images.
     */
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

    public void addImage(ImageV2 imageV2){
        this.images.add(imageV2);
        imageV2.getAlbums().add(this);
    }
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

