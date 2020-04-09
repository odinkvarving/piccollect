package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Java.DatabaseConnection;
import sample.Java.GeoConverter;

import java.util.ArrayList;

/**
 * The Main class has the task of launching the whole application.
 */
public class Main extends Application {


    /*
    int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
     */

    private double xOffset = 0;
    private double yOffset = 0;
    /**
     * start method is a method Main can run when extending Application. start method initializes the Application.
     * @param primaryStage: The primary stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Font.loadFont(getClass().getResourceAsStream("JavaFX/resources/Montserrat-Medium.ttf"), 18);
        Parent root = FXMLLoader.load(getClass().getResource("JavaFX/MapScene/MapScene.fxml"));
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        // Responsive Design
        int sceneWidth = 1066;
        int sceneHeight = 600;

        /*
        if (screenWidth <= 1280 && screenHeight <= 768) {
            sceneWidth = 1066;
            sceneHeight = 600;

        }
         */
        /*else if (screenWidth <= 1920 && screenHeight <= 1080) {
            sceneWidth = 1422;
            sceneHeight = 800;
        }*/

        primaryStage.setTitle("Hello World");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.show();

        GeoConverter.reverseGeocoder(1, 1);


        //File testFile = new File("C:\\Users\\odink\\OneDrive – NTNU\\Programmering2\\Piccollect\\piccollect\\src\\sample\\testBildeGPS.jpg");
        //Metadata testMetadata = ImageMetadataReader.readMetadata(testFile);

        ArrayList<String> testing = new ArrayList<>();

        //Image test = new Image(testing, "C:\\Users\\odink\\OneDrive – NTNU\\Programmering2\\Piccollect\\piccollect\\src\\sample\\testBildeGPS.jpg");

        //GeoConverter.reverseGeocoder(test.getLocation().getLatitude(), test.getLocation().getLongitude());

}

    /**
     * main method launches the Application, with help from start method.
     * @param args
     */
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        launch(args);
    }
}
