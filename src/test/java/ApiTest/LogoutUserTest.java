package ApiTest;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import steps.CreatingUserSteps;
import steps.LogoutUserSteps;
import steps.ValidationResponseSteps;
import testData.UserTestData;

public class LogoutUserTest {
    CreatingUserSteps creatingUserSteps = new CreatingUserSteps();
    LogoutUserSteps logoutUserSteps = new LogoutUserSteps();
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

    @Test
    @Description("Успешное авторизация пользователем")
    public void createdUser() {
       String email = UserTestData.generateUniqueEmail();
       String password = UserTestData.generateUniquePassword();
       String name = UserTestData.generateUniqueName();

        String bodyCreated = String.format("{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\"}",
                email, password, name);
        String bodyAutorization = String.format("{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\"}",
                email, password, name);

       Response response = creatingUserSteps.createUserSuccessful(bodyCreated);
       validationResponseSteps.verifyUserCreatedSuccessfully(response);

       Response responseAuthorization = logoutUserSteps.UserAuthorizationSuccessful(bodyAutorization);
       validationResponseSteps.UserAuthorizationSuccessful(responseAuthorization);

    }
}
