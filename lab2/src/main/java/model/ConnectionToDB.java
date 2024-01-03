package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionToDB {
    public static Connection connectionToDB(){
        Connection connection = null;

        try(FileInputStream file = new FileInputStream("src/main/resources/config.properties")) {
            Properties properties = new Properties();
            properties.load(file);
            connection = DriverManager.getConnection(properties.getProperty("URL"), properties.getProperty("USERNAME"), properties.getProperty("PASSWORD"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Connection Failed : " + e.getMessage());
        }

        return connection;
    }
}
