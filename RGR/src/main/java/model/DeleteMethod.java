package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DeleteMethod {
    public static void delete(String tableName, String column, String value) throws SQLException {
        try (Connection connection = ConnectionToDB.connectionToDB();
             Statement statement = connection.createStatement()) {

            if(tableName.equals("Issuing") || tableName.equals("Service")){
                ResultSet resultSet = statement.executeQuery("SELECT vin, date_return FROM \"" + tableName +"\" WHERE " + column + " = '" + value + "'");
                String date_return = null;

                while(resultSet.next()){
                    if(tableName.equals("Issuing")){
                        date_return = resultSet.getString("date_return");
                    } else {
                        date_return = resultSet.getString("service_end_date");
                    }
                }

                if(date_return == null){
                    if(tableName.equals("Issuing")){
                        throw new SQLException("This car is currently in use! Try to delete another car!");
                    } else {
                        throw new SQLException("This car is currently in service! Try to delete another car!");
                    }
                }

            }

            String query = "DELETE FROM \"" + tableName + "\" WHERE " + column + " = " + "'" + value + "'";
            statement.executeUpdate(query);
        }
    }
}
