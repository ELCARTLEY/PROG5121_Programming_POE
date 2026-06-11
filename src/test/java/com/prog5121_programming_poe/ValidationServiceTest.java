package com.prog5121_programming_poe;

import org.junit.Before;
import org.junit.Test;
import service.ValidationService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * ValidationServiceTest - Unit tests for input validation
 *
 * Tests cover three main validation methods:
 * 1. Username validation (underscore required, max 5 chars)
 * 2. Password complexity validation (8+ chars, uppercase, number, special char)
 * 3. South African phone number validation (+27XXXXXXXXX format)
 */
public class ValidationServiceTest {

    private ValidationService validator;

    @Before
    public void setUp() {
        validator = new ValidationService();
    }

    // ==================== USERNAME VALIDATION TESTS ====================

    /**
     * Test Case 1: Valid username with underscore and exactly 5 characters
     * Expected: PASS
     */
    @Test
    public void testCheckUserName_ValidUsernameWithUnderscoreMaxLength() {
        assertTrue("Username 'kyl_l' (5 chars with underscore) should be valid",
                validator.checkUserName("kyl_l"));
    }

    /**
     * Test Case 2: Valid username with underscore but less than 5 characters
     * Expected: PASS
     */
    @Test
    public void testCheckUserName_ValidUsernameWithUnderscoreUnderMaxLength() {
        assertTrue("Username 'ab_c1' (5 chars with underscore) should be valid",
                validator.checkUserName("ab_c1"));
    }

    /**
     * Test Case 3: Invalid username without underscore
     * Expected: FAIL - Must have underscore
     */
    @Test
    public void testCheckUserName_InvalidUsernameWithoutUnderscore() {
        assertFalse("Username 'kyle' (no underscore) should be invalid",
                validator.checkUserName("kyle"));
    }

    /**
     * Test Case 4: Invalid username exceeding 5 characters
     * Expected: FAIL - Exceeds max length
     */
    @Test
    public void testCheckUserName_InvalidUsernameLongerThanFive() {
        assertFalse("Username 'ab_cd1' (6 chars, exceeds limit) should be invalid",
                validator.checkUserName("ab_cd1"));
    }

    /**
     * Test Case 5: Edge case - Single character with underscore
     * Expected: PASS
     */
    @Test
    public void testCheckUserName_EdgeCaseSingleCharWithUnderscore() {
        assertTrue("Username '_' (1 char with underscore) should be valid",
                validator.checkUserName("_"));
    }

    // ==================== PASSWORD COMPLEXITY VALIDATION TESTS ====================

