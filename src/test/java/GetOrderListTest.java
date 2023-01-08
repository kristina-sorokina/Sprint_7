import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {
    @Before
    public void setUp() {
        baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check that list of orders returns when successful request")
    public void shouldReturnOrderList() {
        given()
                .get("api/v1/orders")
                .then().assertThat().body(notNullValue())
                .and().statusCode(200);
    }
}
