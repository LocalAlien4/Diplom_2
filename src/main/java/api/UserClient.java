package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.NewUserCreds;
import models.User;
import models.UserCreds;

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
@Step("Авторизация пользователя с кредами {userCreds}")
public Response loginUser(UserCreds userCreds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userCreds)
                .when()
                .post(LOGIN_URL);
}
@Step("Изменение данных пользователя с кредами {userCreds}")
public Response changeInfoUser(String token, NewUserCreds newCreds) {
        return given()
                .header("Authorization", token)
                .contentType("application/json")
                .and()
                .body(newCreds)
                .when()
                .patch(USER_URL);
}
    @Step("Изменение данных неавторизованного пользователя")
    public Response changeInfoUnathorizedUser(NewUserCreds newCreds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(newCreds)
                .when()
                .patch(USER_URL);
    }
@Step("Удаление пользователя {user}")
public Response deleteUser(String token){
    return given()
            .header("Authorization", token)
            .contentType("application/json")
            .when()
            .delete(USER_URL);
    }

}
