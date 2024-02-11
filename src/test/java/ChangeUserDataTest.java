import api.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static models.UserCreds.fromUser;
import static models.UserData.randomUser;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class ChangeUserDataTest {

    private String token;
    private UserClient userClient;
    private final String emailNew;
    private final String nameNew;

    public ChangeUserDataTest(String email, String name) {
        this.emailNew = email;
            this.nameNew = name;
    }
    @Parameterized.Parameters()
    public static Object[][] getData() {
        return new Object[][]{
                {"randomdgshdg@email.com", "random_name8374316"},
                {"randomdjdsdgshdg@email.com", ""},
                {"", "random_name83ak74316"}
        };
    }
    @Before
    public void setUp(){
        RestAssured.baseURI= Constants.BASE_URI;
        userClient = new UserClient();
    }
    @Test
    @DisplayName("Успешное редактирование данных пользователя")
    @Description("Проверка изменения данных авторизованного пользователя: email и пароль")
    public void changeDataAuthorizedUser(){
        User user = randomUser();
        NewUserCreds userData = new NewUserCreds(nameNew,emailNew);
        userData.setFieldsNew(this.nameNew,this.emailNew);
        userClient.createUser(user);
        Response responseLogin = userClient.loginUser(fromUser(user));
        token = responseLogin.path("accessToken");
        Response response= userClient.changeInfoUser(token, userData);
        response.then().assertThat().statusCode(SC_OK)
                .and().assertThat().body("user.name", equalTo(nameNew)).body("user.email",equalTo(emailNew));
        userClient.deleteUser(token);
    }
    @Test
    @DisplayName("Редактирование данных для неавторизованного пользователя")
    @Description("Проверка изменения данных неавторизованного пользователя: email и пароль")
    public void changeDataUnauthorizedUser(){
        NewUserCreds userData = new NewUserCreds(nameNew,emailNew);
        userData.setFieldsNew(this.nameNew,this.emailNew);
        Response response= userClient.changeInfoUnathorizedUser(userData);
        response.then().assertThat().statusCode(SC_UNAUTHORIZED)
                .and().assertThat().body("message", equalTo("You should be authorised"));
    }
}