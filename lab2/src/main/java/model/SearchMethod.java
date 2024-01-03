package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchMethod {
    public static void firstSearchMethod(String data) throws SQLException{
        try(Connection connection = ConnectionToDB.connectionToDB(); Statement statement = connection.createStatement()){

            long startTime = System.currentTimeMillis();

            ResultSet resultSet = statement.executeQuery("SELECT \"Cars\".brand, \"Employee\".last_name, \"Employee\".first_name, \"Issuing\".date_issue from \"Issuing\"\n" +
                    "JOIN \"Cars\" ON \"Issuing\".vin = \"Cars\".vin\n" +
                    "JOIN \"Employee\" ON \"Issuing\".№_drivers_licensce = \"Employee\".№_drivers_license\n" +
                    "WHERE \"Issuing\".date_issue = '" + data + "'\n" +
                    "GROUP BY \"Cars\".brand, \"Employee\".first_name, \"Employee\".last_name, \"Issuing\".date_issue");

            long endTime = System.currentTimeMillis();

            while(resultSet.next()){
                String brand = resultSet.getString("brand");
                String last_name = resultSet.getString("last_name");
                String first_name = resultSet.getString("first_name");
                String date_issue = resultSet.getString("date_issue");

                System.out.printf("%-12s | %-12s | %-12s | %-12s \n", brand, last_name, first_name, date_issue);
            }
            System.out.println("\nQuery time: " + (endTime -startTime) + " mils");
        }
    }

    public static void secondSearchMethod(String data) throws SQLException{
        try(Connection connection = ConnectionToDB.connectionToDB(); Statement statement = connection.createStatement()){

            long startTime = System.currentTimeMillis();

            ResultSet resultSet = statement.executeQuery("SELECT \"Cars\".brand, \"Employee\".last_name, \"Employee\".first_name, \"Issuing\".date_issue from \"Issuing\"\n" +
                    "JOIN \"Cars\" ON \"Issuing\".vin = \"Cars\".vin\n" +
                    "JOIN \"Employee\" ON \"Issuing\".№_drivers_licensce = \"Employee\".№_drivers_license\n" +
                    "WHERE \"Cars\".brand = '" + data + "'\n" +
                    "GROUP BY \"Cars\".brand, \"Employee\".first_name, \"Employee\".last_name, \"Issuing\".date_issue");

            long endTime = System.currentTimeMillis();

            while(resultSet.next()){
                String brand = resultSet.getString("brand");
                String last_name = resultSet.getString("last_name");
                String first_name = resultSet.getString("first_name");
                String date_issue = resultSet.getString("date_issue");

                System.out.printf("%-12s | %-12s | %-12s | %-12s \n", brand, last_name, first_name, date_issue);
            }
            System.out.println("\nQuery time: " + (endTime -startTime) + " mils");
        }
    }

    public static void thirdSearchMethod(String data) throws SQLException{
        try(Connection connection = ConnectionToDB.connectionToDB(); Statement statement = connection.createStatement()){

            long startTime = System.currentTimeMillis();

            ResultSet resultSet = statement.executeQuery("SELECT \"Cars\".brand, \"Cars\".model, \"Service\".service_start_date, \"Service\".service_end_date FROM \"Service\"\n" +
                    "JOIN \"Cars\" ON \"Cars\".vin = \"Service\".vin\n" +
                    "WHERE \"Cars\".brand = '" + data + "'\n" +
                    "GROUP BY \"Cars\".brand, \"Cars\".model, \"Service\".service_start_date, \"Service\".service_end_date");

            long endTime = System.currentTimeMillis();

            while(resultSet.next()){
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String service_start_date = resultSet.getString("service_start_date");
                String service_end_date = resultSet.getString("service_end_date");

                System.out.printf("%-12s | %-12s | %-12s | %-12s \n", brand, model, service_start_date, service_end_date);
            }
            System.out.println("\nQuery time: " + (endTime -startTime) + " mils");
        }
    }
}
