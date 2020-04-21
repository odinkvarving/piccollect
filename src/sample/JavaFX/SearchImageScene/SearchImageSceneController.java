package sample.JavaFX.SearchImageScene;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sample.Java.*;
import sample.JavaFX.ResponseDialogs.InformationDialog;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * SearchImageSceneController controls the SearchImageScene.
 * Every method in this class is used to handle the different functionalities on the SearchImageScene.
 * The class implements Initializable
 */

public class SearchImageSceneController implements Initializable {

    /**
     * windowMenuButtonsBox is is a pane which contains exit button etc.
     * nameSearchField is a text field where the user types in the image name
     * locationSearchField is a text field where the user types in the image location
     * fromDatePicker is a DatePicker where the user chooses a from-date
     * toDatePicker is a DatePicker where the user chooses a to-date. These two DatePickers are used to find a/multiple image(s) by their date
     * addTagButton is a button used to add tags for search
     * tagHBox is a HBox which shows every tag in the search
     * tagSearchField is a text field where the user can write a tag to add to the search
     * resetSearchButton is a button which resets the search
     * backButton is a button the user can press to go back to the main menu
     * imageList is a VBox of all images
     * allImages is an ArrayList with all the images from the database
     * tagLabels is an ArrayList with all the tags from the images
     * searchListItems is an ArrayList with the searchListItems that is an HBox that contains the image information
     */
    @FXML
    private Pane windowMenuButtonsBox;
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
    private Button resetSearchButton;
    @FXML
    private Button backButton;
    @FXML
    private VBox imageList;
    ArrayList<ImageV2> allImages;
    ArrayList<Label> tagLabels = new ArrayList<>();
    ArrayList<SearchListItem> searchListItems = new ArrayList<>();

    /**
     * Initialize-method where we fill inn the images-arraylist with images from the database, and fill the scrollpane with
     * all the SearchListItems.
     * @param url: URL
     * @param resourceBundle: ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTagTextFieldListener();
        ImageV2DAO imageV2DAO = new ImageV2DAO(DatabaseConnection.getInstance().getEntityManagerFactory());
        allImages = (ArrayList<ImageV2>) imageV2DAO.getImages();
        setListItems();
        try {
            Node windowMenuButtonsNode = FXMLLoader.load(getClass().getResource("../WindowMenuButtons/WindowMenuButtons.fxml"));
            windowMenuButtonsBox.getChildren().add(windowMenuButtonsNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method where we go through all the images and creates a SearchListItem object with them and adds them to the corresponding lists.
     */
    private void setListItems(){
        for(ImageV2 imageV2 : allImages){
            SearchListItem item = new SearchListItem(imageV2);
            searchListItems.add(item);
            imageList.getChildren().add(item);
        }
    }

    /**
     * Handles the search button clicked. Goes through all input-fields and checks whether they are empty or not. Usese streams to
     * filter through each criteria. At the end we reload the scrollpane with all the search-results and clear all input-fields.
     */
    public void handleSearchButtonClicked(){
        ArrayList<ImageV2> filteredImages = (ArrayList<ImageV2>) allImages.clone();
        if(nameSearchField.getText().equals("") && locationSearchField.getText().equals("") && (fromDatePicker.getValue() == null || toDatePicker.getValue() == null) && tagHBox.getChildren().size() == 1 ) {
            InformationDialog.showInformationDialog("Error", "All boxes are empty");
        } else {
            if (!(nameSearchField.getText().equals(""))) {
                filteredImages = (ArrayList<ImageV2>) filteredImages.stream().filter(image -> image.getImageName().toLowerCase().contains(nameSearchField.getText().toLowerCase())).collect(Collectors.toList());
            }
            if (!(locationSearchField.getText().equals(""))) {
                filteredImages = (ArrayList<ImageV2>) filteredImages.stream().filter(image -> image.getLocation().toLowerCase().contains(locationSearchField.getText().toLowerCase())).collect(Collectors.toList());
            }
            if (!(fromDatePicker.getValue() == null || toDatePicker.getValue() == null)) {
                Date fromDate = convertDateFormat(fromDatePicker.getValue());
                Date toDate = convertDateFormat(toDatePicker.getValue());
                filteredImages = (ArrayList<ImageV2>) filteredImages.stream().filter(image -> image.getDate() != null).filter(image -> (checkIfDateIsInBetween(fromDate, toDate, image.getDate()))).collect(Collectors.toList());
            }
            if (!(tagHBox.getChildren().size() == 1)) {
                filteredImages = (ArrayList<ImageV2>) filteredImages.stream().filter(this::checkIfImageContainsSameTags).collect(Collectors.toList());
            }
            refreshList(filteredImages);
            clearAllSearchInputs();
        }
    }

    /**
     * A for loop that checks if the image contains any tags from the search field
     * @param imageV2
     * @return true or false
     */
    private boolean checkIfImageContainsSameTags(ImageV2 imageV2){
        for(int j = 0; j < tagLabels.size(); j ++){
            if(imageV2.getTags().toLowerCase().contains(tagLabels.get(j).getText().toLowerCase())){
                return true;
            }
        }
        return false;
    }

