package sample.Java;

import com.drew.imaging.FileTypeDetector;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.file.FileTypeDirectory;
import com.drew.metadata.gif.GifImageDirectory;
import com.drew.metadata.jpeg.JpegDescriptor;
import com.drew.metadata.jpeg.JpegDirectory;
import com.drew.metadata.png.PngDirectory;

import java.io.*;
import java.util.Date;

//https://github.com/drewnoakes/metadata-extractor/wiki/Getting-Started-(Java) Follow this to understand more

public class ImageMetaData {

    File file;
    Metadata metaData;

    public ImageMetaData(File imageFile) {
        this.file = imageFile;
        readMetadataFromImage();
    }

    private void readMetadataFromImage(){
        try{
            metaData = ImageMetadataReader.readMetadata(file);
        }catch (ImageProcessingException | IOException e){
            print(e);
        }
    }

    /**
     * Method for fetching width from image
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
     * Method for fetching height from image
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
    private int getWidthFromMetadataJPEG() throws MetadataException {
        int width;
        JpegDirectory jpegDirectory = metaData.getFirstDirectoryOfType(JpegDirectory.class);
        width = jpegDirectory.getInt(JpegDirectory.TAG_IMAGE_WIDTH);
        return width;
    }

    private int getWidthFromMetadataPNG() throws MetadataException {
        int width;
        PngDirectory pngDirectory = metaData.getFirstDirectoryOfType(PngDirectory.class);
        width = pngDirectory.getInt(PngDirectory.TAG_IMAGE_WIDTH);
        return width;
    }

    private int getWidthFromMetadataGIF() throws MetadataException{
        int width;
        GifImageDirectory gifImageDirectory = metaData.getFirstDirectoryOfType(GifImageDirectory.class);
        width = gifImageDirectory.getInt(GifImageDirectory.TAG_WIDTH);
        return width;
    }

    /**
     * Methods for fetching height from the image. Need more than
     * one method for the same reason stated above.
     * @return the width of the image as an int
     * @throws MetadataException
     */
    private int getHeightFromMetadataJPEG() throws MetadataException {
        int height;
        JpegDirectory jpegDirectory = metaData.getFirstDirectoryOfType(JpegDirectory.class);
        height = jpegDirectory.getInt(JpegDirectory.TAG_IMAGE_HEIGHT);
        return height;
    }
    private int getHeightFromMetadataPNG() throws MetadataException {
        int height;
        PngDirectory pngDirectory = metaData.getFirstDirectoryOfType(PngDirectory.class);
        height = pngDirectory.getInt(PngDirectory.TAG_IMAGE_HEIGHT);
        return height;
    }

    private int getHeightFromMetadataGIF() throws MetadataException{
        int height;
        GifImageDirectory gifImageDirectory = metaData.getFirstDirectoryOfType(GifImageDirectory.class);
        height = gifImageDirectory.getInt(GifImageDirectory.TAG_HEIGHT);
        return height;
    }

    /**
     * A method for retrieving the date the image was created/captured
     * Not all images gives the information we need to read the date, may have to
     * find a fix for that.
     * @return the date.
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
     * Method for finding out what the filetype is.
     * @return filetype as a String
     */
    public String checkFileType(){
        String fileType;
        FileTypeDirectory fileTypeDirectory = metaData.getFirstDirectoryOfType(FileTypeDirectory.class);
        fileType = fileTypeDirectory.getString(FileTypeDirectory.TAG_DETECTED_FILE_TYPE_NAME);
        return fileType;
    }


    private static void print(Exception exception)
    {
        System.err.println("EXCEPTION: " + exception);
    }
}

