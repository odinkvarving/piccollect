package sample;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Java.GeoConverter;
import sample.Java.ImageV2;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    /**
    int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
     **/


    @Override
    public void start(Stage primaryStage) throws Exception{
        Font.loadFont(getClass().getResourceAsStream("JavaFX/resources/Montserrat-Regular.ttf"), 18);
        Parent root = FXMLLoader.load(getClass().getResource("JavaFX/MainMenuScene/MainMenu.fxml"));


        // Responsive Design
        int sceneWidth = 1066;
        int sceneHeight = 600;
        /**
        if (screenWidth <= 1280 && screenHeight <= 768) {
            sceneWidth = 1066;
            sceneHeight = 600;

        }
         **/
        /**else if (screenWidth <= 1920 && screenHeight <= 1080) {
            sceneWidth = 1422;
            sceneHeight = 800;
        }**/

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.show();

        GeoConverter.reverseGeocoder(1, 1);


        File testFile = new File("C:\\Users\\Player One\\Desktop\\Piccollect-team9\\piccollect\\src\\sample\\testBildeGPS.jpg");
        Metadata testMetadata = ImageMetadataReader.readMetadata(testFile);

        ImageV2 test = new ImageV2("mAGNUS", "BEACH", "C:\\Users\\Player One\\Desktop\\Piccollect-team9\\piccollect\\src\\sample\\testBildeGPS.jpg");
        System.out.println(test.getLocation());




}


    public static void main(String[] args) {
        launch(args);
    }
}
