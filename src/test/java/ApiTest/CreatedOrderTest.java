package ApiTest;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import steps.CreatingOrderSteps;
import steps.CreatingUserSteps;
import steps.ValidationResponseSteps;
import testData.OrderTestData;

import java.util.List;
import java.util.stream.Stream;

public class CreatedOrderTest {
    CreatingOrderSteps creatingOrderSteps = new CreatingOrderSteps();
    CreatingUserSteps creatingUserSteps = new CreatingUserSteps();
    ValidationResponseSteps validationResponseSteps = new ValidationResponseSteps();



    @BeforeEach
    public void setUp() {

        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        RestAssured.config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 10000)
                        .setParam("http.socket.timeout", 10000)
                );



    }


    @ParameterizedTest
    @MethodSource("orderDataProviderPositive")
    public void createOrderPositiveCombinationsAuthorization(List<OrderTestData.Ingredient> ingredients) {
        Response response = creatingUserSteps.createUserSuccessful();
        validationResponseSteps.verifyUserCreatedSuccessfully(response);

        String token = response.jsonPath().getString("accessToken");

        // Конвертируем List в массив для метода
        OrderTestData.Ingredient[] ingredientsArray = ingredients.toArray(new OrderTestData.Ingredient[0]);
        String body = OrderTestData.createOrderWithIngredients(List.of(ingredientsArray));

        Response orderResponse = creatingOrderSteps.creatingOrderAuthorization(body, token);
        validationResponseSteps.validateSuccessResponse(orderResponse);
    }

    @ParameterizedTest
    @MethodSource("orderDataProviderPositive")
    public void createOrderPositiveCombinationsNotAuthorization(List<OrderTestData.Ingredient> ingredients) {
        Response response = creatingUserSteps.createUserSuccessful();
        validationResponseSteps.verifyUserCreatedSuccessfully(response);

        // Конвертируем List в массив для метода
        OrderTestData.Ingredient[] ingredientsArray = ingredients.toArray(new OrderTestData.Ingredient[0]);
        String body = OrderTestData.createOrderWithIngredients(List.of(ingredientsArray));

        Response orderResponse = creatingOrderSteps.creatingOrderNotAuthorization(body);
        validationResponseSteps.validateCreateOrderError(orderResponse);
    }

    @ParameterizedTest
    @MethodSource("orderDataProviderNegative")
    public void createOrderNegativeCombinations(List<OrderTestData.Ingredient> ingredients) {
        Response response = creatingUserSteps.createUserSuccessful();
        validationResponseSteps.verifyUserCreatedSuccessfully(response);

        String token = response.jsonPath().getString("accessToken");

        // Конвертируем List в массив для метода
        OrderTestData.Ingredient[] ingredientsArray = ingredients.toArray(new OrderTestData.Ingredient[0]);
        String body = OrderTestData.createOrderWithIngredients(List.of(ingredientsArray));

        Response orderResponse = creatingOrderSteps.creatingOrderAuthorization(body, token);
        validationResponseSteps.validateCreateOrderError(orderResponse);
    }


    static Stream<Arguments> orderDataProviderPositive() {
        return Stream.of(

                // Комбинация 1: 2 булки + соус
                Arguments.of(List.of(
                        OrderTestData.Ingredient.CRATOR_BUN,
                        OrderTestData.Ingredient.SPICY_SAUCE,
                        OrderTestData.Ingredient.FLUORESCENT_BUN
                )),
                // Комбинация 1: 2 булки + соус + начинка
                Arguments.of(List.of(
                        OrderTestData.Ingredient.CRATOR_BUN,
                        OrderTestData.Ingredient.SPICY_SAUCE,
                        OrderTestData.Ingredient.BEEF,
                        OrderTestData.Ingredient.FLUORESCENT_BUN
                )),
                // Комбинация 1: 2 булки +2 соуса + 2 начинки
                Arguments.of(List.of(
                        OrderTestData.Ingredient.CRATOR_BUN,
                        OrderTestData.Ingredient.SPICY_SAUCE,
                        OrderTestData.Ingredient.CREAM_SAUCE,
                        OrderTestData.Ingredient.BEEF,
                        OrderTestData.Ingredient.CHICKEN,
                        OrderTestData.Ingredient.FLUORESCENT_BUN
                )),
                // Комбинация 1: 2 булки +4 соуса + 8 начинок
                Arguments.of(List.of(
                        OrderTestData.Ingredient.CRATOR_BUN,
                        OrderTestData.Ingredient.SPICY_SAUCE,
                        OrderTestData.Ingredient.CREAM_SAUCE,
                        OrderTestData.Ingredient.BEEF,
                        OrderTestData.Ingredient.FILLET,
                        OrderTestData.Ingredient.CHICKEN,
                        OrderTestData.Ingredient.CHEESE,
                        OrderTestData.Ingredient.SALAD,
                        OrderTestData.Ingredient.EGG,
                        OrderTestData.Ingredient.TOMATO,
                        OrderTestData.Ingredient.CUCUMBER,
                        OrderTestData.Ingredient.POTATO,
                        OrderTestData.Ingredient.FLUORESCENT_BUN
                ))


        );
    }

    static Stream<Arguments> orderDataProviderNegative() {
        return Stream.of(

                // Комбинация 1: булка
                Arguments.of(List.of(OrderTestData.Ingredient.CRATOR_BUN)),

                // Комбинация 2: соус
                Arguments.of(List.of(
                        OrderTestData.Ingredient.CREAM_SAUCE

                )),
                // Комбинация 3: начинка
                Arguments.of(List.of(
                        OrderTestData.Ingredient.CHICKEN

                )),
                // Комбинация 4: пустой список
                Arguments.of(List.of())


        );
    }

}



