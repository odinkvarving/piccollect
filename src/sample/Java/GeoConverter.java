package sample.Java;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;

/**
 * GeoConverter is a class which converts latitude and longitude to a real address
 */
public class GeoConverter {
    /**
     * This method takes in latitude and longitude as parameters.
     * It uses JOpenCage to convert latitude and longitude to a real address.
     * @param latitude
     * @param longitude
     * @return a formatted address in the form of a string, which displays the location correlating with given longitude and latitude
     */
    public static String reverseGeocoder(double latitude, double longitude){
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("920cd22ea28d4d208d10b6b75e0bbbb6");

        JOpenCageReverseRequest request = new JOpenCageReverseRequest(latitude, longitude);
        request.setLanguage("en"); // prioritize results in a specific language using an IETF format language code
        request.setNoAnnotations(true); // exclude additional info such as calling code, timezone, and currency

        JOpenCageResponse response = jOpenCageGeocoder.reverse(request);

        // get the formatted address of the first result:
        String formattedAddress = response.getResults().get(0).getFormatted();
        // formattedAddress is now 'Travessera de Gràcia, 142, 08012 Barcelona, España'
        return formattedAddress;
    }
}
