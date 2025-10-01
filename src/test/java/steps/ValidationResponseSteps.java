package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import testData.UserTestData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ValidationResponseSteps {


    @Step("Проверить успешное создание пользователя")
    public void verifyUserCreatedSuccessfully(Response response) {
        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }


    @Step("Проверить создание дубля пользователя")
    public void verifyThatCreatingDuplicateUserFails(Response response) {
        response.then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Step("Проверить создание пользователя с отсутствием полей")
    public void verifyUserCreationWithoutRequiredFieldsFails(Response response) {
        response.then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Проверить создался ли пользователь")
    public void verifyCreateUser(Response response, int expectedStatusCode, Boolean expectation) {
        if (expectation == true) {
            response.then()
                    .statusCode(expectedStatusCode)
                    .body("success",equalTo(true));
        } else {
            response.then()
                    .statusCode(not(200));
        }

    }

    @Step("Проверить авторизацию пользователя")
    public void UserAuthorizationSuccessful(Response response) {
        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

}
