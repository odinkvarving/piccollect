package sample.JavaFX.MapScene;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import sample.Java.*;
import sample.JavaFX.ResponseDialogs.InformationDialog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MapSceneController implements Initializable, MapComponentInitializedListener {

    //The layout with the map
    private GoogleMapView mapView;
    //The map which is connected to the mapview. Controls the options of the mapView
    private GoogleMap map;
    //For locating areas in the world
    private GeocodingService geocodingService;

    //root pane
    @FXML
    private AnchorPane scenePane;
    //Anchorpane where the mapview and adresstextfield is in
    @FXML
    private AnchorPane mapAnchorPane;
    //Inputfield where user can search for a place
    @FXML
    private TextField addressTextField;
    //Homebutton to get back to mainscene
    @FXML
    private Button backButton;
    //A pane with custom window-menu buttons
    @FXML
    private Pane windowMenuButtonBox;
    //A stackpane where the preview image dialog pops up
    @FXML
    private StackPane imageDialogPane;

    //StringProperty that is bound to the adresstextfield
    private StringProperty address = new SimpleStringProperty();
    //ArrayList with all the images from the database
    private ArrayList<ImageV2> images;


    /**
     * In the initialize-method we first fetch the images from the database and fill the imagearraylist.
     * then we create the GoogleMapView object with our API-key and adds the mapinitializer.
     * then we add the GoogleMapView to the mapAnchorPane and attaches it to the sides so it is stretchable.
     * We then moves the search textfield to the front and binds it to the StringProperty-variable above.
     * At last we load in the custom window menu buttons.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchImagesFromDB();

        mapView = new GoogleMapView(Locale.getDefault().getLanguage(), "AIzaSyAaCPNXgEw5zlJTHLr0MYOmvxOcQ50vLdw");
        mapView.addMapInializedListener(this);

        mapAnchorPane.getChildren().add(mapView);
        mapAnchorPane.setLeftAnchor(mapView, 0.0);
        mapAnchorPane.setRightAnchor(mapView, 0.0);
        mapAnchorPane.setBottomAnchor(mapView, 0.0);
        mapAnchorPane.setTopAnchor(mapView, 0.0);

        addressTextField.toFront();
        address.bind(addressTextField.textProperty());

        try {
            Node windowMenuButtonsNode = FXMLLoader.load(getClass().getResource("../WindowMenuButtons/WindowMenuButtons.fxml"));
            windowMenuButtonBox.getChildren().add(windowMenuButtonsNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simple method for retrieving the images from the database and filling them into the ArrayList with images.
     */
    private void fetchImagesFromDB(){
        ImageV2DAO imageV2DAO = new ImageV2DAO(DatabaseConnection.getInstance().getEntityManagerFactory());
        images = (ArrayList<ImageV2>) imageV2DAO.getImages();
    }

    /**
     * Initializes the GeocodingService object and creates a MapOptions variable
     * that is edited after our needs and then used to create a map with the createMap(MapOptions mapOptions) method.
     * At last we use the method placeImagesOnMap().
     */
    @Override
    public void mapInitialized() {
        geocodingService = new GeocodingService();
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(62, 10))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(5).mapTypeControl(false);

        map = mapView.createMap(mapOptions);
        placeImagesOnMap();
    }

    /**
     * A method that goes through all images and checks if the geolocation is there or not, and if the geolocaiton
     * exists we create a marker with the geolocation from the image and put a eventhandler on them, and at last
     * we add them to the map with the addMarker(Marker marker) method.
     */
    private void placeImagesOnMap(){
        images.stream().forEach(imageV2 -> {
            if(imageV2.getGeoLocation() != null) {
                Marker marker = createMarker(imageV2.getGeoLocation().getLatitude(), imageV2.getGeoLocation().getLongitude());
                addMarkerClickHandler(marker, imageV2);
                map.addMarker(marker);
            }
        });
    }

    /**
     * This method creates a listener to the marker where it first checks if
     *
     * any other images is being previewed, if so, we clear all children to the stackpane before we add the
     * new imagepreviewdialog. After that we create an image preview dialog with the marker/image.
     * @param marker
     * @param imageV2
     */
    private void addMarkerClickHandler(Marker marker, ImageV2 imageV2){
        map.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {

            if(imageDialogPane.getChildren().size() > 0){
                imageDialogPane.getChildren().clear();
            }

            createImagePreviewDialog(imageV2);
        });
    }

    /**
     * Uses the JFoenix library that has custom dialogs and more.
     * First create a layout and some JFX-buttons. Then creates the dialog and
     * use the layout we just made. After that we add some eventhandlers on the button.
     * Tells the dialog to close if user click close, while if the user press "Add to album" button
     * the handleAddToAlbumButtonPressed(ImageV2 imageV2);
     * @param imageV2
     */
    private void createImagePreviewDialog(ImageV2 imageV2){
        JFXDialogLayout dialogLayout = new JFXDialogLayout();

        JFXButton addToAlbumButton = new JFXButton("Add to album");
        addToAlbumButton.setStyle("-fx-font-family: 'Montserrat Medium'");
        addToAlbumButton.setAlignment(Pos.CENTER);

        JFXButton doneButton = new JFXButton("Done");
        doneButton.setStyle("-fx-font-family: 'Montserrat Medium'");
        doneButton.setAlignment(Pos.CENTER);

        JFXDialog dialog = new JFXDialog(imageDialogPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        doneButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            dialog.close();
            imageDialogPane.toBack();
        });
        addToAlbumButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->{
            handleAddToAlbumButtonPressed(imageV2);
        });

        dialogLayout.setActions(addToAlbumButton, doneButton);
        dialogLayout.setBody(createPreviewImage(imageV2));


        imageDialogPane.toFront();
        dialog.show();
    }

    /**
     * Method for creating a preview image that is shown in the preview image dialog.
     * @param imageV2
     * @return
     */
    private ImageView createPreviewImage(ImageV2 imageV2){
        File file = new File(imageV2.getFilePath());
        Image image = new Image(file.toURI().toString(), 400, 400, true, false);
        ImageView imageView = new ImageView(image);
        return imageView;
    }

    /**
     * Opens a dialog where the user can select album to add the image to.
     * @param imageV2
     */
    private void handleAddToAlbumButtonPressed(ImageV2 imageV2){
        List<Album> albums = new ArrayList<>();

        AlbumDAO albumDAO = new AlbumDAO(DatabaseConnection.getInstance().getEntityManagerFactory());
        albums = albumDAO.getAlbums();

        ChoiceDialog<Album> dialog = new ChoiceDialog<Album>(albums.get(0), albums);
        dialog.setTitle("Choose album");
        dialog.setHeaderText("Add image to an album");
        dialog.setContentText("Choose album:");

        // Traditional way to get the response value.
        Optional<Album> result = dialog.showAndWait();
        if (result.isPresent()){
            albumDAO.createNewAlbumWithImages(dialog.getSelectedItem(), imageV2);
            InformationDialog.showInformationDialog("Image added to album", "Your image has been successfully added in the album!");
        }
    }

    /**
     * Adds a marker to the map.
     */
    public Marker createMarker(double lat, double lng) {
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLong(lat, lng));
        return new Marker(options);
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

    /**
     * Method for handling the text input in the adress textfield.
     * @param event
     */
    @FXML
    public void addressTextFieldAction(ActionEvent event) {
        geocodingService.geocode(address.get(), (GeocodingResult[] results, GeocoderStatus status) -> {

            LatLong latLong = null;

            if( status == GeocoderStatus.ZERO_RESULTS) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No matching address found");
                alert.show();
                return;
            } else if( results.length > 1 ) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Multiple results found, showing the first one.");
                alert.show();
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            } else {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }

            map.setCenter(latLong);

        });
    }
}
