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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static models.UserData.randomUser;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderAuthorizedUserTest {
    private String token;
    private UserClient userClient;
    private OrderClient orderClient;

    @Before
    public void setUp(){
        RestAssured.baseURI= Constants.BASE_URI;
        userClient = new UserClient();
        orderClient = new OrderClient();
        User user = randomUser();
        Response response = userClient.createUser(user);
        token = response.path("accessToken");
    }
    @Test
    @DisplayName("Успешное создание нового заказа")
    @Description("Проверка создания нового заказа авторизованным юзером с корректными ингредиентами")
    public void createNewOrderSuccess(){
        Ingredients ingredients = new Ingredients();
        Order order = new Order(ingredients.getIngredientsForOrder());
        Response response =  orderClient.createOrder(token, order);
        response.then().assertThat().statusCode(SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }
    @Test
    @DisplayName("Cоздание нового заказа без ингредиентов")
    @Description("Проверка создания нового заказа авторизованным юзером без ингредиентов")
    public void createNewOrderWithoutIngredients(){
        Order order = new Order();
        Response response =  orderClient.createOrder(token, order);
        response.then().assertThat().statusCode(SC_BAD_REQUEST)
                .and().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }
    @Test
    @DisplayName("Cоздание нового заказа с невалидными ингредиентами")
    @Description("Проверка создания нового заказа авторизованным юзером с невалидными ингредиентами")
    public void createNewOrderWithNotValidIngredients(){
        ArrayList <String> notValidHash = new ArrayList<>();
        notValidHash.add("f4bf34hb34f");
        Order order = new Order(notValidHash);
        Response response =  orderClient.createOrder(token, order);
        response.then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void tearDown() {
        userClient.deleteUser(token);
    }
}
