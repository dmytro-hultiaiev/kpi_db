package model;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class InsertMethod {
    private static String createInsertQuery(String tableName, Map<String, String> data){
        StringBuilder insertQuery = new StringBuilder("INSERT INTO \"" + tableName + "\" (");
        Set<String> dataKeySet = data.keySet();
        Iterator<String> iterator = dataKeySet.iterator();

        for(int i = 0; i < dataKeySet.size(); i++){
            if(i != dataKeySet.size() - 1){
                insertQuery.append(iterator.next()).append(",");
            } else {
                insertQuery.append(iterator.next()).append(")");
            }
        }

        insertQuery.append(" VALUES (");

        for(int i = 0; i < dataKeySet.size(); i++){
            if(i != dataKeySet.size() - 1){
                insertQuery.append("?").append(",");
            } else {
                insertQuery.append("?)");
            }
        }

        return String.valueOf(insertQuery);
    }

    public static void insert(String tableName, Map<String, String> data) throws SQLException {
        try(Connection connection = ConnectionToDB.connectionToDB(); PreparedStatement preparedStatement = connection.prepareStatement(createInsertQuery(tableName, data))){
            Set<String> dataKeySet = data.keySet();
            Iterator<String> iterator = dataKeySet.iterator();

            if(tableName.equals("Issuing")){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT vin, date_return FROM \"Issuing\"");
                Map<String, String> map = new HashMap<>();

                while(resultSet.next()){
                    map.put(resultSet.getString("vin"), resultSet.getString("date_return"));
                }

                if(map.containsKey(data.get("vin")) && map.get(data.get("vin")) == null){
                    throw new SQLException("This car is currently in use! Try another car!");
                }

                statement.close();
                resultSet.close();
            }

            for(int i = 0; i < data.size(); i++){
                String column = iterator.next();
                if(column.equals("date_issue") || column.equals("date_return") || column.equals("service_start_date") || column.equals("service_end_date")){
                    preparedStatement.setDate(i+1, Date.valueOf(data.get(column)));
                } else {
                    preparedStatement.setString(i+1, data.get(column));
                }
            }

            preparedStatement.executeUpdate();
        }
    }
}
