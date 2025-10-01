package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import testData.UserTestData;

import static io.restassured.RestAssured.given;

public class UpdatingUserSteps {

    @Step("Создание пользователя")
    public Response createUserSuccessful(String body) {
        if (body == null) {
            body = UserTestData.generateUniqueBodyCreatedUser();
        }
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all() // логируем запрос
                .when()
                .patch("/api/auth/user")
                .then()
                .log().all() // логируем ответ
                .extract()
                .response();
    }
}
