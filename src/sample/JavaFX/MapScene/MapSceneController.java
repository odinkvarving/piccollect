package sample.JavaFX.MapScene;

import com.drew.metadata.MetadataException;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
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
import com.lynden.gmapsfx.util.MarkerImageFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;
import sample.Java.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MapSceneController implements Initializable, MapComponentInitializedListener {


    private GoogleMapView mapView;
    private GoogleMap map;
    private GeocodingService geocodingService;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private AnchorPane mapAnchorPane;

    @FXML
    private TextField addressTextField;

    @FXML
    private Button backButton;
    @FXML
    private Pane windowMenuButtonBox;

    @FXML
    private StackPane imageDialogPane;

    private StringProperty address = new SimpleStringProperty();
    private ArrayList<ImageV2> images;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchImagesFromDB();

        mapView = new GoogleMapView(Locale.getDefault().getLanguage(), "AIzaSyAaCPNXgEw5zlJTHLr0MYOmvxOcQ50vLdw");
        mapView.addMapInializedListener(this);

        mapAnchorPane.getChildren().add(mapView);
        mapAnchorPane.setLeftAnchor(mapView, 0.0);
        mapAnchorPane.setRightAnchor(mapView, mapAnchorPane.getWidth());

        addressTextField.toFront();
        address.bind(addressTextField.textProperty());

        try {
            Node windowMenuButtonsNode = FXMLLoader.load(getClass().getResource("../WindowMenuButtons/WindowMenuButtons.fxml"));
            windowMenuButtonBox.getChildren().add(windowMenuButtonsNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void fetchImagesFromDB(){
        ImageV2DAO imageV2DAO = new ImageV2DAO(DatabaseConnection.getInstance().getEntityManagerFactory());
        images = (ArrayList<ImageV2>) imageV2DAO.getImages();
    }
    private void placeImagesOnMap(){
        images.stream().forEach(imageV2 -> {
            if(imageV2.getGeoLocation() != null) {
                Marker marker = createMarker(imageV2.getGeoLocation().getLatitude(), imageV2.getGeoLocation().getLongitude());
                addMarkerClickHandler(marker, imageV2);
                map.addMarker(marker);
            }
        });
    }
    private void addMarkerClickHandler(Marker marker, ImageV2 imageV2){
        map.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {

            if(imageDialogPane.getChildren().size() > 0){
                imageDialogPane.getChildren().clear();
            }

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
        });
    }

    private ImageView createPreviewImage(ImageV2 imageV2){
        File file = new File(imageV2.getFilePath());
        Image image = new Image(file.toURI().toString(), 400, 400, true, false);
        ImageView imageView = new ImageView(image);
        return imageView;
    }

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
        Scene scene;
        try {
            scene = new Scene(mainSceneLoader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
