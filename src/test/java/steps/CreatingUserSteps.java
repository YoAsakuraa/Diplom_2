package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import testData.UserTestData;

import static io.restassured.RestAssured.given;

public class CreatingUserSteps {


    @Step("Создание пользователя")
    public Response createUserSuccessful(String body) {
        if (body == null) {
            body = UserTestData.generateUniqueBody();
        }
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all() // логируем запрос
                .when()
                .post("/api/auth/register")
                .then()
                .log().all() // логируем ответ
                .extract()
                .response();
    }

    @Step("Создание курьера со случайными данными")
    public Response createUserSuccessful() {
        return createUserSuccessful(null);
    }



    @Step("Создание уже зарегистрированного пользователя")
    public Response createDuplicateUser() {
        String body = UserTestData.generateUniqueBody();
         given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all() // логируем запрос
                .when()
                .post("/api/auth/register")
                .then()
                .log().all() // логируем ответ
                .statusCode(200);


       return   given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all() // логируем запрос
                .when()
                .post("/api/auth/register")
                .then()
                .log().all() // логируем ответ
                .extract()
                .response();
    }
}
