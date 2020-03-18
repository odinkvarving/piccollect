package sample;

import com.sun.javafx.iio.ImageMetadata;
import sample.Java.Image;
import sample.Java.ImageMetaData;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Database {

    private static Connection connect = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static String url = "jdbc:mysql://localhost:3306/connect_mysql_database";
    private static String user = "root";
    private static String pass = "123b1i64er0qx123";

    public Database() throws SQLException {
        connect = DriverManager.getConnection(url, user, pass);
        PreparedStatement statement = connect.prepareStatement("Insert into image_table (id, title, path, tags, latitude, longitude, registered)Values(?,?,?,?,?,?,?)");
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
    }

    public void uploadImageToDatabase(Image image, String tags) {
        image.getMetaData().getUniqueIdFromMetaData();
        image.getFile().getAbsolutePath();
        image.getMetaData().getGeoDataFromMetadata().getLatitude();
        image.getMetaData().getGeoDataFromMetadata().getLongitude();
        image.getMetaData().getTimeFromMetaData();

    }

    public static void main(String[] args) throws SQLException {
        Database database = new Database();

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
