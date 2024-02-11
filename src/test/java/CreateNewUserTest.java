import api.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static models.UserData.randomUser;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateNewUserTest {
    private String token;
    private UserClient userClient;

    @Before
    public void setUp(){
        RestAssured.baseURI= Constants.BASE_URI;
        userClient = new UserClient();
    }
    @Test
    @DisplayName("Создание нового пользователя")
    @Description("Проверка создания нового пользователя с уникальными данными: email, пароль и имя")
    public void createUniqueUser(){
        User user = randomUser();
        Response response = userClient.createUser(user);
        response.then().assertThat().statusCode(SC_OK)
                .and().assertThat().body("success", equalTo(true));
    token = response.path("accessToken");
    }
    @After
    public void tearDown() {
        userClient.deleteUser(token);
    }
}
