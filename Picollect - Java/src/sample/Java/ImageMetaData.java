package sample.Java;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;

import java.io.*;

//https://github.com/drewnoakes/metadata-extractor/wiki/Getting-Started-(Java) Follow this to understand more

public class ImageMetaData {

    File file;
    Metadata metaData;

    public ImageMetaData(File imageFile){
        file = imageFile;
        readMetadataFromImage();
    }

    private void readMetadataFromImage(){
        try{
            metaData = ImageMetadataReader.readMetadata(file);
        }catch (ImageProcessingException | IOException e){
            print(e);
        }
    }
    private static void print(Exception exception)
    {
        System.err.println("EXCEPTION: " + exception);
    }
}

