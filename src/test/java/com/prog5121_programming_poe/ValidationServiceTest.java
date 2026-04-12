package com.prog5121_programming_poe;


import org.testng.annotations.Test;
import service.ValidationService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ValidationServiceTest {

    ValidationService validator = new ValidationService();

    @Test
    void testValidUsername() {
        assertTrue(validator.checkUserName("kyl_l"));
    }

    @Test
    void testInvalidUsername() {
        assertFalse(validator.checkUserName("kyle"));
    }

    @Test
    void testValidPassword() {
        assertTrue(validator.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    void testInvalidPassword() {
        assertFalse(validator.checkPasswordComplexity("password"));
    }

    @Test
    void testValidPhone() {
        assertTrue(validator.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    void testInvalidPhone() {
        assertFalse(validator.checkCellPhoneNumber("08966553"));
    }
}