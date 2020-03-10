package sample.Java;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.jpeg.JpegDescriptor;
import com.drew.metadata.jpeg.JpegDirectory;

import java.io.*;

//https://github.com/drewnoakes/metadata-extractor/wiki/Getting-Started-(Java) Follow this to understand more

public class ImageMetaData {

    File file;
    Metadata metaData;

    public ImageMetaData(File imageFile){
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

    public int getWidthFromMetadata(){
        int width = 0;
        JpegDirectory jpegDirectory = metaData.getFirstDirectoryOfType(JpegDirectory.class);
        JpegDescriptor jpegDescriptor = new JpegDescriptor(jpegDirectory);
        width = Integer.parseInt(jpegDescriptor.getImageWidthDescription());
        return width;
    }

    public int getHeightFromMetadata(){
        int height = 0;
        JpegDirectory jpegDirectory = metaData.getFirstDirectoryOfType(JpegDirectory.class);
        JpegDescriptor jpegDescriptor = new JpegDescriptor(jpegDirectory);
        height = Integer.parseInt(jpegDescriptor.getImageHeightDescription());
        return height;
    }
    private static void print(Exception exception)
    {
        System.err.println("EXCEPTION: " + exception);
    }
}

