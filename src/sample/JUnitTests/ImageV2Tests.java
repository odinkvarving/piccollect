package sample.JUnitTests;

import com.drew.metadata.MetadataException;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sample.Java.ImageMetaData;
import sample.Java.ImageV2;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import sample.Java.DatabaseConnection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ImageV2Tests {

    private ImageV2 imageV2;
    private ImageMetaData imageMetaData;
    private File file;
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void BeforeEach() throws MetadataException {
        this.file = new File("C:\\Users\\odink\\OneDrive – NTNU\\Programmering2\\Piccollect\\piccollect\\src\\sample\\resources\\testBildeGPS.jpg");
        this.imageMetaData = new ImageMetaData(file);
        this.imageV2 = new ImageV2("testName", "Test Nature", "C:\\Users\\odink\\OneDrive – NTNU\\Programmering2\\Piccollect\\piccollect\\src\\sample\\resources\\testBildeGPS.jpg");
    }

    //Since DatabaseConnection is static, this might result in not being able to create another instance for testing
    //Look for fix
    @Before
    public void init() {
        entityManagerFactory = DatabaseConnection.getInstance().getEntityManagerFactory();
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }


    @Test
    public void testCreatingInstanceWithValidData() {
        assertEquals("testName", imageV2.getImageName());
        assertEquals("Test Nature", imageV2.getTags());
        assertEquals("C:\\Users\\odink\\OneDrive – NTNU\\Programmering2\\Piccollect\\piccollect\\src\\sample\\resources\\testBildeGPS.jpg", imageV2.getFilePath());
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
        assertEquals(6000, imageV2.getWidth());
        assertEquals(4000, imageV2.getHeight());
        assertEquals("Kalalau Lookout, Kalepa Ridge Trail, Kauaʻi County, HI, United States of America", imageV2.getLocation());
        LocalDateTime ldt = LocalDateTime.of(2018, 11, 10, 13, 28, 42);
        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        assertEquals(date, imageV2.getDate());
        assertEquals("JPEG", imageMetaData.checkFileType());
        assertNull(imageMetaData.getTimeFromMetaData());
    }

    @Entity
    public class Event {
        @Id
        @GeneratedValue
        private long id;

        @Temporal(TemporalType.TIMESTAMP)
        private Date createdOn;

        public Event() {
        }

        public Event(Date createdOn) {
            this.createdOn = createdOn;
        }

        public long getId() {
            return id;
        }

        public Date getCreatedOn() {
            return createdOn;
        }
    }


    @Test
    public void testUploadDatabase() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Event event = new Event( new Date() );
        entityManager.persist( event );

        Event dbEvent = entityManager.createQuery(
                "select e " +
                        "from Event e", Event.class)
                .getSingleResult();
        assertEquals(event.getCreatedOn(), dbEvent.getCreatedOn());

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
