package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;
import static models.Constants.*;

public class UserClient {
@Step("Создание пользователя {user}")
    public Response createUser(User user){
    return given()
            .header("Content-type", "application/json")
            .and()
            .body(user)
            .when()
            .post(REGISTER_URL);
}
@Step("Удаление пользователя {user}")
public Response deleteUser(String token){
    return given()
            .header("Authorization", token)
            .when()
            .delete(USER_URL);
    }

}
