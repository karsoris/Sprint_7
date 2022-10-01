package order;


import courier.BaseClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private final String ORDER = "/api/v1/orders";

    public OrderClient() {
    }


    protected RequestSpecification getSpec() {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(BaseClient.BASE_URI)
                ;
    }

    @Step("Create order")
    @Description("Sending post request for create order")
    public ValidatableResponse sendRequestCreateOrder(Order order) {
        ValidatableResponse response =
                getSpec()
                        .body(order)
                        .when()
                        .post(ORDER)
                        .then().log().all();
        return response;

    }


    private Response sendGetRequest(String get) {
        Response response =
                getSpec()
                        .get(get);
        response.then().log().all();
        return response;
    }

    @Step("Send get request for get order")
    @Description("Send get request for get order")
    public Response sendRequestGetOrders() {
        return sendGetRequest(ORDER);
    }

}