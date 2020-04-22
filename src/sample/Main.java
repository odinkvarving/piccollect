package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    /**
     * xOffset is a double for the x-offset
     * yOffset is a double for the y-offset
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
        Parent root = FXMLLoader.load(getClass().getResource("JavaFX/MainMenuScene/MainMenu.fxml"));

        // Responsive Design
        int sceneWidth = 1066;
        int sceneHeight = 600;

        primaryStage.setTitle("Hello World");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.show();

        GeoConverter.reverseGeocoder(1, 1);

        ArrayList<String> testing = new ArrayList<>();

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
