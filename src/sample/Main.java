package sample;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        File testFile = new File("C:\\Users\\magnu\\OneDrive\\Skrivebord\\Picollect - Project Team 9\\Picollect - Java\\src\\sample\\picklerick.jpg");
        Metadata testMetadata = ImageMetadataReader.readMetadata(testFile);

        for(Directory directory : testMetadata.getDirectories()){
            for(Tag tag : directory.getTags()){
                System.out.println(tag);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
