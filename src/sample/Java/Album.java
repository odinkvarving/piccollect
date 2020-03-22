package sample.Java;

import com.drew.metadata.Metadata;

import java.time.LocalDate;
import java.util.ArrayList;

public class Album {
    //An ArrayList with images
    private String albumName;

    public Album(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return albumName;
    }



    /**
     * The method will browse images on the users PC
     * @return the filepath of the image, or null
     */
    public String browseImage(){
        SwingFileChooser fileChooser = new SwingFileChooser();
        return fileChooser.browseFiles();
    }

    /**
     * The method will drop an image into a box
     * @return true or false, depending on if the sequence was a success or not
     */
    public boolean dropImage(){
        return true;
    }




    /**
     * The method will search for images by using criteria
     * @param criteria
     * @return criteria
     */

    public String findImageByCriteria(String criteria){
        return criteria;
    }


    /**
     * makeAlbum() will make an album containing images the user have chosen
     * @param images: Images chosen by the user
     * @return some sort of PDF
     */
    /*public PDF makeAlbum(ArrayList<Image> images){
        return PDF;
    }*/

}

