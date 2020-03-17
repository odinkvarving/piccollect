package sample.JavaFX.SearchImageScene;

import com.drew.metadata.MetadataException;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import sample.Java.Image;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class SearchImageSceneController implements Initializable {

    @FXML private TableView<Image> table;
    @FXML private TableColumn<Image, ImageView> imageColumn;
    @FXML private TableColumn<Image, String> nameColumn;
    @FXML private TableColumn<Image, String> locationColumn;
    @FXML private TableColumn<Image, Date> dateColumn;
    @FXML private TableColumn<Image, ArrayList<String>> tagsColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setColumnValues();
        } catch (MetadataException e) {
            e.printStackTrace();
        }

    }

    private void setColumnValues() throws MetadataException {
        imageColumn.setCellValueFactory(new PropertyValueFactory<Image, ImageView>("image"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Image, String>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Image, String>("location"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Image, Date>("date"));
        tagsColumn.setCellValueFactory(new PropertyValueFactory<Image, ArrayList<String>>("tags"));

        table.setItems(getImages());
    }

    /**
     * Dummy data just for testing for now
     * @return an observable list to populate the tableview
     */
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
}
