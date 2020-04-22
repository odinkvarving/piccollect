package sample.Java;


import com.drew.imaging.FileTypeDetector;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.file.FileTypeDirectory;
import com.drew.metadata.gif.GifImageDirectory;
import com.drew.metadata.jpeg.JpegDescriptor;
import com.drew.metadata.jpeg.JpegDirectory;
import com.drew.metadata.png.PngDirectory;

import java.io.*;
import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * ImageMetaData is a class which is used to collect all metadata from a image
 */
public class ImageMetaData {

    /**
     * file is an image file
     * metadata is all metadata from the image in the same Metadata object
     */
    File file;
    Metadata metaData;

    /**
     * This constructor takes the image file as a parameter
     * Uses a method from the metadata-extractor API to read metadata from the given image-file
     * @param imageFile: image file
     */
    public ImageMetaData(File imageFile) {
        this.file = imageFile;
        readMetadataFromImage();
    }

    /**
     * This is the method that reads metadata from an image
     * @throws MetadataException
     */
    private void readMetadataFromImage(){
        try{
            metaData = ImageMetadataReader.readMetadata(file);
        }catch (ImageProcessingException | IOException e){
            print(e);
        }
    }

    /**
     * This method prints out metadata from an image and displays it in a defined order
     * @throws MetadataException
     */
    public void printMetaDataFromImage() {
        try {
            metaData = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metaData.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.format("[%s] - %s = %s",
                            directory.getName(), tag.getTagName(), tag.getDescription());
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                    }
                }
            }
        } catch (ImageProcessingException | IOException e){
            print(e);
        }
    }

    /**
     * Method for fetching width from image based on what datatype the image is (jpeg, png or gif)
     * @return width as an Integer
     * @throws MetadataException
     */
    public int getWidthFromMetadata() throws MetadataException {
        String dataType = checkFileType();
        if(dataType.equalsIgnoreCase("jpeg")){
            return getWidthFromMetadataJPEG();
        }
        else if(dataType.equalsIgnoreCase("png")){
            return getWidthFromMetadataPNG();
        }
        else if(dataType.equalsIgnoreCase("gif")){
            return getWidthFromMetadataGIF();
        }
        return -1;
    }

    /**
     * Method for fetching height from image based in what datatype the image is (jpeg, png or gif)
     * @return image height as an Integer
     * @throws MetadataException
     */
    public int getHeightFromMetadata() throws MetadataException {
        String dataType = checkFileType();
        if(dataType.equalsIgnoreCase("jpeg")){
            return getHeightFromMetadataJPEG();
        }
        else if(dataType.equalsIgnoreCase("png")){
            return getHeightFromMetadataPNG();
        }
        else if(dataType.equalsIgnoreCase("gif")){
            return getHeightFromMetadataGIF();
        }
        return -1;
    }

    /**
     * The methods below is fetching the width from the image
     * we need different methods for different files because not every file
     * has the same directories.
     * @return the width of the image as an int
     * @throws MetadataException
     */
    private int getWidthFromMetadataJPEG() {
        int width;
        JpegDirectory jpegDirectory = metaData.getFirstDirectoryOfType(JpegDirectory.class);
        try {
            width = jpegDirectory.getInt(JpegDirectory.TAG_IMAGE_WIDTH);
        } catch (MetadataException e) {
            e.getStackTrace();
            return -1;
        }
        return width;
    }
    private int getWidthFromMetadataPNG() {
        int width;
        PngDirectory pngDirectory = metaData.getFirstDirectoryOfType(PngDirectory.class);
        try {
            width = pngDirectory.getInt(PngDirectory.TAG_IMAGE_WIDTH);
        } catch (MetadataException e) {
            e.getStackTrace();
            return -1;
        }
        return width;
    }
    private int getWidthFromMetadataGIF() {
        int width;
        GifImageDirectory gifImageDirectory = metaData.getFirstDirectoryOfType(GifImageDirectory.class);
        try {
            width = gifImageDirectory.getInt(GifImageDirectory.TAG_WIDTH);
        } catch (MetadataException e) {
            e.getStackTrace();
            return -1;
        }
        return width;
    }

    /**
     * Methods for fetching height from the image. Need more than
     * one method for the same reason stated above.
     * @return the width of the image as an int
     * @throws MetadataException
     */
    private int getHeightFromMetadataJPEG() {
        int height;
        JpegDirectory jpegDirectory = metaData.getFirstDirectoryOfType(JpegDirectory.class);
        try {
            height = jpegDirectory.getInt(JpegDirectory.TAG_IMAGE_HEIGHT);
        } catch (MetadataException e) {
            e.getStackTrace();
            return -1;
        }
        return height;
    }
    private int getHeightFromMetadataPNG() {
        int height;
        PngDirectory pngDirectory = metaData.getFirstDirectoryOfType(PngDirectory.class);
        try {
            height = pngDirectory.getInt(PngDirectory.TAG_IMAGE_HEIGHT);
        } catch (MetadataException e) {
            e.getStackTrace();
            return -1;
        }
        return height;
    }
    private int getHeightFromMetadataGIF() {
        int height;
        GifImageDirectory gifImageDirectory = metaData.getFirstDirectoryOfType(GifImageDirectory.class);
        try {
            height = gifImageDirectory.getInt(GifImageDirectory.TAG_HEIGHT);
        } catch (MetadataException e) {
            e.getStackTrace();
            return -1;
        }
        return height;
    }

    /**
     * A method for retrieving the date the image was created/captured by looking for a specific Date-tag in the ExifIFD0Directory
     * Not all images gives the information we need to read the date
     * @return the date.
     * @throws NullPointerException
     */
    public Date getDateFromMetaData(){
        Date date;
        ExifIFD0Directory exifIFD0Directory = metaData.getFirstDirectoryOfType(ExifIFD0Directory.class);
        try{
            date = exifIFD0Directory.getDate(ExifIFD0Directory.TAG_DATETIME);
        }catch (NullPointerException e){
            return null;
        }
        return date;
    }

    /**
     * This method returns timestamp from when the image was taken by looking for a specific Datetime-tag in the ExifIFD0Directory
     * @return timestamp from image
     * @throws NullPointerException
     */
    public Timestamp getTimeFromMetaData() {
        Timestamp timestamp;
        ExifIFD0Directory exifIFD0Directory = metaData.getFirstDirectoryOfType(ExifIFD0Directory.class);
        try {
            timestamp = (Timestamp) exifIFD0Directory.getDate(ExifIFD0Directory.TAG_DATETIME_ORIGINAL);
        } catch (NullPointerException e) {
            return null;
        }
        return timestamp;
    }

    /**
     * This method returns the location in the form of a Geolocation object. This method can later be used for extracting the longitude and latitude
     * @return geoLocation from image
     * @throws NullPointerException
     */
    public GeoLocation getGeoDataFromMetadata() {
        GeoLocation geoLocation;
        GpsDirectory gpsDirectory = metaData.getFirstDirectoryOfType(GpsDirectory.class);
        try {
            geoLocation = gpsDirectory.getGeoLocation();
        }catch (NullPointerException e){
            return null;
        }
        return geoLocation;
    }

    /**
     * Method for finding out what the type of file the image is
     * @return Type of file as a String
     */
    public String checkFileType(){
        String fileType;
        FileTypeDirectory fileTypeDirectory = metaData.getFirstDirectoryOfType(FileTypeDirectory.class);
        fileType = fileTypeDirectory.getString(FileTypeDirectory.TAG_DETECTED_FILE_TYPE_NAME);
        return fileType;
    }

    /**
     * This method prints out any kind of exception as an error in the terminal window
     * @param exception: any kind of exception as it takes in an exception object
     */
    private static void print(Exception exception)
    {
        System.err.println("EXCEPTION: " + exception);
    }

}

