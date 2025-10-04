package ApiTest;

import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import steps.CreatingUserSteps;
import steps.LoginSteps;
import steps.ValidationResponseSteps;
import testData.UserTestData;

public class LoginTest {
    CreatingUserSteps creatingUserSteps = new CreatingUserSteps();
    LoginSteps loginSteps = new LoginSteps();
    ValidationResponseSteps validationResponseSteps = new ValidationResponseSteps();

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

    @Test
    @Description("Успешный логин пользователем")
    public void loginUser() {
        String email = UserTestData.Utils.generateUniqueEmail();
        String password = UserTestData.Utils.generateUniquePassword();
        String name = UserTestData.Utils.generateUniqueName();

        String bodyCreated = UserTestData.Utils.generateCustomBody(email, password, name);
        String bodyAuthorization = UserTestData.Utils.generateCustomBody(email, password, name);

        Response response = creatingUserSteps.createUserSuccessful(bodyCreated);
        validationResponseSteps.verifyUserCreatedSuccessfully(response);

        Response responseAuthorization = loginSteps.UserLogoutSuccessful(bodyAuthorization);
        validationResponseSteps.validateSuccessResponse(responseAuthorization);
    }
}
