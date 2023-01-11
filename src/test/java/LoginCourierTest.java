import api.ApiResponse;
import api.Courier;
import api.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    private Courier courier;
    private String courierIdJson;
    private ValidatableResponse response;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        baseURI = "http://qa-scooter.praktikum-services.ru/";
        courier = new Courier("testLogin609", "testPassword482", "testName371");
        courierClient = new CourierClient();
        courierClient.createCourier(courier);
        courierIdJson = String.format(
                "{\n" +
                        "    \"login\": \"%s\",\n" +
                        "    \"password\": \"%s\"\n" +
                        "}", courier.getLogin(), courier.getPassword()
        );
    }

    @Test
    @DisplayName("Check successful login returns courier id")
    public void shouldReturnIdWhenLoginSuccess() {
        response = courierClient.loginCourier(courierIdJson);
        response.statusCode(200);
        response.assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Check login under non existent credentials")
    public void shouldReturn404NotFoundWhenLoginNonexistentCourier() {
        String nonexistentCourier = "{\n" +
                "    \"login\": \"noSuchLogin2222\",\n" +
                "    \"password\": \"noSuchPassword4444\"\n" +
                "}";
        courierClient.loginCourier(nonexistentCourier)
                .statusCode(404).assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Check response status code and message when courier logs in without password")
    public void shouldReturn400BadRequestWhenLoginWithoutPassword() {
        String noPasswordLogin = "{\"login\": \"testLogin9512\"}";
        courierClient.loginCourier(noPasswordLogin)
                .statusCode(400).assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Check response status code and message when courier logs in without login")
    public void shouldReturn400BadRequestWhenLoginWithoutLogin() {
        String noLogin = "{\"password\": \"testPassword7058\"}";
        courierClient.loginCourier(noLogin)
                .statusCode(400).assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));

    }

    @After
    public void tearDown() {
        if (response != null) {
            ApiResponse apiResponse = response.extract().body().as(ApiResponse.class);
            courierClient.deleteCourier(courierIdJson, apiResponse.getId());
        }
    }
}
