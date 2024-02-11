package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Order;

import static io.restassured.RestAssured.given;
import static models.Constants.*;

public class OrderClient {
    @Step("Получение списка игредиентов")
    public Response getListOfIngredients(){
        return (Response) given()
                .header("Content-type", "application/json")
                .when()
                .get(INGREDIENTS_URL)
                .then()
                .extract()
                .body();
    }
    @Step("Создание заказа авторизованным пользователем")
    public Response createOrder(String token, Order order) {
        return given()
                .header("Authorization", token)
                .contentType("application/json")
                .and()
                .body(order)
                .when()
                .post(ORDERS_URL);
    }
    @Step("Создание заказа неавторизованным пользователем")
    public Response createOrderWithoutToken(Order order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ORDERS_URL);
    }
    @Step("Получение списка заказов авторизованным пользователем")
    public Response getOrdersWithoutToken() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDERS_URL);
    }
    @Step("Получение списка заказов неавторизованным пользователем")
    public Response getOrdersWithToken(String token) {
        return given()
                .header("Authorization", token)
                .contentType("application/json")
                .when()
                .get(ORDERS_URL);
    }

}
