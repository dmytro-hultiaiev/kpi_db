package controller;

import entity.*;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import view.Menu;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class Controller {
    private SessionFactory sessionFactory;
    private List<Class> tablesClass;

    public Controller(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;

        tablesClass = new ArrayList<>();
        tablesClass.add(Cars.class);
        tablesClass.add(Employee.class);
        tablesClass.add(Issuing.class);
        tablesClass.add(Service.class);
        tablesClass.add(ServiceStation.class);
    }

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
        try(Session session = sessionFactory.openSession()){
            List<String> tablesName = new ArrayList<>();

            for (Class aClass : tablesClass) {
                tablesName.add(aClass.getSimpleName());
            }

            int tableNum = menu.drawChooseTable(tablesName);
            List<String> columnNames = Arrays.stream(tablesClass.get(tableNum - 1).getDeclaredFields())
                                    .map(field -> ofNullable(field.getAnnotation(Column.class))
                                            .map(Column::name)
                                            .orElse(field.getName()))
                                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

            columnNames.remove("id");
            List<String> data = menu.drawAddDataHibernate(columnNames);

            try {
                session.beginTransaction();

                switch (tablesName.get(tableNum - 1)){
                    case "Cars":
                        Cars car = Cars.builder()
                                .vin(data.get(0))
                                .registrationNumber(data.get(1))
                                .brand(data.get(2))
                                .model(data.get(3))
                                .build();
                        session.persist(car);
                        break;
                    case "Employee":
                        Employee employee = Employee.builder()
                                .driversLicense(data.get(0))
                                .firstName(data.get(1))
                                .lastName(data.get(2))
                                .surName(data.get(3))
                                .build();
                        session.persist(employee);
                        break;
                    case "Issuing":
                        Issuing issuing = Issuing.builder()
                                .vin(data.get(0))
                                .driversLicense(data.get(1))
                                .dateIssue(LocalDate.parse(data.get(2)))
                                .dateReturn(LocalDate.parse(data.get(3)))
                                .build();
                        session.persist(issuing);
                        break;
                    case "Service":
                        Service service = Service.builder()
                                .vin(data.get(0))
                                .serviceName(data.get(1))
                                .serviceStartDate(LocalDate.parse(data.get(2)))
                                .serviceEndDate(LocalDate.parse(data.get(3)))
                                .build();
                        session.persist(service);
                        break;
                    case "ServiceStation":
                        ServiceStation serviceStation = ServiceStation.builder()
                                .name(data.get(0))
                                .addressCity(data.get(1))
                                .addressBuilding(data.get(2))
                                .maxCars(Integer.parseInt(data.get(3)))
                                .build();
                        session.persist(serviceStation);
                        break;
                    default:
                        System.out.println("Error to insert!");
                }

                session.getTransaction().commit();
            } catch (Exception e){
                System.out.println(e.getMessage());
            } finally {
                menu.drawFirstMenu();
            }

        }
    }
    public void controllerUpdate(Menu menu){
        try(Session session = sessionFactory.openSession()){
            List<String> tablesName = new ArrayList<>();

            for (Class aClass : tablesClass) {
                tablesName.add(aClass.getSimpleName());
            }

            int tableNum = menu.drawChooseTable(tablesName);
            List<String> columnNames = Arrays.stream(tablesClass.get(tableNum - 1).getDeclaredFields())
                    .map(field -> ofNullable(field.getAnnotation(Column.class))
                            .map(Column::name)
                            .orElse(field.getName()))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

            columnNames.remove("id");

            Class<?> primaryKeyType = Arrays.stream(tablesClass.get(tableNum - 1).getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findFirst()
                    .map(Field::getType)
                    .orElseThrow(() -> new IllegalArgumentException("Не знайдено поле з аннотацією @Id"));

            String primaryKeyEntity = menu.valuePrimaryKey();

            Object entity;

            if(primaryKeyType.getSimpleName().equals("String")){
                entity = session.get(tablesClass.get(tableNum - 1), primaryKeyEntity);
            } else {
                entity = session.get(tablesClass.get(tableNum - 1), Integer.parseInt(primaryKeyEntity));
            }

            int columnNum = menu.chooseColumn(columnNames, "Choose column to update: ");
            String columnValue = menu.valueForColumnUpdate(columnNames.get(columnNum - 1));

            session.beginTransaction();

            Field field = entity.getClass().getDeclaredField(columnNames.get(columnNum - 1));
            field.setAccessible(true);
            field.set(entity, columnValue);

            session.update(entity);

            session.getTransaction().commit();

        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println(e.getMessage());;
        } finally {
            menu.drawFirstMenu();
        }

    }
    public void controllerDelete(Menu menu){
//        List<String> tableNames = InitDB.getTables();
//        int tableNum = menu.drawChooseTable(tableNames);
//        List<String> columnNames = InitDB.getTableColumns(tableNames.get(tableNum - 1));
//
//        int columnNum = menu.chooseColumn(columnNames, "Choose column with which the data will be deleted: ");
//        String columnValue = menu.valueForColumnUpdate(columnNames.get(columnNum - 1));
//
//        try {
//            DeleteMethod.delete(tableNames.get(tableNum - 1), columnNames.get(columnNum - 1), columnValue);
//            menu.print("Delete information is successful!\n");
//        } catch (SQLException e) {
//            System.out.println(e.getMessage() + "\n");
//        } finally {
//            menu.drawFirstMenu();
//        }

        try(Session session = sessionFactory.openSession()) {
            List<String> tablesName = new ArrayList<>();

            for (Class aClass : tablesClass) {
                tablesName.add(aClass.getSimpleName());
            }

            int tableNum = menu.drawChooseTable(tablesName);

            Class<?> primaryKeyType = Arrays.stream(tablesClass.get(tableNum - 1).getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findFirst()
                    .map(Field::getType)
                    .orElseThrow(() -> new IllegalArgumentException("Не знайдено поле з аннотацією @Id"));

            String primaryKeyEntity = menu.valuePrimaryKey();

            Object entity;

            if(primaryKeyType.getSimpleName().equals("String")){
                entity = session.get(tablesClass.get(tableNum - 1), primaryKeyEntity);
            } else {
                entity = session.get(tablesClass.get(tableNum - 1), Integer.parseInt(primaryKeyEntity));
            }

            session.beginTransaction();

            session.delete(entity);

            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
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
