package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginSteps {

    @Step("Логин пользователем")
    public Response UserLogoutSuccessful(String body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all() // логируем запрос
                .when()
                .post("/api/auth/login")
                .then()
                .log().all() // логируем ответ
                .extract()
                .response();

    }
}
