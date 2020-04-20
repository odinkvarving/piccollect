package sample.JUnitTests;

import com.drew.metadata.MetadataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import sample.Java.ImageMetaData;
import sample.Java.ImageV2;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ImageV2Tests {

    private ImageV2 imageV2;
    private ImageMetaData imageMetaData;
    private File file;

    @BeforeEach
    public void BeforeEach() throws MetadataException {
        this.file = new File("C:\\PiccollectSamplePictures\\boatIbiza.jpg");
        this.imageMetaData = new ImageMetaData(file);
        this.imageV2 = new ImageV2("testName", "Test Nature", "C:\\PiccollectSamplePictures\\boatIbiza.jpg");

    }

    @Test
    public void testCreatingInstanceWithValidData() {
        assertEquals("testName", imageV2.getImageName());
        assertEquals("Test Nature", imageV2.getTags());
        assertEquals("C:\\PiccollectSamplePictures\\boatIbiza.jpg", imageV2.getFilePath());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void testCreatingInstanceWithInvalidData() {
        try {
            this.file = new File ("1234");
            this.imageMetaData = new ImageMetaData(file);
            this.imageV2 = new ImageV2("invalidTest", "error", "1234");
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void testSettingNewImageName(String newName) {
        imageV2.setImageName(newName);
        assertEquals(newName, imageV2.getImageName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void testSettingNewImageTags(String tags) {
        imageV2.setTags(tags);
        assertEquals(tags, imageV2.getTags());
    }

    @Test
    public void testExtractingImageMetaData() {
        assertEquals(800, imageV2.getWidth());
        assertEquals(598, imageV2.getHeight());
        assertEquals("Avinguda de les Andanes, 07800 Ibiza, Spain", imageV2.getLocation());
        LocalDateTime ldt = LocalDateTime.of(2011, 9, 4, 14, 51, 11);
        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        assertEquals(date, imageV2.getDate());
        assertEquals("JPEG", imageMetaData.checkFileType());
        assertNull(imageMetaData.getTimeFromMetaData());
    }
}
