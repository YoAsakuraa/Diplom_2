package ApiTest;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import steps.*;
import testData.UserTestData;


public class UpdateDateUserTest {
    CreatingUserSteps creatingUserSteps = new CreatingUserSteps();
    ValidationResponseSteps validationResponseSteps = new ValidationResponseSteps();
    UpdatingUserSteps updatingUserSteps = new UpdatingUserSteps();
    LoginSteps logoutUserSteps = new LoginSteps();
    DeleteSteps deleteSteps = new DeleteSteps();
    String token;

    @BeforeAll
    public static void setUp() {

        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        RestAssured.config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 10000)
                        .setParam("http.socket.timeout", 10000)
                );
    }

    @AfterEach
    public void DeleteUser() {
        deleteSteps.deleteUser(token);
    }


    @Test
    @Description("Полное обновление данных пользователя и проверка доступности")
    public void UpdatingEmailAndNameUserSuccessfully() {
        //Создаем пользователя с паролем
        String createBody = UserTestData.CreatedUserData.generateUserWithCustomPassword("Test15");
        Response response = creatingUserSteps.createUserSuccessful(createBody);
        validationResponseSteps.verifyUserCreatedSuccessfully(response);

        //Сохраняем токен и тело после создания
        token = response.jsonPath().getString("accessToken");
        String oldEmail = response.jsonPath().getString("user.email");
        String oldName = response.jsonPath().getString("user.name");
        String oldPassword = "Test15";
        String newPassword = UserTestData.Utils.generateUniquePassword();

        //Меняем данные (используем токен для авторизации)
        String updateBody = UserTestData.UpdateData.generateFullUpdateCustomPassword(newPassword);
        Response updateResponse = updatingUserSteps.updateDateUserAuthorization(token, updateBody);
        validationResponseSteps.validateEmailAndNameChanged(updateResponse, oldEmail, oldName);

        String newEmail = updateResponse.jsonPath().getString("user.email");
        String newName = updateResponse.jsonPath().getString("user.name");

        //Проверяем что пароль действительно изменился логинимся под старым паролем
        String bodyAuthorizationOldPassword = UserTestData.Utils.generateCustomBody(newEmail, oldPassword, newName);

        Response responseLoginOldPassword = logoutUserSteps.UserLogoutSuccessful(bodyAuthorizationOldPassword);
        validationResponseSteps.validateErrorResponseAuthorization(responseLoginOldPassword);

        //Проверяем что пароль действительно изменился логинимся под новым паролем
        String bodyAuthorizationNewPassword = UserTestData.Utils.generateCustomBody(newEmail, newPassword, newName);
        Response responseLoginNewPassword = logoutUserSteps.UserLogoutSuccessful(bodyAuthorizationNewPassword);
        validationResponseSteps.validateSuccessResponse(responseLoginNewPassword);


    }


    @Test
    @Description("Обновление email  пользователя")
    public void UpdatingEmailUserSuccessfully() {
        //Создаем пользователя
        Response response = creatingUserSteps.createUserSuccessful();
        validationResponseSteps.verifyUserCreatedSuccessfully(response);
        //Сохраняем токен после создания
        token = response.jsonPath().getString("accessToken");
        String oldEmail = response.jsonPath().getString("user.email");

        //Меняем данные (используем токен для авторизации)
        String body = UserTestData.UpdateData.generateEmailUpdate();
        Response updateResponse = updatingUserSteps.updateDateUserAuthorization(token, body);
        validationResponseSteps.validateEmailChanged(updateResponse, oldEmail);

    }

    @Test
    @Description("Обновление name пользователя")
    public void UpdatingNameUserSuccessfully() {
        //Создаем пользователя
        Response response = creatingUserSteps.createUserSuccessful();
        validationResponseSteps.verifyUserCreatedSuccessfully(response);
        //Сохраняем токен после создания
        token = response.jsonPath().getString("accessToken");
        String oldName = response.jsonPath().getString("user.name");


        //Меняем данные (используем токен для авторизации)
        String body = UserTestData.UpdateData.generateNameUpdate();
        Response updateResponse = updatingUserSteps.updateDateUserAuthorization(token, body);
        validationResponseSteps.validateNameChanged(updateResponse, oldName);

    }

    @Test
    @Description("Обновление password пользователя")
    public void UpdatingPasswordUserSuccessfully() {
        String oldPassword = "Test15";
        //Создаем пользователя с паролем
        String createBody = UserTestData.CreatedUserData.generateUserWithCustomPassword(oldPassword);
        Response response = creatingUserSteps.createUserSuccessful(createBody);
        validationResponseSteps.verifyUserCreatedSuccessfully(response);

        //Сохраняем токен и тело после создания
        token = response.jsonPath().getString("accessToken");
        String oldEmail = response.jsonPath().getString("user.email");
        String oldName = response.jsonPath().getString("user.name");
        String newPassword = UserTestData.Utils.generateUniquePassword();

        //Меняем данные (используем токен для авторизации)
        String updateBody = UserTestData.UpdateData.PasswordUpdate(newPassword);
        Response updateResponse = updatingUserSteps.updateDateUserAuthorization(token, updateBody);
        validationResponseSteps.validateSuccessResponse(updateResponse);


        //Проверяем что пароль действительно изменился логинимся под старым паролем
        String bodyAuthorizationOldPassword = UserTestData.Utils.generateCustomBody(oldEmail, oldPassword, oldName);

        Response responseLoginOldPassword = logoutUserSteps.UserLogoutSuccessful(bodyAuthorizationOldPassword);
        validationResponseSteps.validateErrorResponseAuthorization(responseLoginOldPassword);

        //Проверяем что пароль действительно изменился логинимся под новым паролем
        String bodyAuthorizationNewPassword = UserTestData.Utils.generateCustomBody(oldEmail, newPassword, oldName);
        Response responseLoginNewPassword = logoutUserSteps.UserLogoutSuccessful(bodyAuthorizationNewPassword);
        validationResponseSteps.validateSuccessResponse(responseLoginNewPassword);


    }


    @Test
    @Description("Обновление name пользователя без авторизации")
    public void UpdatingNameUserNotAuthorization() {
        //Создаем пользователя
        Response response = creatingUserSteps.createUserSuccessful();
        validationResponseSteps.verifyUserCreatedSuccessfully(response);

        //Меняем данные
        String body = UserTestData.UpdateData.generateNameUpdate();
        Response updateResponse = updatingUserSteps.updateDateUser(body);
        validationResponseSteps.validateDataNotAuthorization(updateResponse);

        token = response.jsonPath().getString("accessToken");

    }

    @Test
    @Description("Обновление email пользователя без авторизации")
    public void UpdatingEmailUserNotAuthorization() {
        //Создаем пользователя
        Response response = creatingUserSteps.createUserSuccessful();
        validationResponseSteps.verifyUserCreatedSuccessfully(response);

        //Меняем данные
        String body = UserTestData.UpdateData.generateEmailUpdate();
        Response updateResponse = updatingUserSteps.updateDateUser(body);
        validationResponseSteps.validateDataNotAuthorization(updateResponse);

        token = response.jsonPath().getString("accessToken");

    }

    @Test
    @Description("Обновление password пользователя без авторизации")
    public void UpdatingPasswordUserNotAuthorization() {
        //Создаем пользователя
        Response response = creatingUserSteps.createUserSuccessful();
        validationResponseSteps.verifyUserCreatedSuccessfully(response);

        //Меняем данные
        String body = UserTestData.UpdateData.generatePasswordUpdate();
        Response updateResponse = updatingUserSteps.updateDateUser(body);
        validationResponseSteps.validateDataNotAuthorization(updateResponse);

        token = response.jsonPath().getString("accessToken");

    }


}
