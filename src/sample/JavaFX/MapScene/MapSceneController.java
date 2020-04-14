package sample.JavaFX.MapScene;

import com.drew.metadata.MetadataException;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import sample.Java.DatabaseConnection;
import sample.Java.ImageMetaData;
import sample.Java.ImageV2;
import sample.Java.ImageV2DAO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class MapSceneController implements Initializable, MapComponentInitializedListener {


    private GoogleMapView mapView;
    private GoogleMap map;
    private GeocodingService geocodingService;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField addressTextField;

    @FXML
    private Button backButton;
    @FXML
    private Pane windowMenuButtonBox;


    private StringProperty address = new SimpleStringProperty();
    private ArrayList<ImageV2> images;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchImagesFromDB();
        mapView = new GoogleMapView(Locale.getDefault().getLanguage(), "AIzaSyAaCPNXgEw5zlJTHLr0MYOmvxOcQ50vLdw");
        mapView.addMapInializedListener(this);
        borderPane.setRight(mapView);
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

        mapOptions.center(new LatLong(47.6097, -122.3331))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);
        placeImagesOnMap();


        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<img src=\"https://photos.queens.cz/queens/2018-03/large/ambassadors-komplet-skateboard-black-crown-80662_2.jpg\" alt=\"Italian Trulli\">\n" );

        InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);

        /**
        map.addUIEventHandler(fredWilkieMarker, UIEventType.mouseover, (JSObject obj) -> {
            fredWilkeInfoWindow.open(map, fredWilkieMarker);
        });
**/
    }

    private void fetchImagesFromDB(){
        ImageV2DAO imageV2DAO = new ImageV2DAO(DatabaseConnection.getInstance().getEntityManagerFactory());
        images = (ArrayList<ImageV2>) imageV2DAO.getImages();
    }
    private void placeImagesOnMap(){
        images.stream().forEach(imageV2 -> {
            if(imageV2.getGeoLocation() != null) {
                showMarker(imageV2.getGeoLocation().getLatitude(), imageV2.getGeoLocation().getLongitude());
            }
        });
    }

    /**
     * Adds a marker to the map.
     */
    public void showMarker(double lat, double lng) {
        MarkerOptions options = new MarkerOptions();
        options
                .position(new LatLong(lat, lng));
        Marker marker = new Marker(options);
        map.addMarker(marker);
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
