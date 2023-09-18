package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import static io.restassured.RestAssured.given;

public class Main {

    //SQl запросы
    private final static String SELECT_WHERE = "SELECT * FROM FOOD WHERE FOOD_NAME=?";
    private final static List<String> foodTypes = Arrays.asList("FRUIT", "VEGETABLE");

    public static void main(String[] args) throws SQLException {
        final ObjectMapper objectMapper = new ObjectMapper();
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
            //Пост запрос для добавления продукта
            Response response = given()
                    .baseUri("http://localhost:8080")
                    .header("Content-type", "application/json")
                    .body(objectMapper.convertValue(testFoodEntity, JsonNode.class))
                    .when()
                    .post("/api/food")
                    .then()
                    .extract()
                    .response();
            resultSet = statementSelect.executeQuery();
            //Сущность добавлена т.е. нашли с помощью SELECT и она одна
            Assertions.assertEquals(resultSet.next(), Boolean.TRUE);
            Assertions.assertEquals(resultSet.next(), Boolean.FALSE);

            String sessionId=response.getSessionId();//Запоминаем айди сессии
            given().sessionId(sessionId)//Гет запрос для получения списка продуктов
                    .baseUri("http://localhost:8080")
                    .when()
                    .get("/api/food")
                    .then()
                    .log().all()
            ;
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