package com.example.baruch.android5779_6256_4843.model;

public class Exceptions {

    public static boolean checkOnlyLetters(String value) {
        return value.matches("[a-zA-Z]+");
    }

    public static boolean checkOnlyNumbers(String value) {
        return value.matches("[0-9]+");
    }

    public static boolean checkEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$");
    }
}