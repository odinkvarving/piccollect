package sample.JavaFX.SearchImageScene;

import com.drew.metadata.MetadataException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Java.ImageV2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class SearchImageSceneController implements Initializable {

    @FXML private TableView<ImageV2> table;
    @FXML private TableColumn<ImageV2, ImageView> imageColumn;
    @FXML private TableColumn<ImageV2, String> nameColumn;
    @FXML private TableColumn<ImageV2, String> locationColumn;
    @FXML private TableColumn<ImageV2, Date> dateColumn;
    @FXML private TableColumn<ImageV2, ArrayList<String>> tagsColumn;

    @FXML
    private Button backButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setColumnValues();
        } catch (MetadataException e) {
            e.printStackTrace();
        }

    }

    private void setColumnValues() throws MetadataException {
        imageColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, ImageView>("image"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, String>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, String>("location"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, Date>("date"));
        tagsColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, ArrayList<String>>("tags"));

        table.setItems(null);
    }

    /**
     * Dummy data just for testing for now
     * @return an observable list to populate the tableview
     */
    /**
    private ObservableList<Image> getImages() throws MetadataException {
        ObservableList<Image> images = FXCollections.observableArrayList();

        //Test image object
        ArrayList<String> testTags = new ArrayList<>();
        testTags.add("Gaming");
        testTags.add("Nature");

        Image testImage = new Image(testTags, "C:\\Users\\Player One\\Desktop\\piccollect\\src\\sample\\testBildeGPS.jpg");

        images.add(new Image(testImage.getTags(), testImage.getFile().getPath()));
        images.add(new Image(testImage.getTags(), testImage.getFile().getPath()));
        images.add(new Image(testImage.getTags(), testImage.getFile().getPath()));
        images.add(new Image(testImage.getTags(), testImage.getFile().getPath()));


        images.forEach(image -> {
            image.getImage().setFitWidth(100);
            image.getImage().setFitHeight(100);
        });
        return images;
    }
    **/

    /**
     * A method for handling the backbutton.
     * Sends you back to mainscene.
     */
    public void handleBackButtonClicked(){
        FXMLLoader mainSceneLoader = new FXMLLoader(getClass().getResource("../MainMenuScene/MainMenu.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene;
        try {
            scene = new Scene(mainSceneLoader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
