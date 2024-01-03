package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateMethod {
    public static void update(String tableName, String column, String newValue, String columnForWhere, String whereValue) throws SQLException {
        try(Connection connection = ConnectionToDB.connectionToDB();
            Statement statement = connection.createStatement()){
            String query = "UPDATE \"" + tableName + "\" SET " + column + " = '" + newValue + "' WHERE " + columnForWhere + " = '" + whereValue +"'";
            statement.executeUpdate(query);
        }
    }
}
