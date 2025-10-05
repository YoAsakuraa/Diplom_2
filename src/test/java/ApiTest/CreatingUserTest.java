package ApiTest;

import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import steps.CreatingUserSteps;
import steps.DeleteSteps;
import steps.ValidationResponseSteps;
import testData.UserTestData;

import java.util.stream.Stream;

public class CreatingUserTest {

    CreatingUserSteps creatingUserSteps = new CreatingUserSteps();
    ValidationResponseSteps validationResponseSteps = new ValidationResponseSteps();
    DeleteSteps deleteSteps = new DeleteSteps();
    String token;

    static {
        RestAssured.filters(new AllureRestAssured());
    }

    @BeforeAll
    public static void setUp() {

        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        RestAssured.config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 10000)
                        .setParam("http.socket.timeout", 10000)
                );
    }

    @AfterEach
    public void DeleteUser() {
        deleteSteps.deleteUser(token);
    }

    /**
     * ПРИМЕЧАНИЕ: В документации API отсутствует описание формата успешного ответа.
     * Проверяем статус 201 (Created) и наличие флага "success": true,
     * исходя из:
     * 1. Стандартного поведения REST API при создании сущности
     * 2. Ожиданий бизнес-логики приложения
     * 3. Анализа фактических ответов сервера в различных сценариях
     */
    @Test
    @Description("Успешное создание пользователя")
    public void createdUser() {
        Response response = creatingUserSteps.createUserSuccessful();
        token = response.jsonPath().getString("accessToken");
        validationResponseSteps.verifyUserCreatedSuccessfully(response);


    }

    @Test
    @Description("Создание уже зарегистрированного пользователя")
    public void createdDuplicationUser() {
        Response response = creatingUserSteps.createDuplicateUser();
        token = response.jsonPath().getString("accessToken");
        validationResponseSteps.verifyThatCreatingDuplicateUserFails(response);

    }

    @ParameterizedTest
    @MethodSource("userDataProvider")
    @Description("Создание пользователя с различными данными")
    public void createOrderWithDifferentData(String email, String password, String name, Integer statusCode, Boolean expectation) {
        String body = String.format("{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\"}",
                email, password, name);

        Response response = creatingUserSteps.createUserSuccessful(body);
        token = response.jsonPath().getString("accessToken");
        validationResponseSteps.verifyCreateUser(response, statusCode, expectation);

    }

    @Test
    @Description("Создание пользователя без Email")
    public void createdUserWithoutEmail() {
        String body = UserTestData.CreatedUserData.generateBodyWithoutEmailCreatedUser();
        Response response = creatingUserSteps.createUserSuccessful(body);
        token = response.jsonPath().getString("accessToken");
        validationResponseSteps.verifyUserCreationWithoutRequiredFieldsFails(response);


    }

    @Test
    @Description("Создание пользователя без Password")
    public void createdUserWithoutPassword() {
        String body = UserTestData.CreatedUserData.generateBodyWithoutPasswordCreatedUser();
        Response response = creatingUserSteps.createUserSuccessful(body);
        token = response.jsonPath().getString("accessToken");
        validationResponseSteps.verifyUserCreationWithoutRequiredFieldsFails(response);

    }

    @Test
    @Description("Создание пользователя без Name")
    public void createdUserWithoutName() {
        String body = UserTestData.CreatedUserData.generateBodyWithoutNameCreatedUser();
        Response response = creatingUserSteps.createUserSuccessful(body);
        token = response.jsonPath().getString("accessToken");
        validationResponseSteps.verifyUserCreationWithoutRequiredFieldsFails(response);


    }


    static Stream<Arguments> userDataProvider() {
        return Stream.of(
                // Комбинация 1: Все поля
                Arguments.of(UserTestData.Utils.generateUniqueEmail(), UserTestData.Utils.generateUniquePassword(), UserTestData.Utils.generateUniqueName(), 200, true),

                // Комбинация 2: Значение Email null
                Arguments.of(UserTestData.Utils.getNull(), UserTestData.Utils.generateUniquePassword(), UserTestData.Utils.generateUniqueName(), 200, false),

                // Комбинация 3: Значение password null
                Arguments.of(UserTestData.Utils.generateUniqueEmail(), UserTestData.Utils.getNull(), UserTestData.Utils.generateUniqueName(), 200, false),

                // Комбинация 4: Значение name null
                Arguments.of(UserTestData.Utils.generateUniqueEmail(), UserTestData.Utils.generateUniquePassword(), UserTestData.Utils.getNull(), 200, false),

                // Комбинация 5: Только email  (значение password и name null)
                Arguments.of(UserTestData.Utils.generateUniqueEmail(), UserTestData.Utils.getNull(), UserTestData.Utils.getNull(), 200, false),

                // Комбинация 6: Только password  (значение email и name null)
                Arguments.of(UserTestData.Utils.getNull(), UserTestData.Utils.generateUniquePassword(), UserTestData.Utils.getNull(), 200, false),
                // Комбинация 6: Только name     (значение email и name password)
                Arguments.of(UserTestData.Utils.getNull(), UserTestData.Utils.getNull(), UserTestData.Utils.generateUniqueName(), 200, false)
        );
    }

}
