package sample.Java;

import javafx.stage.FileChooser;

import java.io.File;

//https://www.youtube.com/watch?v=hNz8Xf4tMI4

public class FXFileChooser {
    public static void main(String[] args) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif"));
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            System.out.println(selectedFile.getAbsolutePath());
        }
    }
}
