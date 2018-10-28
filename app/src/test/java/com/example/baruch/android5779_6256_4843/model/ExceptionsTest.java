package com.example.baruch.android5779_6256_4843.model;

import org.junit.Test;

import static com.example.baruch.android5779_6256_4843.model.Exceptions.checkEmail;
import static com.example.baruch.android5779_6256_4843.model.Exceptions.checkOnlyLetters;
import static com.example.baruch.android5779_6256_4843.model.Exceptions.checkOnlyNumbers;
import static org.junit.Assert.*;

public class ExceptionsTest {

    @Test
    public void checkOnlyLettersTest() {
        assertTrue(checkOnlyLetters("Test"));
        assertFalse(checkOnlyLetters("Werw123f"));
        assertTrue(checkOnlyLetters("test"));
    }

    @Test
    public void checkOnlyNumbersTest() {
        String test="1234567890";
        assertTrue(checkOnlyNumbers(test));
        assertFalse(checkOnlyNumbers("1234d34"));
    }

    @Test
    public void checkEmailTest(){
        String goodEmail="123.gehlerb@gmail.com";
        assertTrue(checkEmail(goodEmail));
        String badEmail="gehler@gmailcom";
        assertFalse(checkEmail(badEmail));
    }

}