    /**
     * Method for converting localdate to date
     * @param date
     * @return Date
     */
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
     * Method for refreshing the list when the search has been done
     * @param filteredImages
     */
    private void refreshList(ArrayList<ImageV2> filteredImages){
        searchListItems.clear();
        filteredImages.stream().forEach(imageV2 -> searchListItems.add(new SearchListItem(imageV2)));
        imageList.getChildren().clear();
        searchListItems.stream().forEach(searchListItem -> imageList.getChildren().add(searchListItem));
    }


    /**
     * A method for handling the backbutton.
     * Sends you back to mainscene.
     */
    public void handleBackButtonClicked(){
        FXMLLoader mainSceneLoader = new FXMLLoader(getClass().getResource("../MainMenuScene/MainMenu.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        try {
            stage.getScene().setRoot(mainSceneLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleResetSearchButtonClicked(){
        clearAllSearchInputs();
        refreshList((ArrayList<ImageV2>) allImages.clone());
    }

    /**
     * Button to handle when the add tag button is clicked
     */
    public void handleAddTagButtonClicked(){
        addTag();
    }

    private void addTag(){
        if(!(tagSearchField.equals(""))) {
            Label tag = new Label();
            tag.setText(tagSearchField.getText());
            tagHBox.getChildren().add(tag);
            tagLabels.add(tag);
            tagSearchField.clear();
        }
    }
    private void initializeTagTextFieldListener(){
        tagSearchField.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent keyEvent){
                if(keyEvent.getCode() == KeyCode.ENTER){
                    addTag();
                }
            }
        });
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

    /**
     * Goes through all the SearchListItems-objects and checks if the checkbox is checkd, and adds them to the list of selected images if so.
     * @return
     */
    private ArrayList<ImageV2> collectAllSelectedImages(){
        ArrayList<ImageV2> selectedImages = new ArrayList<>();

        searchListItems.stream().filter(searchListItem -> searchListItem.getCheckBox().isSelected()).forEach(searchListItem -> selectedImages.add(searchListItem.getImageV2()));
        return selectedImages;
    }

    /**
     * Opens a dialog where the user enters the name of the new album. After that we go through all selected images
     * and adds them to the new album.
     */
    public void handleCreateButtonClicked(){
        if(collectAllSelectedImages().isEmpty()) {
            InformationDialog.showInformationDialog("Error", "You have not selected any photos");
        } else {
            String albumName;
            Album newAlbum;
            AlbumDAO albumDAO = new AlbumDAO(DatabaseConnection.getInstance().getEntityManagerFactory());

            TextInputDialog albumDialog = new TextInputDialog();
            albumDialog.setTitle("Create new album");
            albumDialog.setHeaderText("Create a new album");
            albumDialog.setContentText("Please enter album name: ");

            Optional<String> result = albumDialog.showAndWait();

            if (result.isPresent() && !result.get().equals("")) {
                albumName = result.get();
                newAlbum = new Album(albumName);
                albumDAO.storeNewAlbum(newAlbum);
                ArrayList<ImageV2> selectedImages = collectAllSelectedImages();
                selectedImages.forEach(imageV2 -> albumDAO.createNewAlbumWithImages(newAlbum, imageV2));
                InformationDialog.showInformationDialog("Create album", "Album created successfully!");
            }
        }
    }

    /**
     * Method for generating a pdf based on selected images
     * IMPORTANT: Access to directory needs to be public, if a FileNotFoundException will be thrown
     * @throws Exception
     */
    public void handleGeneratePDFButtonClicked() throws Exception {
        if(collectAllSelectedImages().isEmpty()) {
            InformationDialog.showInformationDialog("Error", "You have not selected any photos");
        } else {

            TextInputDialog albumDialog = new TextInputDialog();
            albumDialog.setTitle("Create new pdf");
            albumDialog.setHeaderText("Create a new pdf");
            albumDialog.setContentText("Please enter pdf name: ");

            Optional<String> result = albumDialog.showAndWait();
            if (result.isPresent() && !result.get().equals("")) {

                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Select directory");
                File defaultDirectory = new File("C:/");
                chooser.setInitialDirectory(defaultDirectory);
                File selectedDirectory = chooser.showDialog(null);


                // Creating a PdfWriter
                String dest = selectedDirectory.getAbsolutePath() + "\\" + result.get() + ".pdf";
                PdfWriter writer = new PdfWriter(dest);

                // Creating a PdfDocument
                PdfDocument pdf = new PdfDocument(writer);

                // Creating a Document
                Document document = new Document(pdf);

                ArrayList<ImageV2> selectedImages = collectAllSelectedImages();
                for (ImageV2 images : selectedImages) {
                    String imFile = images.getFilePath();

                    // Creating an ImageData object

                    ImageData data = ImageDataFactory.create(imFile);

                    // Creating an Image object
                    Image image = new Image(data);

                    // Adding image to the document
                    document.add(image);
                }


                // Closing the document
                document.close();
                InformationDialog.showInformationDialog("PDF-Document successfully created", "Your PDF with the images is now stored in the folder you chose to place it in.");
            } else {
                InformationDialog.showInformationDialog("Error", "The pdf file needs a name");
            }
        }
    }
}
