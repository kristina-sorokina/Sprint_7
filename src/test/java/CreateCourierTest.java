import api.ApiResponse;
import api.Courier;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {
    private Courier courier;

    @Before
    public void setUp() {
        baseURI = "http://qa-scooter.praktikum-services.ru/";
        courier = new Courier("testLogin731", "testPassword094", "testName528");
    }

    @Test
    @DisplayName("Check Status code when courier created successfully")
    public void shouldReturn201WhenSuccess() {
        given()
                .header("Content-type", "application/json")
                .and().body(courier)
                .when().post("api/v1/courier")
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Check Response body when courier created successfully")
    public void shouldReturnCorrectResponseBodyWhenSuccess() {
        given()
                .header("Content-type", "application/json")
                .and().body(courier)
                .when().post("api/v1/courier")
                .then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Check response status code and message when courier created without password")
    public void shouldReturn400BadRequestWhenNoPassword() {
        String noPasswordCourier = "{\n" +
                "    \"login\": \"testLogin45222\",\n" +
                "    \"firstName\": \"testName7530\"\n" +
                "}";

        given()
                .header("Content-type", "application/json")
                .and().body(noPasswordCourier)
                .when().post("api/v1/courier")
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
    }

    @Test
    @DisplayName("Check response status code and message when courier created without login")
    public void shouldReturn400BadRequestWhenNoLogin() {
        String noLoginCourier = "{\n" +
                "    \"password\": \"testPassword0495\",\n" +
                "    \"firstName\": \"testName7530\"\n" +
                "}";

        given()
                .header("Content-type", "application/json")
                .and().body(noLoginCourier)
                .when().post("api/v1/courier")
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(400);
    }

    @Test
    @DisplayName("Check response status code and message when same courier created")
    public void shouldReturn409ConflictWhenSameCourierCreated() {
        given()
                .header("Content-type", "application/json")
                .and().body(courier)
                .post("api/v1/courier");

        given()
                .header("Content-type", "application/json")
                .and().body(courier)
                .when().post("api/v1/courier")
                .then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and().statusCode(400);

    }

    @After
    public void tearDown() {
        String courierIdJson = String.format(
                "{\n" +
                        "    \"login\": \"%s\",\n" +
                        "    \"password\": \"%s\"\n" +
                        "}", courier.getLogin(), courier.getPassword()
        );

        ApiResponse response = given()
                .header("Content-type", "application/json")
                .body(courierIdJson)
                .post("api/v1/courier/login")
                .body().as(ApiResponse.class);


        given()
                .header("Content-type", "application/json")
                .and().body(courierIdJson)
                .delete(String.format("api/v1/courier/%s", response.getId()));
    }
}
