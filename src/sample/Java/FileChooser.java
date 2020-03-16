package sample.Java;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A class to browse your files
 */
public class FileChooser extends JFileChooser {

    /**
     * Method to browse your files
     * @return the pathname or null
     */
    public String browseFiles(){
        JButton open = new JButton();
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        fc.setDialogTitle("Choose image");
        fc.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images","jpeg","gif","png");
        fc.setFileFilter(filter);

        //opens the file directory and if a file is selected, returns the files path
        if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }
        //if a file is not chosen, the method will return null
        return null;
    }
}
