package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ReceivingUserOrderSteps {

    @Step("Создание заказа c авторизацией")
    public Response creatingOrderAuthorization(String body , String token) {

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(body)
                .log().all() // логируем запрос
                .when()
                .post("/api/orders")
                .then()
                .log().all() // логируем ответ
                .extract()
                .response();
    }




}
