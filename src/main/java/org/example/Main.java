package org.example;

import org.junit.jupiter.api.Assertions;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class Main {
    //SQl запросы
    private final static String SELECT_WHERE = "SELECT * FROM FOOD WHERE FOOD_NAME=?";
    private final static String INSERT = "INSERT INTO FOOD(FOOD_NAME,FOOD_TYPE,FOOD_EXOTIC) VALUES (?,?,?)";
    private final static String DELETE = "DELETE FROM FOOD WHERE FOOD_NAME=?";
    private final static List<String> foodTypes = Arrays.asList("FRUIT", "VEGETABLE");

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        PreparedStatement statementInsert = null;
        PreparedStatement statementSelect = null;
        PreparedStatement statementDelete = null;
        //Генерим рандомную сущность в таблицу
        FoodEntity testFoodEntity = new FoodEntity();
        testFoodEntity.setFoodName(UUID.randomUUID().toString());
        testFoodEntity.setFoodType(foodTypes.get((int) Math.round(Math.random())));
        testFoodEntity.setFoodExotic(BigDecimal.valueOf(Math.round(Math.random())));
        try {
            //Открываем connection
            connection = H2DataBaseConnection.getH2DataSource().getConnection();
            //Стейтмент для SELECT
            statementSelect = connection.prepareStatement(SELECT_WHERE);
            //Задаем параметры на месте ?
            statementSelect.setString(1, testFoodEntity.getFoodName());
            //Записываем в resultSet результат Select
            ResultSet resultSet = statementSelect.executeQuery();
            //Проверяем есть ли сущность в таблице(не должно быть) т.к. не добавили еще
            Assertions.assertEquals(resultSet.next(), Boolean.FALSE);
            //Стейтмент для INSERT
            statementInsert = connection.prepareStatement(INSERT);
            statementInsert.setString(1, testFoodEntity.getFoodName());
            statementInsert.setString(2, testFoodEntity.getFoodType());
            statementInsert.setBigDecimal(3, testFoodEntity.getFoodExotic());
            //Смотрим количество записей,затронутых операцией
            int insertCount = statementInsert.executeUpdate();
            //Количество вставок равно 1
            Assertions.assertEquals(insertCount, 1);

            resultSet = statementSelect.executeQuery();
            //Сущность добавлена т.е. нашли с помощью SELECT и она одна
            Assertions.assertEquals(resultSet.next(), Boolean.TRUE);
            Assertions.assertEquals(resultSet.next(),Boolean.FALSE);
            //Стейтмент для DELETE
            statementDelete = connection.prepareStatement(DELETE);
            statementDelete.setString(1, testFoodEntity.getFoodName());
            //Удаляем и смотрим сколько строк удалили
            int deleteCount = statementDelete.executeUpdate();
            Assertions.assertEquals(deleteCount, 1);
             //Сущность удалена,в таблице не найдена
            resultSet = statementSelect.executeQuery();
            Assertions.assertEquals(resultSet.next(), Boolean.FALSE);

        } catch (Exception e) {
           e.printStackTrace();
        } finally {//Закрываем statement и connection
            if (statementSelect != null) {
                statementSelect.close();
            }
            if (statementInsert != null) {
                statementInsert.close();
            }
            if (statementDelete != null) {
                statementDelete.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}