package sample.Java;

import java.time.LocalDate;
import java.util.ArrayList;

public class Album {
    //An ArrayList with images
    private String albumName;
    private ArrayList<Image> images;

    public Album(String albumName, ArrayList<Image> images) {
        this.albumName = albumName;
        this.images = images;
    }

    /**
     * Method to find all the images containing a certain tag
     * @param tag
     * @return returning a list of images containing the requested tag
     */
    public ArrayList<Image> findImagesByTag(String tag) {
        ArrayList<Image> copy = new ArrayList<Image>();
        for(int i = 0; i < this.images.size(); i++) {
            if(this.images.get(i).checkTag(tag)) {
                copy.add(this.images.get(i));
            }
        }
        return copy;
    }

    /**
     * The method will search for images by name
     * @param name
     * @return name
     */
    public String findImageByName(String name){
        return name;
    }
    /**
     * The method will upload an image
     * @return true or false, depending on if the sequence was a success or not
     */
    public boolean uploadImage(Image image){
        if(image == null) {
            return false;
        }
        images.add(image);
        return true;
    }

    /**
     * The method will browse images on the users PC
     * @return true or false, depending on if the sequence was a success or not
     */
    public boolean browseImage(){
        return true;
    }

    /**
     * The method will drop an image into a box
     * @return true or false, depending on if the sequence was a success or not
     */
    public boolean dropImage(){
        return true;
    }


    /**
     * The method will search for images by using date
     * @param location
     * @return location
     */
    public String findImageByLocation(String location){
        return location;
    }

    /**
     * The method will search for images by using date
     * @param date
     * @return date
     */
    public LocalDate findImageByDate(LocalDate date){
        return date;
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
     * searchImage() will search for all images with the equivalent metadata
     * @param metaData: ArrayList of metadata
     * @return images
     */
    public ArrayList<Image> searchImages(ArrayList<ImageMetaData> metaData){
        ArrayList<Image> images = new ArrayList<>();
        return images;
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

