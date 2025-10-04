package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ReceivingUserOrderSteps {

    @Step("Создание заказа c авторизацией")
    public Response receivingUserOrderAuthorization( String token) {

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .log().all() // логируем запрос
                .when()
                .get("/api/orders")
                .then()
                .log().all() // логируем ответ
                .extract()
                .response();
    }

    @Step("Создание заказа c авторизацией")
    public Response receivingUserOrderNotAuthorization( ) {

        return given()
                .contentType(ContentType.JSON)
                .log().all() // логируем запрос
                .when()
                .get("/api/orders")
                .then()
                .log().all() // логируем ответ
                .extract()
                .response();
    }




}
