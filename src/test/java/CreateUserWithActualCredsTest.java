import api.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Constants;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static models.UserData.randomUser;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserWithActualCredsTest {
    private String token;
    private UserClient userClient;

    @Before
    public void setUp(){
        RestAssured.baseURI= Constants.BASE_URI;
        userClient = new UserClient();
    }
    @Test
    @DisplayName("Создание пользователя c уже существующими данными")
    @Description("Проверка ошибки при создании нового пользователя с существующими данными: email, пароль и имя")
    public void createUserWithActualCreds(){
        User user = randomUser();
        Response response = userClient.createUser(user);
        Response responseAgain = userClient.createUser(user);
        responseAgain.then().assertThat().statusCode(SC_FORBIDDEN)
                .and().assertThat().body("message", equalTo("User already exists"));
        token = response.path("accessToken");
    }
    @After
    public void tearDown() {
        userClient.deleteUser(token);
    }
}

