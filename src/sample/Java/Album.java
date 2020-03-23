package sample.Java;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public Album(){}

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

    public void setImages(List<ImageV2> images) {
        this.images = images;
    }

}

