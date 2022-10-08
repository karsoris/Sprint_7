package courier;


import com.github.cliftonlabs.json_simple.JsonException;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.io.IOException;


public class CourierClient extends BaseClient {

    private final String ROOT = "/api/v1/courier";
    private final String COURIER = "/api/v1/courier/{courierId}";
    private final String LOGIN = ROOT + "/login";

    public CourierClient() throws IOException, JsonException {
    }

    @Step("Create Courier")
    public ValidatableResponse create(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();
    }
    @Step("Fail-Create Courier")
    public ValidatableResponse createFailed(Courier courier, int statusCode) {
         ValidatableResponse response = getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all()
                .statusCode(statusCode);
        if (statusCode == 409) {
            response.extract().path("message");
        } else if (statusCode == 400) {
            response.extract().path("message");
        }
        return response;
    }

    public ValidatableResponse login(CourierCredentials creds, int statusCode) {
        return getSpec()
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(statusCode);
    }
    @Step("Delete Courier")
    public void delete(int courierId) {
        getSpec()
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}
