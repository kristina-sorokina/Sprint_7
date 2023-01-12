import api.ApiResponse;
import api.Courier;

import api.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {
    private Courier courier;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        baseURI = "http://qa-scooter.praktikum-services.ru/";
        courier = new Courier("testLogin739", "testPassword092", "testName527");
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Check Status code when courier created successfully")
    public void shouldReturn201WhenSuccess() {
        ValidatableResponse response = courierClient.createCourier(courier);
        response.statusCode(201);
    }

    @Test
    @DisplayName("Check Response body when courier created successfully")
    public void shouldReturnCorrectResponseBodyWhenSuccess() {
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Check response status code and message when courier created without password")
    public void shouldReturn400BadRequestWhenNoPassword() {
        String noPasswordCourier = "{\n" +
                "    \"login\": \"testLogin45222\",\n" +
                "    \"firstName\": \"testName7530\"\n" +
                "}";
        ValidatableResponse response = courierClient.createCourier(noPasswordCourier);
        response.statusCode(400);
        response.body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check response status code and message when courier created without login")
    public void shouldReturn400BadRequestWhenNoLogin() {
        String noLoginCourier = "{\n" +
                "    \"password\": \"testPassword0495\",\n" +
                "    \"firstName\": \"testName7530\"\n" +
                "}";
        ValidatableResponse response = courierClient.createCourier(noLoginCourier);
        response.statusCode(400);
        response.body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check response status code and message when same courier created")
    public void shouldReturn409ConflictWhenSameCourierCreated() {
        courierClient.createCourier(courier);

        ValidatableResponse response = courierClient.createCourier(courier);
        response.statusCode(409);
        response.body("message", equalTo("Этот логин уже используется"));
    }

    @After
    public void tearDown() {
        String courierIdJson = String.format(
                "{\n" +
                        "    \"login\": \"%s\",\n" +
                        "    \"password\": \"%s\"\n" +
                        "}", courier.getLogin(), courier.getPassword()
        );
        ValidatableResponse response = courierClient.loginCourier(courierIdJson);
        ApiResponse apiResponse = response.extract().body().as(ApiResponse.class);
        courierClient.deleteCourier(courierIdJson, apiResponse.getId());
    }
}
