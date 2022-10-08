package orderTest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import order.OrderClient;
import order.OrdersData;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderTest {


    OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Get all orders") // имя теста
    @Description("Get all orders") // описание теста
    public void getOrders() {
        Response response = orderClient.sendRequestGetOrders();
        OrdersData ordersData = response.body().as(OrdersData.class);
        MatcherAssert.assertThat(response.statusCode(), equalTo(200));
        MatcherAssert.assertThat(ordersData, notNullValue());
    }
}

