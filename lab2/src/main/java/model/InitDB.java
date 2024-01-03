package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InitDB {
    public static List<String> getTables(){
        List<String> tableNames = new ArrayList<>();

        try(Connection connection = ConnectionToDB.connectionToDB();
            Statement statement = connection.createStatement();
            ResultSet tableNamesSet = statement.executeQuery("SELECT table_name FROM information_schema.columns WHERE table_schema='public' GROUP BY table_name")) {

            while(tableNamesSet.next()){
                tableNames.add(tableNamesSet.getString("table_name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tableNames;
    }

    public static List<String> getTableColumns(String tableName){
        List<String> tableColumns = new ArrayList<>();

        try(Connection connection = ConnectionToDB.connectionToDB();
            Statement statement = connection.createStatement();
            ResultSet columnsSet = statement.executeQuery("SELECT column_name FROM information_schema.columns WHERE table_name='" + tableName + "'")) {

            while(columnsSet.next()){
                tableColumns.add(columnsSet.getString("column_name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tableColumns;
    }
}