import api.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Constants;
import models.UserCreds;
import models.UserData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static models.UserData.randomUser;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class LoginUserWithNotValidsCredsTest {
    private UserClient userClient;
    private final String email;
    private final String password;

    public LoginUserWithNotValidsCredsTest(String email, String password) {
        this.email = email;
        this.password = password;
    }
    @Parameterized.Parameters()
    public static Object[][] getData() {
        return new Object[][]{
                {UserData.email, UserData.password},
                {UserData.email, ""},
                {"", UserData.password},
                {"", ""}
        };
    }
    @Before
    public void setUp(){
        RestAssured.baseURI= Constants.BASE_URI;
        userClient = new UserClient();
    }
    @Test
    @DisplayName("Авторизация пользователя с несуществующими или пустыми данными")
    @Description("Проверка авторизации пользователя с несуществующими email и пароль, а так же при пустых полях")
    public void loginNotValidCredsUser(){
        UserCreds user = UserCreds.fromUser(randomUser());
        user.setEmailAndPass(this.email,this.password);
        Response response = userClient.loginUser(user);
        response.then().assertThat().statusCode(SC_UNAUTHORIZED)
                .and().assertThat().body("message", equalTo("email or password are incorrect"));
    }
}
