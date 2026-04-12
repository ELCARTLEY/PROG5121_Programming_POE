package com.prog5121_programming_poe;

import org.junit.Test;
import service.ValidationService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationServiceTest {

    private final ValidationService validator = new ValidationService();

    @Test
    public void shouldAcceptUsernameWithUnderscoreAndMaxLengthFive() {
        assertTrue(validator.checkUserName("kyl_l"));
    }

    @Test
    public void shouldRejectUsernameWithoutUnderscore() {
        assertFalse(validator.checkUserName("kyle"));
    }

    @Test
    public void shouldRejectUsernameLongerThanFiveCharacters() {
        assertFalse(validator.checkUserName("ab_cd1"));
    }

    @Test
    public void shouldAcceptUsernameExactlyFiveCharacters() {
        assertTrue(validator.checkUserName("ab_c1"));
    }

    @Test
    public void shouldAcceptComplexPassword() {
        assertTrue(validator.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void shouldRejectPasswordWithoutUppercase() {
        assertFalse(validator.checkPasswordComplexity("password@1"));
    }

    @Test
    public void shouldRejectPasswordWithoutNumber() {
        assertFalse(validator.checkPasswordComplexity("Password@"));
    }

    @Test
    public void shouldRejectPasswordWithoutSpecialCharacter() {
        assertFalse(validator.checkPasswordComplexity("Password1"));
    }

    @Test
    public void shouldRejectPasswordShorterThanEightCharacters() {
        assertFalse(validator.checkPasswordComplexity("password"));
    }

    @Test
    public void shouldAcceptValidSouthAfricanPhoneNumber() {
        assertTrue(validator.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void shouldRejectPhoneWithoutInternationalCode() {
        assertFalse(validator.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void shouldRejectPhoneWithInvalidCountryCode() {
        assertFalse(validator.checkCellPhoneNumber("+26838968976"));
    }

    @Test
    public void shouldRejectPhoneWithIncorrectLength() {
        assertFalse(validator.checkCellPhoneNumber("+2783896897"));
    }

    @Test
    public void shouldRejectPhoneContainingNonDigitCharacters() {
        assertFalse(validator.checkCellPhoneNumber("+27838968A76"));
    }
}