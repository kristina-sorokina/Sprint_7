import api.client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.CoreMatchers.notNullValue;
import static util.OrderBuilder.*;

public class CreateOrderTest {
    private OrderClient orderClient;
    private ValidatableResponse response;

    @Before
    public void setUp() {
        baseURI = "http://qa-scooter.praktikum-services.ru/";
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Check that response body contains Track when order created successfully")
    public void shouldReturnTrackWhenOrderCreatedSuccessfully() {
        response = orderClient.createOrder(getOrderNoColor());
        response.assertThat().body("track", notNullValue());
    }

    @Test
    @DisplayName("Check that order created when no color chosen")
    public void shouldCreateOrderWhenNoColorChosen() {
        response = orderClient.createOrder(getOrderNoColor());
        response.statusCode(201);
    }

    @Test
    @DisplayName("Check that order created when BLACK color chosen")
    public void shouldCreateOrderWhenBlackColorChosen() {
        response = orderClient.createOrder(getOrderBlackColor());
        response.statusCode(201);
    }

    @Test
    @DisplayName("Check that order created when GREY color chosen")
    public void shouldCreateOrderWhenGreyColorChosen() {
        response = orderClient.createOrder(getOrderGreyColor());
        response.statusCode(201);
    }

    @Test
    @DisplayName("Check that order created when BLACK and GREY color chosen")
    public void shouldCreateOrderWhenBlackAndGreyColorChosen() {
        response = orderClient.createOrder(getOrderBlackAndGreyColor());
        response.statusCode(201);
    }
}
