package org.example.utils;

public class FieldsValidator {
    /**
     * Валидация email
     */
    public static boolean isEmailNullOrValid(String email) {
        return email == null || email.isEmpty() || email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    }

    /**
     * Валидация имени
     */
    public static boolean isNameValid(String name) {
        return name != null && !name.isEmpty() && name.matches("[a-zA-Zа-яА-Я\\s-]+");
    }

    /**
     * Валидация введенных данных
     */
    public static boolean isNameAndEmailValid(String name, String email) {
        return FieldsValidator.isEmailNullOrValid(email) && FieldsValidator.isNameValid(name);
    }

}
