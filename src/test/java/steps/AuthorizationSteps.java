package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import testData.UserTestData;

import static io.restassured.RestAssured.given;

public class AuthorizationSteps {


    @Step("Авторизация пользователя")
    public Response authorizationUserSuccessful(String token) {

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .log().all() // логируем запрос
                .when()
                .get("/api/auth/user")
                .then()
                .log().all() // логируем ответ
                .extract()
                .response();
    }



}
