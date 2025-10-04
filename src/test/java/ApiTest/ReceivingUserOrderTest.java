package ApiTest;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import steps.CreatingOrderSteps;
import steps.CreatingUserSteps;
import steps.ReceivingUserOrderSteps;
import steps.ValidationResponseSteps;
import testData.OrderTestData;

import java.util.List;

public class ReceivingUserOrderTest {
    CreatingOrderSteps creatingOrderSteps = new CreatingOrderSteps();
    ValidationResponseSteps validationResponseSteps = new ValidationResponseSteps();
    ReceivingUserOrderSteps receivingUserOrderSteps = new ReceivingUserOrderSteps();
    CreatingUserSteps creatingUserSteps = new CreatingUserSteps();


    @BeforeAll
    public static void setUpOnce() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        RestAssured.config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 10000)
                        .setParam("http.socket.timeout", 10000)
                );
    }

    @Test
    public void receivingUserOrderAuthorization() {
        List<OrderTestData.Ingredient> ingredients = List.of(
                OrderTestData.Ingredient.CRATOR_BUN,
                OrderTestData.Ingredient.SPICY_SAUCE,
                OrderTestData.Ingredient.BEEF,
                OrderTestData.Ingredient.FLUORESCENT_BUN
        );

        Response userResponse = creatingUserSteps.createUserSuccessful();
        validationResponseSteps.verifyUserCreatedSuccessfully(userResponse);
        String token = userResponse.jsonPath().getString("accessToken");

        String body = OrderTestData.createOrderWithIngredients(ingredients);
        Response orderResponse = creatingOrderSteps.creatingOrderAuthorization(body, token);
        validationResponseSteps.validateSuccessResponse(orderResponse);


        String orderId = orderResponse.jsonPath().getString("order._id");


        Response receivingResponse = receivingUserOrderSteps.receivingUserOrderAuthorization(token);
        validationResponseSteps.validateReceivingOrder(receivingResponse, orderId);

    }

    @Test
    public void receivingUserOrderNotAuthorization() {
        List<OrderTestData.Ingredient> ingredients = List.of(
                OrderTestData.Ingredient.CRATOR_BUN,
                OrderTestData.Ingredient.SPICY_SAUCE,
                OrderTestData.Ingredient.BEEF,
                OrderTestData.Ingredient.FLUORESCENT_BUN
        );

        Response userResponse = creatingUserSteps.createUserSuccessful();
        validationResponseSteps.verifyUserCreatedSuccessfully(userResponse);
        String token = userResponse.jsonPath().getString("accessToken");

        String body = OrderTestData.createOrderWithIngredients(ingredients);
        Response orderResponse = creatingOrderSteps.creatingOrderAuthorization(body, token);
        validationResponseSteps.validateSuccessResponse(orderResponse);


        Response receivingResponse = receivingUserOrderSteps.receivingUserOrderNotAuthorization();
        validationResponseSteps.validateReceivingOrderNotAuthorization(receivingResponse);

    }


}
