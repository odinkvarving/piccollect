package sample;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.png.PngDirectory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Java.ImageMetaData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        File testFile = new File("C:\\Users\\magnu\\OneDrive\\Skrivebord\\PiccollectApplication\\src\\sample\\testBildeGPS.jpg");
        Metadata testMetadata = ImageMetadataReader.readMetadata(testFile);

        for(Directory directory : testMetadata.getDirectories()){
            System.out.println(directory);
        }

        ImageMetaData test = new ImageMetaData(testFile);
        System.out.println(test.getGeoDataFromMetadata());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
