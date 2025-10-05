package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteSteps {

    @Step("Удаление пользователя")
    public void deleteUser(String token) {
        try {
            given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", token)
                    .when()
                    .delete("https://stellarburgers.nomoreparties.site/api/auth/user")
                    .then()
                    .log().all()
                    .statusCode(202)
                    .body("success", equalTo(true))
                    .body("message", equalTo("User successfully removed"));

            System.out.println("Пользователь успешно удален");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }
}