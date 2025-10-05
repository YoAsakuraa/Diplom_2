package testData;
import java.util.List;
import java.util.stream.Collectors;

public class OrderTestData {

    public static String createOrderWithIngredients(List<OrderTestData.Ingredient> ingredients) {
        if (ingredients.isEmpty()) {
            // ✅ Правильно для пустого массива
            return "{\"ingredients\": []}";
        }

        List<String> ids = ingredients.stream()
                .map(OrderTestData.Ingredient::getId)
                .collect(Collectors.toList());

        String ingredientsList = String.join("\",\"", ids);
        return String.format("{\"ingredients\": [\"%s\"]}", ingredientsList);
    }


    public enum Ingredient {
        // Булки
        FLUORESCENT_BUN("61c0c5a71d1f82001bdaaa6d", "Флюоресцентная булка"),
        CRATOR_BUN("61c0c5a71d1f82001bdaaa6c", "Краторная булка"),

        // Соусы
        SPICY_SAUCE("61c0c5a71d1f82001bdaaa72", "Соус Spicy"),
        CREAM_SAUCE("61c0c5a71d1f82001bdaaa73", "Соус фирменный Space Sauce"),
        TRADITIONAL_SAUCE("61c0c5a71d1f82001bdaaa74", "Соус традиционный галактический"),
        ANOTHER_SAUCE("61c0c5a71d1f82001bdaaa75", "Другой соус"),
        CUSTOM_SAUCE("61c0c5a71d1f82001bdaaaaa", "Несуществующий соус"),

        // Начинки
        BEEF("61c0c5a71d1f82001bdaaa6f", "Говяжий метеорит"),
        FILLET("61c0c5a71d1f82001bdaaa70", "Филе Люминесцентного тетраодонтимформа"),
        CHICKEN("61c0c5a71d1f82001bdaaa71", "Куриный бедренный цилиндр"),
        CHEESE("61c0c5a71d1f82001bdaaa6e", "Сыр с астероидной плесенью"),
        SALAD("61c0c5a71d1f82001bdaaa77", "Салат Листовой Кристальный"),
        EGG("61c0c5a71d1f82001bdaaa76", "Яйцо отборное Космической птицы"),
        TOMATO("61c0c5a71d1f82001bdaaa79", "Помидор Копус-8 с альфа-Центавры"),
        CUCUMBER("61c0c5a71d1f82001bdaaa78", "Огурец гидропонный Марсианский"),
        POTATO("61c0c5a71d1f82001bdaaa7a", "Картофель из планеты Плюк");

        private final String id;
        private final String name;

        Ingredient(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}