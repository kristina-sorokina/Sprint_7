import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static util.OrderBuilder.*;

public class CreateOrderTest {

    @Before
    public void setUp() {
        baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check that response body contains Track when order created successfully")
    public void shouldReturnTrackWhenOrderCreatedSuccessfully() {
        given()
                .header("Content-type", "application/json")
                .and().body(getOrderNoColor())
                .when().post("api/v1/orders")
                .then().assertThat().body("track", notNullValue());
    }

    @Test
    @DisplayName("Check that order created when no color chosen")
    public void shouldCreateOrderWhenNoColorChosen() {
        given()
                .header("Content-type", "application/json")
                .and().body(getOrderNoColor())
                .post("api/v1/orders")
                .then().statusCode(201);
    }

    @Test
    @DisplayName("Check that order created when BLACK color chosen")
    public void shouldCreateOrderWhenBlackColorChosen() {
        given()
                .header("Content-type", "application/json")
                .and().body(getOrderBlackColor())
                .post("api/v1/orders")
                .then().statusCode(201);
    }

    @Test
    @DisplayName("Check that order created when GREY color chosen")
    public void shouldCreateOrderWhenGreyColorChosen() {
        given()
                .header("Content-type", "application/json")
                .and().body(getOrderGreyColor())
                .post("api/v1/orders")
                .then().statusCode(201);
    }

    @Test
    @DisplayName("Check that order created when BLACK and GREY color chosen")
    public void shouldCreateOrderWhenBlackAndGreyColorChosen() {
        given()
                .header("Content-type", "application/json")
                .and().body(getOrderBlackAndGreyColor())
                .post("api/v1/orders")
                .then().statusCode(201);
    }
}