    /**
     * Test Case 6: Valid complex password meeting all requirements
     * Requirements: 8+ chars, uppercase, number, special character
     * Expected: PASS
     */
    @Test
    public void testCheckPasswordComplexity_ValidComplexPassword() {
        assertTrue("Password 'Ch&&sec@ke99!' should be valid (8+ chars, uppercase, number, special char)",
                validator.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    /**
     * Test Case 7: Valid minimum length password with all requirements
     * Expected: PASS
     */
    @Test
    public void testCheckPasswordComplexity_ValidMinimumLengthPassword() {
        assertTrue("Password 'Strong@1' (8 chars exactly) should be valid",
                validator.checkPasswordComplexity("Strong@1"));
    }

    /**
     * Test Case 8: Invalid password without uppercase letter
     * Expected: FAIL - Missing uppercase
     */
    @Test
    public void testCheckPasswordComplexity_InvalidNoUppercase() {
        assertFalse("Password 'password@1' (no uppercase) should be invalid",
                validator.checkPasswordComplexity("password@1"));
    }

    /**
     * Test Case 9: Invalid password without number
     * Expected: FAIL - Missing numeric digit
     */
    @Test
    public void testCheckPasswordComplexity_InvalidNoNumber() {
        assertFalse("Password 'Password@' (no number) should be invalid",
                validator.checkPasswordComplexity("Password@"));
    }

    /**
     * Test Case 10: Invalid password without special character
     * Expected: FAIL - Missing special character
     */
    @Test
    public void testCheckPasswordComplexity_InvalidNoSpecialCharacter() {
        assertFalse("Password 'Password1' (no special character) should be invalid",
                validator.checkPasswordComplexity("Password1"));
    }

    /**
     * Test Case 11: Invalid password shorter than 8 characters
     * Expected: FAIL - Minimum length not met
     */
    @Test
    public void testCheckPasswordComplexity_InvalidShorterThanEight() {
        assertFalse("Password 'Pass@1' (7 chars, too short) should be invalid",
                validator.checkPasswordComplexity("Pass@1"));
    }

    /**
     * Test Case 12: Invalid password without any special character
     * Expected: FAIL
     */
    @Test
    public void testCheckPasswordComplexity_InvalidAllRequirementsButNoSpecial() {
        assertFalse("Password 'Password123' (all but special char) should be invalid",
                validator.checkPasswordComplexity("Password123"));
    }

    // ==================== PHONE NUMBER VALIDATION TESTS ====================

    /**
     * Test Case 13: Valid South African phone number
     * Format: +27XXXXXXXXX (country code +27 + 9 digits)
     * Expected: PASS
     */
    @Test
    public void testCheckCellPhoneNumber_ValidSouthAfricanPhone() {
        assertTrue("Phone '+27838968976' should be valid (SA format)",
                validator.checkCellPhoneNumber("+27838968976"));
    }

    /**
     * Test Case 14: Valid alternative South African phone number
     * Expected: PASS
     */
    @Test
    public void testCheckCellPhoneNumber_ValidAlternativeSAPhone() {
        assertTrue("Phone '+27123456789' should be valid (SA format)",
                validator.checkCellPhoneNumber("+27123456789"));
    }

    /**
     * Test Case 15: Invalid phone without international code
     * Expected: FAIL - Missing country code
     */
    @Test
    public void testCheckCellPhoneNumber_InvalidNoInternationalCode() {
        assertFalse("Phone '0838968976' (no +27) should be invalid",
                validator.checkCellPhoneNumber("0838968976"));
    }

    /**
     * Test Case 16: Invalid phone with wrong country code
     * Expected: FAIL - Not SA country code
     */
    @Test
    public void testCheckCellPhoneNumber_InvalidWrongCountryCode() {
        assertFalse("Phone '+26838968976' (wrong country code) should be invalid",
                validator.checkCellPhoneNumber("+26838968976"));
    }

    /**
     * Test Case 17: Invalid phone with incorrect length (too short)
     * Expected: FAIL - Not enough digits
     */
    @Test
    public void testCheckCellPhoneNumber_InvalidTooShort() {
        assertFalse("Phone '+2783896897' (11 chars, 1 digit short) should be invalid",
                validator.checkCellPhoneNumber("+2783896897"));
    }

    /**
     * Test Case 18: Invalid phone with incorrect length (too long)
     * Expected: FAIL - Too many digits
     */
    @Test
    public void testCheckCellPhoneNumber_InvalidTooLong() {
        assertFalse("Phone '+278389689760' (13 chars, 1 digit too many) should be invalid",
                validator.checkCellPhoneNumber("+278389689760"));
    }

    /**
     * Test Case 19: Invalid phone containing non-digit characters
     * Expected: FAIL - Contains letters
     */
    @Test
    public void testCheckCellPhoneNumber_InvalidContainsLetters() {
        assertFalse("Phone '+27838968A76' (contains letter 'A') should be invalid",
                validator.checkCellPhoneNumber("+27838968A76"));
    }

    /**
     * Test Case 20: Invalid phone with special characters (not +)
     * Expected: FAIL - Invalid character
     */
    @Test
    public void testCheckCellPhoneNumber_InvalidSpecialCharacters() {
        assertFalse("Phone '-27838968976' (uses '-' instead of '+') should be invalid",
                validator.checkCellPhoneNumber("-27838968976"));
    }
}