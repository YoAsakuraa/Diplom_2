package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

public class ValidationResponseSteps {


    @Step("Проверить успешное создание пользователя")
    public void verifyUserCreatedSuccessfully(Response response) {
        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
        System.out.println("Пользователь успешно создан");
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
                    .body("success", equalTo(true));
        } else {
            response.then()
                    .statusCode(not(200));
        }

    }

    @Step("Валидация успешного ответа")
    public void validateSuccessResponse(Response response) {
        response.then()
                .statusCode(200)
                .body("success", equalTo(true));

    }

    @Step("Валидация ошибочного при авторизации ответа")
    public void validateErrorResponseAuthorization(Response response) {
        response.then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }


    @Step("Проверка что email изменился")
    public void validateEmailChanged(Response response, String oldEmail) {
        response.then().statusCode(200).body("user.email", not(equalTo(oldEmail)));
        System.out.println("📧 Email изменен. Старый: " + oldEmail);
    }

    @Step("Проверка что имя изменилось")
    public void validateNameChanged(Response response, String oldName) {
        response.then().body("user.name", not(equalTo(oldName)));
        System.out.println("👤 Имя изменено. Старое: " + oldName);
    }

    @Step("Проверка что email и имя изменились")
    public void validateEmailAndNameChanged(Response response, String oldEmail, String oldName) {
        response.then().body("user.email", not(equalTo(oldEmail)))
                .body("user.name", not(equalTo(oldName)));
        System.out.println("📧 Email изменен. Старый: " + oldEmail);
        System.out.println("👤 Имя изменено. Старое: " + oldName);
    }

    @Step("Проверка изменение данных без авторизации")
    public void validateDataNotAuthorization(Response response) {
        response.then().statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
        System.out.println("Ошибка обновления пользователя , отсутствует авторизация ");
    }

    @Step("Проверка создания заказа ")
    public void validateCreateOrderError(Response response) {
        response.then().statusCode(not(200))
                .body("id", equalTo(false));

    }
    @Step("Проверка получения заказа ")
    public void validateReceivingOrder(Response response ,String idValue) {
        response.then().statusCode(200)
                .body("orders[0]._id", equalTo(idValue));

        System.out.println("Список заказов получен ");
    }

    @Step("Проверка получения списка заказов не авторизированным пользователем ")
    public void validateReceivingOrderNotAuthorization(Response response ) {
        response.then().statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));

    }
}
