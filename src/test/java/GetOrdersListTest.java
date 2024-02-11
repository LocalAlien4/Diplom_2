import api.OrderClient;
import api.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Constants;
import models.Order;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static models.UserData.randomUser;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrdersListTest {
    private String token;
    private UserClient userClient;
    private OrderClient orderClient;

    @Before
    public void setUp(){
        RestAssured.baseURI= Constants.BASE_URI;
        orderClient = new OrderClient();
    }
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка получения списка заказов авторизованным юзером")
    public void getOrdersWithAuthorizedUser(){
        userClient = new UserClient();
        User user = randomUser();
        Response responseUser = userClient.createUser(user);
        token = responseUser.path("accessToken");
        Response response =  orderClient.getOrdersWithToken(token);
        response.then().assertThat().statusCode(SC_OK)
                .and().assertThat().body("success", equalTo(true));
        userClient.deleteUser(token);
    }
    @Test
    @DisplayName("Получение списка заказов без авторизации")
    @Description("Проверка получения списка заказов неавторизованным юзером")
    public void getOrdersWithNotAuthorizedUser(){
        Response response =  orderClient.getOrdersWithoutToken();
        response.then().assertThat().statusCode(SC_UNAUTHORIZED)
                .and().assertThat().body("message", equalTo("You should be authorised"));
    }
}
