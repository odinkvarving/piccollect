package sample;

import com.drew.metadata.MetadataException;
import com.sun.javafx.iio.ImageMetadata;
import sample.Java.Image;
import sample.Java.ImageMetaData;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Database {

    private static Connection connect = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    private static String url = "jdbc:mysql://localhost:3306/connect_mysql_database";
    private static String user = "root";
    private static String pass = "123b1i64er0qx123";

    public Database() throws SQLException {
        this.connect = DriverManager.getConnection(url, user, pass);
        /*statement = connect.prepareStatement("INSERT INTO image_table (id, title, path, tags, latitude, longitude, registered)VALUES(?,?,?,?,?,?,?)");
        statement.setInt(1, 2);
        statement.setString(2, "TestTitle");
        statement.setString(3, "TestPath");
        statement.setString(4, "TestTags");
        statement.setString(5, "TestLatitude");
        statement.setString(6, "TestLongitude");
        statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));

        int a = statement.executeUpdate();
        if(a > 0) {
            System.out.println("Row updated.");
        }

         */
    }

    //Image can not be uploaded if some of the values have already been added to the database earlier. Deleting the row and re-adding the same values does not fix this problem. Look for fix.
    public void uploadImageToDatabase(Image image, String tags, String title) throws SQLException {
        if(image != null) {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO image_table (id, title, path, tags, latitude, longitude, registered)VALUES(?,?,?,?,?,?,?)");
            statement.setInt(1, 124684);
            statement.setString(2, title);
            statement.setString(3, image.getFile().getAbsolutePath());
            statement.setObject(4, tags);
            statement.setString(5, Double.toString(image.getMetaData().getGeoDataFromMetadata().getLatitude()));
            statement.setString(6, Double.toString(image.getMetaData().getGeoDataFromMetadata().getLongitude()));
            statement.setTimestamp(7, image.getMetaData().getTimeFromMetaData());

            int a = statement.executeUpdate();
            if (a > 0) {
                System.out.println("Image added to database. ");
            }
        }
    }

    public static void main(String[] args) throws SQLException, MetadataException {
        String tags = "ereerwe";
        ArrayList<String> list = new ArrayList<>();
        Image image = new Image(list, "C:\\Users\\odink\\OneDrive – NTNU\\Programmering2\\Piccollect\\piccollect\\src\\sample\\testBildeGPS.jpg");
        Database database = new Database();
<<<<<<< HEAD
        database.uploadImageToDatabase(image, tags, "Testing123");

=======
        database.uploadImageToDatabase(image, tags, "Testinggg");
>>>>>>> OdinDatabase
        /*try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from image_table");

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String path = resultSet.getString(3);
                String tags = resultSet.getString(4);
                String latitude = resultSet.getString(5);
                String longitude = resultSet.getString(6);
                java.util.Date timestamp = resultSet.getTimestamp(7);
                System.out.println(id + " " + title + " " + path + " " + tags + " " + latitude + " " + longitude + " " + timestamp);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        
         */
    }
}
