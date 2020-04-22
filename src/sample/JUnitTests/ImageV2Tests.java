package sample.JUnitTests;

import com.drew.metadata.MetadataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import sample.Java.ImageMetaData;
import sample.Java.ImageV2;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * ImageV2Tests is a test-class that uses JUnit-tests for some of the methods in our application
 * the @Test tells the compiler that this method is connected to a JUnit-test
 * the @ParameterizedTest tells the compiler that this test has parameters
 * the @ValueSource Defines what type of parameter will be used
 */
public class ImageV2Tests {

    private ImageV2 imageV2;
    private ImageMetaData imageMetaData;
    private File file;

    /**
     * Method for defining a task at the start of every It-block
     * the @BeforeEach command that allow you to define setup and teardown tasks that are performed at the beginning and end of every It block (For example before every assert-method).
     * @throws MetadataException the exception that is thrown
     */
    @BeforeEach
    public void BeforeEach() throws MetadataException {
        this.file = new File("C:\\PiccollectSamplePictures\\boatIbiza.jpg");
        this.imageMetaData = new ImageMetaData(file);
        this.imageV2 = new ImageV2("testName", "Test Nature", "C:\\PiccollectSamplePictures\\boatIbiza.jpg");

    }

    /**
     * Test-method for creating an instance of ImageV2 with valid data
     * Checks if we are able to create a ImageV2 object with input that should be accepted
     */
    @Test
    public void testCreatingInstanceWithValidData() {
        assertEquals("testName", imageV2.getImageName());
        assertEquals("Test Nature", imageV2.getTags());
        assertEquals("C:\\PiccollectSamplePictures\\boatIbiza.jpg", imageV2.getFilePath());
    }

    /**
     * Parameterized test-method for creating an instance of ImageV2 with invalid data. Checks if we are able to create a ImageV2 object with input that should not be accepted.
     * Fails if the object is created with some invalid input, so that the test actually "succeeds".
     * Prints the stacktrace in catch-block to show what input is invalid.
     */
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

    /**
     * Simple parameterized test for testing the setImageName-method in ImageV2
     * the @assertEquals Asserts that the string in the parameter is equal to the string from getImageName-method
     * @param newName the new name for the image
     */
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void testSettingNewImageName(String newName) {
        imageV2.setImageName(newName);
        assertEquals(newName, imageV2.getImageName());
    }

    /**
     * Simple parameterized test for testing the setTags-method in ImageV2
     * the @assertEquals Asserts that the string in the parameter is equal to the string from getTags-method
     * @param tags the tags that will be added
     */
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void testSettingNewImageTags(String tags) {
        imageV2.setTags(tags);
        assertEquals(tags, imageV2.getTags());
    }

    /**
     * Test-method for testing all the metadata-methods in ImageV2.
     * the @assertEquals Asserts equality between information already defined to ImageV2 object in the @BeforeEach command, and the get-methods for these parameters
     */
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
