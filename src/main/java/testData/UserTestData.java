package testData;

import java.util.Locale;

import com.github.javafaker.Faker;

public class UserTestData {
    private static final Faker faker = new Faker(new Locale("eu"));
    private static String lastEmail;
    private static String lastPassword;
    private static String lastName;


    public static class CreatedUserData {

        public static String generateUniqueBodyCreatedUser() {

            lastEmail = faker.internet().emailAddress();
            lastPassword = faker.internet().password();
            lastName = faker.name().firstName();

            return String.format("{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\"}",
                    lastEmail, lastPassword, lastName);
        }

        public static String generateBodyWithoutEmailCreatedUser() {

            lastPassword = faker.internet().password();
            lastName = faker.name().firstName();

            return String.format("{\"password\":\"%s\",\"name\":\"%s\"}",
                    lastPassword, lastName);
        }

        public static String generateBodyWithoutPasswordCreatedUser() {

            lastEmail = faker.internet().emailAddress();
            lastName = faker.name().firstName();

            return String.format("{\"email\":\"%s\",\"name\":\"%s\"}",
                    lastEmail, lastName);
        }

        public static String generateBodyWithoutNameCreatedUser() {

            lastEmail = faker.internet().emailAddress();
            lastPassword = faker.internet().password();

            return String.format("{\"email\":\"%s\",\"password\":\"%s\"}",
                    lastEmail, lastPassword);
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

    }


    public static class Getters {
        public static String getLastEmail() {
            return lastEmail;
        }

        public static String getLastPassword() {
            return lastPassword;
        }

        public static String getLastName() {
            return lastName;
        }
    }
}
