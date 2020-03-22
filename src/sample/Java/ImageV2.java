package sample.Java;


import com.drew.metadata.MetadataException;
import com.sun.xml.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;

import javax.persistence.*;
import java.io.File;
import java.util.Date;

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

    public ImageV2(){}

    public ImageV2(String imageName, String tags, String filePath) throws MetadataException {
        ImageMetaData imageMetaData = new ImageMetaData(new File(filePath));
        this.imageName = imageName;
        this.tags = tags;
        this.filePath = filePath;
        this.width = imageMetaData.getWidthFromMetadata();
        this.height = imageMetaData.getHeightFromMetadata();
        this.location = GeoConverter.reverseGeocoder(imageMetaData.getGeoDataFromMetadata().getLatitude(), imageMetaData.getGeoDataFromMetadata().getLongitude());
        this.date = imageMetaData.getDateFromMetaData();
    }

    private void getMetaData() throws MetadataException {
        ImageMetaData imageMetaData = new ImageMetaData(new File(filePath));

        this.width = imageMetaData.getWidthFromMetadata();
        this.height = imageMetaData.getHeightFromMetadata();
        this.location = GeoConverter.reverseGeocoder(imageMetaData.getGeoDataFromMetadata().getLatitude(), imageMetaData.getGeoDataFromMetadata().getLongitude());
        this.date = imageMetaData.getDateFromMetaData();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
