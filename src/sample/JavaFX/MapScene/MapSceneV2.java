package sample.JavaFX.MapScene;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Locale;

public class MapSceneV2 implements MapComponentInitializedListener {

    private GoogleMapView mapView;
    private TextField adressTextField;
    private GoogleMap map;
    private GeocodingService geocodingService;
    private StringProperty address = new SimpleStringProperty();

    public MapSceneV2(){
        mapView = new GoogleMapView(Locale.getDefault().getLanguage(), "AIzaSyAaCPNXgEw5zlJTHLr0MYOmvxOcQ50vLdw");
        mapView.addMapInitializedListener(this);

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
    }

    public GoogleMapView getMapView() {
        return mapView;
    }

    public void setMapView(GoogleMapView mapView) {
        this.mapView = mapView;
    }

    public TextField getAdressTextField() {
        return adressTextField;
    }

    public void setAdressTextField(TextField adressTextField) {
        this.adressTextField = adressTextField;
    }

    public GoogleMap getMap() {
        return map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public GeocodingService getGeocodingService() {
        return geocodingService;
    }

    public void setGeocodingService(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
}
