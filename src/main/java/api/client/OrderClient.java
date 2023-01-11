package api.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private final String orderEndpoint = "api/v1/orders";

    @Step("Get create order response")
    public ValidatableResponse createOrder(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and().body(body)
                .when().post(orderEndpoint)
                .then();
    }

    @Step("Get list of orders response")
    public ValidatableResponse getOrderList() {
        return given()
                .header("Content-type", "application/json")
                .get(orderEndpoint)
                .then();
    }
}
