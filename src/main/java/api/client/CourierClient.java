package api.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private final String createEndpoint = "api/v1/courier";
    private final String loginEndpoint = "api/v1/courier/login";
    private final String deleteEndpoint = "api/v1/courier/%s";

    @Step("Get create courier response")
    public ValidatableResponse createCourier(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and().body(body)
                .when().post(createEndpoint)
                .then();
    }

    @Step("Get courier login response")
    public ValidatableResponse loginCourier(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and().body(body)
                .when().post(loginEndpoint)
                .then();
    }

    @Step("Get courier delete response")
    public ValidatableResponse deleteCourier(Object body, int courierId) {
        return given()
                .header("Content-type", "application/json")
                .and().body(body)
                .when().delete(String.format(deleteEndpoint, String.valueOf(courierId)))
                .then();
    }
}
