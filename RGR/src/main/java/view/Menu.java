package view;

import controller.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Menu {
    Scanner scanner;
    Controller controller;

    public Menu(Controller controller){
        scanner = new Scanner(System.in);
        this.controller = controller;
    }
    public void print(String text){
        System.out.println(text);
    }
    public void drawFirstMenu(){
        System.out.println("1. Add data to tables");
        System.out.println("2. Update data in tables");
        System.out.println("3. Delete data in tables");
        System.out.println("4. Generate data in tables");
        System.out.println("5. Search data in tables");

        System.out.print("\nChoose your action: ");
        int result = Integer.parseInt(scanner.next());

        while(true){
            if(result > 0 && result <= 5){
                break;
            } else {
                System.out.println("Error! No such item exists. Try entering another number!");
                System.out.print("\nChoose your action: ");
                result = Integer.parseInt(scanner.next());
            }
        }

        controller.controllerForFirstMenu(result, this);
    }
    public int drawChooseTable(List<String> tableNames){
        System.out.println(" ");
        for(int i = 0; i < tableNames.size(); i++){
            System.out.println((i+1) + ". " + tableNames.get(i));
        }
        System.out.print("\nChoose table: ");
        int result = Integer.parseInt(scanner.next());

        while(true){
            if(result > 0 && result <= tableNames.size()){
                break;
            } else {
                System.out.println("Error! No such table exists. Try entering another number!");
                System.out.print("\nChoose table: ");
                result = Integer.parseInt(scanner.next());
            }
        }

        return result;
    }
    public HashMap<String,String> drawAddData(List<String> columnNames){
        HashMap<String,String> data = new HashMap<>();
        System.out.println("\nEnter data for variables:");

        for (String columnName : columnNames) {
            System.out.print(columnName + " - ");
            String str = scanner.next();
            data.put(columnName, str);
        }

        return data;
    }

    public List<String> drawAddDataHibernate(List<String> columnNames){
        List<String> data = new ArrayList<>();
        System.out.println("\nEnter data for variables:");

        for (String columnName : columnNames) {
            System.out.print(columnName + " - ");
            String str = scanner.next();
            data.add(str);
        }

        return data;
    }
    public int chooseColumn(List<String> columnNames, String text){
        System.out.println(" ");
        for(int i = 0; i < columnNames.size(); i++){
            System.out.println((i+1) + ". " + columnNames.get(i));
        }
        System.out.print(text);
        int result = scanner.nextInt();

        while(true){
            if(result > 0 && result <= columnNames.size()){
                break;
            } else {
                System.out.println("Error! No such column exists. Try entering another number!");
                System.out.print("\nChoose table: ");
                result = Integer.parseInt(scanner.next());
            }
        }

        return result;
    }
    public String valueForColumnUpdate(String columnName){
        Scanner scanner4 = new Scanner(System.in);
        System.out.print("\nEnter value: \n" + columnName + " - ");
        return scanner4.nextLine();
    }
    public String valuePrimaryKey(){
        Scanner scanner4 = new Scanner(System.in);
        System.out.print("\nEnter value of Primary Key:");
        return scanner4.nextLine();
    }
    public int amountOfGenerateData(){
        Scanner scanner2 = new Scanner(System.in);
        System.out.print("\nEnter amount of generate data: ");
        return Integer.parseInt(scanner2.nextLine());
    }
    public int chooseSearchQuery(){
        System.out.println(" ");

        System.out.println("1. First query");
        System.out.println("2. Second query");
        System.out.println("3. Third query");

        System.out.print("\nChoose query: ");
        int result = Integer.parseInt(scanner.next());

        while(true){
            if(result > 0 && result <= 3){
                break;
            } else {
                System.out.println("Error! No such query exists. Try entering another number!");
                System.out.print("\nChoose query: ");
                result = Integer.parseInt(scanner.next());
            }
        }

        return result;
    }

    public String valueForColumnQuery(){
        Scanner scanner3 = new Scanner(System.in);
        System.out.print("\nEnter value for query: ");
        return scanner3.nextLine();
    }
}
