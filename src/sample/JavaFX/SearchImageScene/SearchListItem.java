package sample.JavaFX.SearchImageScene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import sample.Java.ImageV2;

/**
 * This class is used to create a HBox with custom settings using an image
 */
public class SearchListItem extends HBox {

    private CheckBox checkBox;
    private ImageView imageView;
    private Label imageName;
    private Label location;
    private Label date;
    private Label tags;

    private ImageV2 imageV2;

    /**
     * The constructor for this class
     * @param imageV2 the image used for the HBox
     */
    public SearchListItem(ImageV2 imageV2){
        this.imageV2 = imageV2;

        this.checkBox = new CheckBox("");
        this.imageView = imageV2.getImage();

        this.imageName = new Label(imageV2.getImageName());
        this.location = new Label(imageV2.getLocation());
        try {
            this.date = new Label(imageV2.getDate().toString());
        } catch (Exception e) {
            this.date = new Label("No date found");
        }
        this.tags = new Label(imageV2.getTags());

        setLabelFonts();
        adjustImageView();
        setPrefSizes();
        adjustHBox();
        initializeMouseEvents();

        getChildren().addAll(imageView, imageName, location, date, tags, checkBox);
    }

    /**
     * Method for setting size-preferences to the object-variables
     */
    private void setPrefSizes(){
        this.imageName.setPrefSize(120, 20);
        this.location.setPrefSize(120, 20);
        this.date.setPrefSize(120, 20);
        this.tags.setPrefSize(150, 20);
    }

    /**
     * Method for adjusting the HBox and defining how it should look
     */
    private void adjustHBox(){
        setAlignment(Pos.CENTER_LEFT);
        setPrefHeight(53);
        prefWidth(700);
        setSpacing(20);
        setPadding(new Insets(10,0,10,10));
        setStyle("-fx-background-color: #ffffff; -fx-background-insets: 0; -fx-border-radius: 5; -fx-border-color: #2681F2");
    }

    /**
     * Method for initializing what happens when the cursor enters different parts of the HBox
     */
    private void initializeMouseEvents(){
        setOnMouseEntered(mouseEvent -> {
            setStyle("-fx-background-color: #DCDCDC; -fx-cursor: hand; -fx-border-radius: 5; -fx-border-color: #2681F2");
        });
        setOnMouseExited(mouseEvent -> {
            setStyle("-fx-background-color: #ffffff; -fx-cursor: pointer; -fx-border-radius: 5; -fx-border-color: #2681F2");
        });
        setOnMouseClicked(mouseEvent -> {
            if(checkBox.isSelected()){
               checkBox.setSelected(false);
            }
            else{
                checkBox.setSelected(true);
            }
        });
    }


    /**
     * Method for adjusting the IamgeView
     */
    private void adjustImageView(){
        this.imageView.isPickOnBounds();
        this.imageView.setOnMouseEntered(e -> {
            imageView.setStyle("-fx-cursor: hand");
        });
        this.imageView.setOnMouseExited(e -> {
            imageView.setStyle("-fx-cursor: pointer");
        });
    }

    /**
     * Method for defining the fonts of the labels
     */
    private void setLabelFonts(){
        imageName.setStyle("-fx-font-family: 'Montserrat Medium'");
        location.setStyle("-fx-font-family: 'Montserrat Medium'");
        date.setStyle("-fx-font-family: 'Montserrat Medium'");
        tags.setStyle("-fx-font-family: 'Montserrat Medium'");
    }

    public ImageV2 getImageV2(){
        return imageV2;
    }

    public CheckBox getCheckBox(){
        return checkBox;
    }


}
