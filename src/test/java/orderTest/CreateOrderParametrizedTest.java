package orderTest;

import com.github.cliftonlabs.json_simple.JsonException;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import order.OrderColors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)

public class CreateOrderParametrizedTest {
    OrderClient orderClient = new OrderClient();

    Order order;
    ValidatableResponse response;
    List<String> color;


    public CreateOrderParametrizedTest(List<String> color) {
        this.color = color;
    }


    @Parameterized.Parameters(name = "Тестовые цвета: {0}")


    public static Object[][] getColor() {
        return new Object[][]{
                {OrderColors.getColor().get(0)},
                {OrderColors.getColor().get(1)},
                {OrderColors.getColor().get(2)},
                {OrderColors.getColor().get(3)},
                null
        };
    }


    @Test
    @DisplayName("Create order with any colors")
    @Description("Checking create order with any combinations colors")
    public void createOrder() {
        order = Order.createOrderData(color);
        response = orderClient.sendRequestCreateOrder(order);
        response.assertThat().statusCode(201)
                .and()
                .body("track", notNullValue())
        ;
    }
}