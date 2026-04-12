package com.prog5121_programming_poe;

import org.junit.Test;
import service.ValidationService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationServiceTest {

    ValidationService validator = new ValidationService();

    @Test
    public void testValidUsername() {
        assertTrue(validator.checkUserName("kyl_l"));
    }

    @Test
    public void testInvalidUsername() {
        assertFalse(validator.checkUserName("kyle"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(validator.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(validator.checkPasswordComplexity("password"));
    }

    @Test
    public void testValidPhone() {
        assertTrue(validator.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testInvalidPhone() {
        assertFalse(validator.checkCellPhoneNumber("08966553"));
    }
}