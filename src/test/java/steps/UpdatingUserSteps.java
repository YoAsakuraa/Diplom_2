package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UpdatingUserSteps {




    @Step("Обновление данных пользователя c авторизацией")
    public Response updateDateUserAuthorization(String token, String body) {

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(body)
                .log().all() // логируем запрос
                .when()
                .patch("/api/auth/user")
                .then()
                .log().all() // логируем ответ
                .extract()
                .response();
    }

    @Step("Обновление данных пользователя")
    public Response updateDateUser( String body) {

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
