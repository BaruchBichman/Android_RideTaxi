package com.example.baruch.android5779_6256_4843.model;

public class Exceptions {
    boolean checkOnlyLetters(String value) {
        return value.matches("[a-zA-Z]");
    }

    boolean checkOnlyNumbers(String value){
        return value.matches("[0-9]+");
    }


}
