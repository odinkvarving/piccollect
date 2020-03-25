package sample.JavaFX.SearchImageScene;

import com.drew.metadata.MetadataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.Java.ImageV2;
import sample.Java.ImageV2DAO;
import sample.Main;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SearchImageSceneController implements Initializable {

    @FXML private TableView<ImageV2> table;
    @FXML private TableColumn<ImageV2, ImageIcon> imageColumn;
    @FXML private TableColumn<ImageV2, String> nameColumn;
    @FXML private TableColumn<ImageV2, String> locationColumn;
    @FXML private TableColumn<ImageV2, Date> dateColumn;
    @FXML private TableColumn<ImageV2, ArrayList<String>> tagsColumn;

    @FXML
    private TextField nameSearchField;

    @FXML
    private TextField locationSearchField;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private Button addTagButton;

    @FXML
    private HBox tagHBox;

    @FXML
    private TextField tagSearchField;

    @FXML
    private Button backButton;

    ArrayList<ImageV2> allImages;
    ArrayList<Label> tagLabels = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageV2DAO imageV2DAO = new ImageV2DAO(Main.entityManagerFactory);
        allImages = (ArrayList<ImageV2>) imageV2DAO.getImages();
        try {
            setColumnValues();
        } catch (MetadataException e) {
            e.printStackTrace();
        }
        setTableItems();

    }

    /**
     * Sets up the columns that is used in the tableview
     * @throws MetadataException
     */

    private void setColumnValues() throws MetadataException {
        imageColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, ImageIcon>("image"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, String>("imageName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, String>("location"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, Date>("date"));
        tagsColumn.setCellValueFactory(new PropertyValueFactory<ImageV2, ArrayList<String>>("tags"));
    }

    /**
     * Method for creating observable list and setting it as table items
     */
    private void setTableItems(){
        ObservableList<ImageV2> images = FXCollections.observableArrayList();
        for(ImageV2 image : allImages){
            images.add(image);
        }
        table.setItems(images);
    }

    /**
     * Handles the search button clicked
     */
    public void handleSearchButtonClicked(){
        ArrayList<ImageV2> filteredImages = (ArrayList<ImageV2>) allImages.clone();
        if(!(nameSearchField.getText().equals("") )){
            filteredImages = (ArrayList<ImageV2>) filteredImages.stream().filter(image -> image.getImageName().contains(nameSearchField.getText())).collect(Collectors.toList());
        }
        if(!(locationSearchField.getText().equals(""))){
            filteredImages = (ArrayList<ImageV2>) filteredImages.stream().filter(image -> image.getLocation().equals(locationSearchField.getText())).collect(Collectors.toList());
        }
        if(!(fromDatePicker.getValue() == null || toDatePicker.getValue() == null)){
            Date fromDate = convertDateFormat(fromDatePicker.getValue());
            Date toDate = convertDateFormat(toDatePicker.getValue());
            filteredImages = (ArrayList<ImageV2>) filteredImages.stream().filter(image -> image.getDate() != null).filter(image -> (checkIfDateIsInBetween(fromDate, toDate, image.getDate()))).collect(Collectors.toList());
        }
        if(!(tagHBox.getChildren().size() == 1)){
            filteredImages = (ArrayList<ImageV2>) filteredImages.stream().filter(this::checkIfImageContainsSameTags).collect(Collectors.toList());
        }
        refreshTable(filteredImages);
        clearAllSearchInputs();
    }

    /**
     * A for loop that checks if the image contains any tags from the search field
     * @param imageV2
     * @return true or false
     */
    private boolean checkIfImageContainsSameTags(ImageV2 imageV2){
        for(int j = 0; j < tagLabels.size(); j ++){
            if(imageV2.getTags().contains(tagLabels.get(j).getText())){
                return true;
            }
        }
        return false;
    }

    private Date convertDateFormat(LocalDate date){
        return java.sql.Date.valueOf(date);
    }

    /**
     * Method for checking if a date is between two dates.
     * @param fromDate
     * @param toDate
     * @param betweenDate
     * @return true or false
     */
    private boolean checkIfDateIsInBetween(Date fromDate, Date toDate, Date betweenDate){
        return fromDate.compareTo(betweenDate) * betweenDate.compareTo(toDate) >= 0;
    }

    /**
     * Method for refreshing the tableview when the search has been done
     * @param filteredImages
     */
    private void refreshTable(ArrayList<ImageV2> filteredImages){
        ObservableList<ImageV2> images = FXCollections.observableArrayList();
        for(ImageV2 imageV2 : filteredImages){
            images.add(imageV2);
        }
        table.getItems().clear();
        table.setItems(images);
    }


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

    /**
     * Button to handle when the add tag button is clicked
     */
    public void handleAddTagButtonClicked(){
        if(!(tagSearchField.equals(""))) {
            Label tag = new Label();
            tag.setText(tagSearchField.getText());
            tagHBox.getChildren().add(tag);
            tagLabels.add(tag);
            tagSearchField.clear();
        }
    }

    /**
     * Method for clearing all the tags that is added
     */
    private void clearAllTags(){
        int nrTags = tagLabels.size();
        for(int i = nrTags; i > 0; i --){
            tagHBox.getChildren().remove(i);
        }
        tagLabels.clear();
    }

    /**
     * Method for clearing all search inputs
     */
    private void clearAllSearchInputs(){
        nameSearchField.clear();
        locationSearchField.clear();
        fromDatePicker.setValue(null);
        toDatePicker.setValue(null);
        clearAllTags();
    }
}
