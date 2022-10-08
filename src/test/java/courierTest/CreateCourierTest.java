package courierTest;

import com.github.cliftonlabs.json_simple.JsonException;
import courier.Courier;
import courier.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;


import java.io.IOException;

import static org.junit.Assert.*;

public class CreateCourierTest {


    Courier courier;

    CourierClient courierClient;


    @Test
    @DisplayName("Create courier")
    @Description("Create random courier")
    public void createCourierTest() throws JsonException, IOException {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
        Boolean isOk = courierClient.create(courier)
                .statusCode(201)
                .extract().path("ok");
        assertTrue(isOk);


    }

    @Test
    @DisplayName("Duplicate courier")
    @Description("Create duplicate courier")
    public void createDublicateCourierTest() throws JsonException, IOException {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
        Boolean isOk = courierClient.create(courier)
                .statusCode(201)
                .extract().path("ok");
        assertTrue(isOk);

        String notOk = courierClient.createFailed(courier, 409)
                .extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", notOk);
    }


}
