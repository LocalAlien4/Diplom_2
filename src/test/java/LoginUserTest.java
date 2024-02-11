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
import static models.UserCreds.fromUser;
import static models.UserData.randomUser;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest {
    private String token;
    private UserClient userClient;
    @Before
    public void setUp(){
        RestAssured.baseURI= Constants.BASE_URI;
        userClient = new UserClient();
    }
    @Test
    @DisplayName("Успешная авторизация пользователя")
    @Description("Проверка авторизации пользователя с существующими email и пароль")
    public void loginCorrectUser(){
        User user = randomUser();
        userClient.createUser(user);
        Response response = userClient.login(fromUser(user));
                response.then().assertThat().statusCode(SC_OK)
                .and().assertThat().body("success", equalTo(true));
        token = response.path("accessToken");
    }
    @After
    public void tearDown() {
        userClient.deleteUser(token);
    }
}

