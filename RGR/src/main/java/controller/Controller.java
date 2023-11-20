package controller;

import model.*;
import view.Menu;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Controller {
    public void controllerForFirstMenu(int num, Menu menu){
        switch(num){
            case 1:
                controllerInsert(menu);
                break;
            case 2:
                controllerUpdate(menu);
                break;
            case 3:
                controllerDelete(menu);
                break;
            case 4:
                controllerGenerateData(menu);
                break;
            case 5:
                controllerSearch(menu);
                break;
            default:
                System.out.println("Error!");
        }
    }
    public void controllerInsert(Menu menu){
        List<String> tableNames = InitDB.getTables();
        tableNames.remove("Service Station");
        int tableNum = menu.drawChooseTable(tableNames);
        List<String> columnNames = InitDB.getTableColumns(tableNames.get(tableNum - 1));
        columnNames.remove("id");
        columnNames.remove("date_return");
        HashMap <String,String> data = menu.drawAddData(columnNames);
        try {
            InsertMethod.insert(tableNames.get(tableNum - 1), data);
            menu.print("Add information is successful!\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n");
        } finally {
            menu.drawFirstMenu();
        }
    }
    public void controllerUpdate(Menu menu){
        List<String> tableNames = InitDB.getTables();
        int tableNum = menu.drawChooseTable(tableNames);
        List<String> columnNames = InitDB.getTableColumns(tableNames.get(tableNum - 1));
        columnNames.remove("id");

        int columnNum = menu.chooseColumn(columnNames, "Choose column to update: ");
        String columnValue = menu.valueForColumnUpdate(columnNames.get(columnNum - 1));

        int columnNumWhere = menu.chooseColumn(columnNames, "Choose column for WHERE: ");
        String columnValueWhere = menu.valueForColumnUpdate(columnNames.get(columnNumWhere - 1));

        try {
            UpdateMethod.update(tableNames.get(tableNum - 1), columnNames.get(columnNum - 1), columnValue, columnNames.get(columnNumWhere - 1), columnValueWhere);
            menu.print("Update information is successful!\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n");
        } finally {
            menu.drawFirstMenu();
        }
    }
    public void controllerDelete(Menu menu){
        List<String> tableNames = InitDB.getTables();
        int tableNum = menu.drawChooseTable(tableNames);
        List<String> columnNames = InitDB.getTableColumns(tableNames.get(tableNum - 1));

        int columnNum = menu.chooseColumn(columnNames, "Choose column with which the data will be deleted: ");
        String columnValue = menu.valueForColumnUpdate(columnNames.get(columnNum - 1));

        try {
            DeleteMethod.delete(tableNames.get(tableNum - 1), columnNames.get(columnNum - 1), columnValue);
            menu.print("Delete information is successful!\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n");
        } finally {
            menu.drawFirstMenu();
        }
    }

    public void controllerGenerateData(Menu menu){
        List<String> tableNames = InitDB.getTables();
        tableNames.remove("Service");
        tableNames.remove("Service Station");
        tableNames.remove("Issuing");
        tableNames.remove("Cars");
        int tableNum = menu.drawChooseTable(tableNames);
        int amountOfGenerateData = menu.amountOfGenerateData();
        try{
            if(tableNames.get(tableNum-1).equals("Employee")){
                GenerateData.generateForEmployee(amountOfGenerateData);
            }
            menu.print("Generate information is successful!\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n");
        } finally {
            menu.drawFirstMenu();
        }
    }

    public void controllerSearch(Menu menu) {
        int searchNum = menu.chooseSearchQuery();
        String value = menu.valueForColumnQuery();

        switch(searchNum){
            case 1:
                try {
                    SearchMethod.firstSearchMethod(value);
                } catch (SQLException e) {
                    System.out.println(e.getMessage() + "\n");
                }
                break;
            case 2:
                try {
                    SearchMethod.secondSearchMethod(value);
                } catch (SQLException e) {
                    System.out.println(e.getMessage() + "\n");
                }
                break;
            case 3:
                try {
                    SearchMethod.thirdSearchMethod(value);
                } catch (SQLException e) {
                    System.out.println(e.getMessage() + "\n");
                }
                break;
            default:
                System.out.println("Error!");
        }
    }
}
