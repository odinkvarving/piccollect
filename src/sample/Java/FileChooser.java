package sample.Java;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.io.File;


public class FileChooser {
    public String browseFiles(){
        JButton open = new JButton();
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        fc.setDialogTitle("Choose image");
        fc.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
        if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
            //
        }
        return fc.getSelectedFile().getAbsolutePath();
    }
}
