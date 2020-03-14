package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("JavaFX/MainMenuScene/MainMenu.fxml"));

        // Responsive Design
        int sceneWidth = 0;
        int sceneHeight = 0;
        if (screenWidth <= 800 && screenHeight <= 600) {
            sceneWidth = 711;
            sceneHeight = 400;
        } else if (screenWidth <= 1280 && screenHeight <= 768) {
            sceneWidth = 1066;
            sceneHeight = 600;
        } else if (screenWidth <= 1920 && screenHeight <= 1080) {
            sceneWidth = 1422;
            sceneHeight = 800;
        }

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.show();
/**
        File testFile = new File("C:\\Users\\magnu\\OneDrive\\Skrivebord\\PiccollectApplication\\src\\sample\\testBildeGPS.jpg");
        Metadata testMetadata = ImageMetadataReader.readMetadata(testFile);

        for(Directory directory : testMetadata.getDirectories()){
            System.out.println(directory);
        }

        ImageMetaData test = new ImageMetaData(testFile);
        System.out.println(test.getGeoDataFromMetadata());
 **/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
