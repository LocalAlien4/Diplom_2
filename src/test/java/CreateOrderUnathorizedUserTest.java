import api.OrderClient;
import api.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Constants;
import models.Ingredients;
import models.Order;
import models.User;
import org.junit.Before;
import org.junit.Test;

import static models.UserData.randomUser;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderUnathorizedUserTest {
    private OrderClient orderClient;

    @Before
    public void setUp(){
        RestAssured.baseURI= Constants.BASE_URI;
        orderClient = new OrderClient();
    }
    @Test
    @DisplayName("Cоздание нового заказа без авторизации")
    @Description("Проверка создания нового заказа неавторизованным юзером с корректными ингредиентами")
    public void createNewOrderWithoutToken(){
        Ingredients ingredients = new Ingredients();
        Order order = new Order(ingredients.getIngredientsForOrder());
        Response response =  orderClient.createOrderWithoutToken(order);
        response.then().assertThat().statusCode(SC_UNAUTHORIZED);
    }
}
