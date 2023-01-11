import api.client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {
    private OrderClient orderClient;
    @Before
    public void setUp() {
        baseURI = "http://qa-scooter.praktikum-services.ru/";
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Check that list of orders returns when successful request")
    public void shouldReturnOrderList() {
        ValidatableResponse response = orderClient.getOrderList();
        response.statusCode(200);
        response.assertThat().body(notNullValue());

    }
}
