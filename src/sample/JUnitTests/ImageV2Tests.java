package sample.JUnitTests;

import com.drew.metadata.MetadataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sample.Java.ImageMetaData;
import sample.Java.ImageV2;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ImageV2Tests {

    private ImageV2 imageV2;
    private ImageMetaData imageMetaData;
    private File file;

    @BeforeEach
    public void BeforeEach() throws MetadataException {
        this.imageV2 = new ImageV2("testName", "Test Nature", "C:\\Users\\odink\\OneDrive – NTNU\\Programmering2\\Piccollect\\piccollect\\src\\sample\\testBildeGPS.jpg");
        this.file = new File("C:\\Users\\odink\\OneDrive – NTNU\\Programmering2\\Piccollect\\piccollect\\src\\sample\\testBildeGPS.jpg");
        this.imageMetaData = new ImageMetaData(file);
    }
    @Test
    public void testCreatingInstanceWithValidData() {
        assertEquals("testName", imageV2.getImageName());
        assertEquals("Test Nature", imageV2.getTags());
        assertEquals("C:\\Users\\odink\\OneDrive – NTNU\\Programmering2\\Piccollect\\piccollect\\src\\sample\\testBildeGPS.jpg", imageV2.getFilePath());
    }

    @ParameterizedTest
    @ValueSource(strings = {""," "})
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
        assertEquals(6000, imageV2.getWidth());
        assertEquals(4000, imageV2.getHeight());
        assertEquals("Kalalau Lookout, Kalepa Ridge Trail, Kauaʻi County, HI, United States of America", imageV2.getLocation());
        LocalDateTime ldt = LocalDateTime.of(2018, 11, 10, 13, 28, 42);
        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        assertEquals(date, imageV2.getDate());
        assertEquals("JPEG", imageMetaData.checkFileType());
        assertNull(imageMetaData.getTimeFromMetaData());
    }
}
