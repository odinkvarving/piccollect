package sample.Java;


import com.drew.metadata.MetadataException;
import com.sun.xml.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Album> albums = new ArrayList<>();

    public ImageV2(){}

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
