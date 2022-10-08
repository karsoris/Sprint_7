package courierTest;

import com.github.cliftonlabs.json_simple.JsonException;
import courier.Courier;
import courier.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateCourierParametrizedTest {
    private final String login;
    private final String password;
    private final String firstName;

    Courier courier;
    CourierClient courierClient;

    public CreateCourierParametrizedTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;

    }

    @Parameterized.Parameters(name = " login: {0} password: {1} firstName : {2}")
    public static Object[][] getCreateCourierData() {
        return new Object[][]{
                {null, "P@ssw0rd", RandomStringUtils.randomAlphabetic(10)},
                {"", "P@ssw0rd", RandomStringUtils.randomAlphabetic(10)},
                {RandomStringUtils.randomAlphabetic(10), null, RandomStringUtils.randomAlphabetic(10)},
                {RandomStringUtils.randomAlphabetic(10), "", RandomStringUtils.randomAlphabetic(10)}
        };
    }


    @Test
    @DisplayName("Create courier")
    @Description("Create courier with all combinations")
    public void createCourierWithoutAllFields() throws JsonException, IOException {
        courier = Courier.getRandomCourierWithoutAnyField(login, password, firstName);
        courierClient = new CourierClient();
        String notOk = courierClient.createFailed(courier, 400)
                .extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", notOk);

    }
}
