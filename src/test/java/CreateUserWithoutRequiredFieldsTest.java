import api.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Constants;
import models.User;
import models.UserData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CreateUserWithoutRequiredFieldsTest {
    private final String email;
    private final String password;
    private final String name;
    private UserClient userClient;

    public CreateUserWithoutRequiredFieldsTest(String email, String password, String name) {
        this.name = name;
        this.email = email;
        this.password = password;

    }
    @Parameterized.Parameters()
    public static Object[][] getData() {
        return new Object[][]{
                {UserData.name, UserData.email, ""},
                {UserData.name, "", UserData.password},
                {"", UserData.email, UserData.password},
                {"", UserData.email, ""},
                {"", "", ""}
        };
    }
    @Before
    public void setUp(){
        RestAssured.baseURI= Constants.BASE_URI;
        userClient = new UserClient();
    }
    @Test
    @DisplayName("Создание пользователя без обязательных полей")
    @Description("Проверка ошибки при создании нового пользователя без обязательных полей")
    public void createUserWithoutRequiredFieldsCreds(){
        User user = new User();
        user.setFields(this.name, this.email, this.password);
        System.out.println(user);
        Response response = userClient.createUser(user);
        response.then().assertThat().statusCode(SC_FORBIDDEN)
                .and().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
}

