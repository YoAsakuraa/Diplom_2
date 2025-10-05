package testData;

import java.util.Locale;

import com.github.javafaker.Faker;

public class UserTestData {
    private static final Faker faker = new Faker(new Locale("eu"));
    private static String email;
    private static String password;
    private static String name;


    public static class CreatedUserData {

        public static String generateUniqueBodyCreatedUser() {

            email = faker.internet().emailAddress();
            password = faker.internet().password();
            name = faker.name().firstName();

            return String.format("{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\"}",
                    email, password, name);
        }

        public static String generateBodyWithoutEmailCreatedUser() {

            password = faker.internet().password();
            name = faker.name().firstName();

            return String.format("{\"password\":\"%s\",\"name\":\"%s\"}",
                    password, name);
        }

        public static String generateBodyWithoutPasswordCreatedUser() {

            email = faker.internet().emailAddress();
            name = faker.name().firstName();

            return String.format("{\"email\":\"%s\",\"name\":\"%s\"}",
                    email, name);
        }

        public static String generateBodyWithoutNameCreatedUser() {

            email = faker.internet().emailAddress();
            password = faker.internet().password();

            return String.format("{\"email\":\"%s\",\"password\":\"%s\"}",
                    email, password);
        }

        public static String generateUserWithCustomPassword(String password) {

            email = faker.internet().emailAddress();
            name = faker.name().firstName();

            return String.format("{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\"}",
                    email, password, name);
        }



    }

    public static class Utils {

        public static String generateUniqueEmail() {

            return faker.internet().emailAddress();

        }

        public static String generateUniquePassword() {

            return faker.internet().password();

        }

        public static String generateUniqueName() {

            return faker.name().firstName();

        }

        public static String getNull() {

            return null;

        }

        public static String generateCustomBody(String email, String password , String name ) {

            return String.format("{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\"}",
                    email, password, name);
        }

    }



    public static class UpdateData {

        public static String generateEmailUpdate() {
            email = faker.internet().emailAddress();
            return String.format("{\"email\":\"%s\"}",
                    email);
        }

        public static String generateNameUpdate() {
            name = faker.name().firstName();
            return String.format("{\"name\":\"%s\"}",
                    name);
        }

        public static String generatePasswordUpdate() {
            password = faker.internet().password();
            return String.format("{\"password\":\"%s\"}",
                    password);
        }

        public static String PasswordUpdate(String password) {
            return String.format("{\"password\":\"%s\"}",
                    password);
        }

        public static String generateFullUpdateCustomPassword(String password) {
            email = faker.internet().emailAddress();
            name = faker.name().firstName();
            return String.format("{\"email\":\"%s\",\"name\":\"%s\",\"password\":\"%s\"}",
                    email, name, password);
        }
    }
}
