package courierTest;

import com.github.cliftonlabs.json_simple.JsonException;
import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;


import java.io.IOException;

import static org.junit.Assert.*;

public class LoginCourierTest {

    static Courier courier;
    static CourierClient courierClient;
    private static int courierId;

    @BeforeClass
    public static void setup() throws JsonException, IOException {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
        courierClient.create(courier)
                .extract().path("ok");

    }

    @AfterClass
    public static void teardown() {
        CourierCredentials credsTrue = CourierCredentials.from(courier);
        courierId = courierClient.login(credsTrue, 200)
                .statusCode(200)
                .extract().path("id");
        assertNotEquals(0, courierId);
        courierClient.delete(courierId);
    }


    @Test
    @DisplayName("courier can log in")
    @Description("Successful authorization of a randomly generated courier")
    public void courierLoginTest() {
        CourierCredentials credsTrue = CourierCredentials.from(courier);
        courierId = courierClient.login(credsTrue, 200)
                .extract().path("id");
        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Log in without login")
    @Description("Log in with an empty string")
    public void courierLoginWithoutLoginTest() {

        CourierCredentials creds = new CourierCredentials("", courier.getPassword());
        String message = courierClient.login(creds, 400)
                .statusCode(400)
                .extract().path("message");
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @DisplayName("Log in without login")
    @Description("Log in with an null string")
    public void courierLoginWithNullLoginTest() {

        CourierCredentials creds = new CourierCredentials(null, courier.getPassword());
        String message = courierClient.login(creds, 400)
                .statusCode(400)
                .extract().path("message");
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @DisplayName("Log in without password")
    @Description("Log in with an empty password")
    public void courierLoginWithoutPasswordTest() {

        CourierCredentials creds = new CourierCredentials(courier.getLogin(), "");
        String message = courierClient.login(creds, 400)
                .statusCode(400)
                .extract().path("message");
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test()
    @DisplayName("Log in without password")
    @Description("Log in with an null password")
    public void courierLoginWithNullPasswordTest() {

        CourierCredentials creds = new CourierCredentials(courier.getLogin(), null);
        String message = courierClient.login(creds, 400)
                .statusCode(400)
                .extract().path("message");
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @DisplayName("Log of a non-existent courier")
    @Description("Log of a non-existent courier")
    public void courierLoginWithWrongLoginTest() {

        CourierCredentials creds = new CourierCredentials(RandomStringUtils.randomAlphanumeric(10), courier.getPassword());
        String message = courierClient.login(creds, 404)
                .statusCode(404)
                .extract().path("message");
        assertEquals("Учетная запись не найдена", message);
    }

    @Test
    @DisplayName("Log in with wrong password")
    @Description("Log in with wrong password")
    public void courierLoginWithWrongPasswordTest() {

        CourierCredentials creds = new CourierCredentials(courier.getLogin(), RandomStringUtils.randomAlphanumeric(10));
        String message = courierClient.login(creds, 404)
                .statusCode(404)
                .extract().path("message");
        assertEquals("Учетная запись не найдена", message);
    }
}