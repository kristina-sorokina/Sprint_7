import api.ApiResponse;
import api.Courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    private Courier courier;
    private String courierIdJson;
    private Response response;

    @Before
    public void setUp() {
        baseURI = "http://qa-scooter.praktikum-services.ru/";
        courier = new Courier("testLogin609", "testPassword482", "testName371");

        given()
                .header("Content-type", "application/json")
                .and().body(courier)
                .post("api/v1/courier");

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
        response = given()
                .header("Content-type", "application/json")
                .and().body(courierIdJson)
                .post("api/v1/courier/login");

        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Check login under non existent credentials")
    public void shouldReturn404NotFoundWhenLoginNonexistentCourier() {
        String nonexistentCourier = "{\n" +
                "    \"login\": \"noSuchLogin2222\",\n" +
                "    \"password\": \"noSuchPassword4444\"\n" +
                "}";

        given()
                .header("Content-type", "application/json")
                .and().body(nonexistentCourier)
                .when().post("api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and().statusCode(404);
    }

    @Test
    @DisplayName("Check response status code and message when courier logs in without password")
    public void shouldReturn400BadRequestWhenLoginWithoutPassword() {
        String noPasswordLogin = "{\"login\": \"testLogin9512\"}";

        given()
                .header("Content-type", "application/json")
                .and().body(noPasswordLogin)
                .when().post("api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and().statusCode(400);
    }

    @Test
    @DisplayName("Check response status code and message when courier logs in without login")
    public void shouldReturn400BadRequestWhenLoginWithoutLogin() {
        String noLogin = "{\"password\": \"testPassword7058\"}";

        given()
                .header("Content-type", "application/json")
                .and().body(noLogin)
                .when().post("api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and().statusCode(400);
    }

    @After
    public void tearDown() {
        if (response != null) {
            ApiResponse apiResponse = response.body().as(ApiResponse.class);

            given()
                    .header("Content-type", "application/json")
                    .and().body(courierIdJson)
                    .delete(String.format("api/v1/courier/%s", apiResponse.getId()));
        }
    }
}
