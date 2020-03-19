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

    //ID is the primary key in the image_table, and will be auto-incremented to create a unique ID. We don't have to include this when adding values in the table. 
    public void uploadImageToDatabase(Image image, ArrayList<String> tags, String title) throws SQLException {
        if(image != null) {
            statement = connect.prepareStatement("INSERT INTO image_table (title, path, tags, latitude, longitude, registered)VALUES(?,?,?,?,?,?)");
            statement.setString(1, title);
            statement.setString(2, image.getFile().getAbsolutePath());

            StringBuffer sb = new StringBuffer();
            for(String s : tags) {
                sb.append(s);
                sb.append(" ");
                String str = sb.toString();
                statement.setObject(3, str);
            }
            statement.setString(4, Double.toString(image.getMetaData().getGeoDataFromMetadata().getLatitude()));
            statement.setString(5, Double.toString(image.getMetaData().getGeoDataFromMetadata().getLongitude()));
            try {
                statement.setTimestamp(6, image.getMetaData().getTimeFromMetaData());
            } catch (NullPointerException e) {
                statement.setTimestamp(6, null);
                System.out.println("No timestamp found for this image. ");
            }

            int a = statement.executeUpdate();
            if (a > 0) {
                System.out.println("Image added to database. ");
            }
        }
    }

    //Find out how to select a row that matches given path or id etc. Does not currently work.
    public void getImageInfoFromDatabase(Image image) {
        try {
            String ImagePath = image.getFile().getPath();
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            Statement statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from image_table WHERE imagePath = path");

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
    }

    public static void main(String[] args) throws SQLException, MetadataException {
        ArrayList<String> list = new ArrayList<>();
        list.add("test4");
        list.add("test5");
        list.add("test6");
        Image image = new Image(list, "C:\\Users\\odink\\OneDrive – NTNU\\Programmering2\\Piccollect\\piccollect\\src\\sample\\testBildeGPS.jpg");
        Database database = new Database();
        database.uploadImageToDatabase(image, list, "TestImage");
        database.getImageInfoFromDatabase(image);

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
