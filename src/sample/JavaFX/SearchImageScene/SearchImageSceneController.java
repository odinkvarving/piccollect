package sample.JavaFX.SearchImageScene;

import com.drew.metadata.MetadataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import sample.Java.EMF;
import sample.Java.ImageV2;
import sample.Java.ImageV2DAO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class SearchImageSceneController implements Initializable {

    @FXML private TableView<ImageV2> table;
    @FXML private TableColumn imageColumn;
    @FXML private TableColumn<ImageV2, String> nameColumn;
    @FXML private TableColumn<ImageV2, String> locationColumn;
    @FXML private TableColumn<ImageV2, Date> dateColumn;
    @FXML private TableColumn<ImageV2, ArrayList<String>> tagsColumn;

    @FXML
    private Button backButton;

    ArrayList<ImageV2> allImages;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageV2DAO imageV2DAO = new ImageV2DAO(EMF.entityManagerFactory);
        allImages = (ArrayList<ImageV2>) imageV2DAO.getImages();
        try {
            setColumnValues();
        } catch (MetadataException e) {
            e.printStackTrace();
        }

    }

    private void setColumnValues() throws MetadataException {
        nameColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, String>("imageName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, String>("location"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, Date>("date"));
        tagsColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, ArrayList<String>>("tags"));

        ObservableList<ImageV2> images = FXCollections.observableArrayList();
        for(ImageV2 image : allImages){
            images.add(image);
        }

        table.setItems(images);
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
