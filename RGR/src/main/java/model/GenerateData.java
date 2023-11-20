package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GenerateData {
    public static void generateForEmployee(int amount) throws SQLException {
        try(Connection connection = ConnectionToDB.connectionToDB();
            Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("select max(№_drivers_license) as max_num from \"Employee\"");

            int maxNum = 1000000;

            while (resultSet.next()) {
                maxNum = resultSet.getInt("max_num") + 1;
            }

            if(maxNum == 0){
                maxNum = 1000000;
            }

            int limitNum = maxNum + amount;

            String query = "insert into \"Employee\" (№_drivers_license, first_name, last_name, surname)\n" +
                    "select \n" +
                    "row_number() OVER () as №_drivers_license, \n" +
                    "first_name, \n" +
                    "last_name, \n" +
                    "surname\n" +
                    "from generate_series(1, 100) as №_drivers_license\n" +
                    "cross join\n" +
                    "(select unnest(array['Ivan','Dmytro','Aleksandr','Aleksey','Vadim','Viktor','Kyrilo','Denis','Nikita','Oleg','Pavlo','Petro','Roman','Ruslan','Sergiy','Stepan','Fedir','Anton','Andriy','Yuriy']) as first_name) as f\n" +
                    "cross join\n" +
                    "(select unnest(array['Melnyk','Schevchenko','Kovalenko','Bondarenko','Tkaschenko','Kravchenko','Kovalchuk','Schevchuk','Oliynik','Tkachuk','Savchenko','Bondar','Marchenko','Rudenko','Moroz','Petrenko','Pavlenko','Vasilenko','Levchenko','Karpenko','Gavrulyk','Popov','Panchenko','Mazur','Homenko','Goncharenko','Kostenko','Kostyk','Kozak','Federenko','Kovtun','Bilous','Nesterenko','Tarasenko','Vovk','Zhuk','Vlasenko','Radchenko','Voloshyn','Velyshko']) as last_name) as l\n" +
                    "cross join\n" +
                    "(select unnest(array['Alexandrovich','Sergiyvich','Petrovich','Bogdanovich','Denisovich','Olegovich','Ruslanovich','Romanovich','Stepanovich','Fedirovich','Antonovich','Andriyovich','Viltorovich','Ivanovich','Vadimovich','Kyrilovich','Alekseyovich','Dmytrovich','Maksymovich','Vladislavovich']) as surname) as s\n" +
                    "order by random()\n" +
                    "LIMIT " + amount;
            statement.executeUpdate(query);
        }
    }
}
