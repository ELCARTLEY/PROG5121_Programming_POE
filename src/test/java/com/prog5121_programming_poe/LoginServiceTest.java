package com.prog5121_programming_poe;

import model.User;
import org.junit.Before;
import org.junit.Test;
import service.LoginService;
import service.ValidationService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * LoginServiceTest - Unit tests for user authentication and registration
 * 
 * Tests cover:
 * 1. User registration with validation
 * 2. User login with correct credentials
 * 3. Login failure scenarios
 * 4. Login status messaging
 */
public class LoginServiceTest {

    private LoginService loginService;
    private ValidationService validationService;

    @Before
    public void setUp() {
        loginService = new LoginService();
        validationService = new ValidationService();
    }

    /**
     * Test Case 1: Successful user registration and login
     * 
     * Prerequisites:
     * - Valid username: "kyl_1" (5 chars with underscore)
     * - Valid password: "Strong@123" (8+ chars, uppercase, number, special char)
     * - Valid phone: "+27838968976" (SA format)
     * - First name: "Kyle", Last name: "Smith"
     * 
     * Expected Results:
     * - Registration returns true
     * - Login returns true
     * - Correct welcome message displayed
     */
    @Test
    public void testRegisterAndLoginSuccess() {
        // Arrange
        User user = new User("kyl_1", "Strong@123", "+27838968976", "Kyle", "Smith");

        // Act
        boolean registered = loginService.registerUser(user, validationService);
        boolean loggedIn = loginService.loginUser("kyl_1", "Strong@123");
        String loginStatus = loginService.returnLoginStatus(true);

        // Assert
        assertTrue("User registration should succeed with valid credentials", registered);
        assertTrue("User login should succeed with correct credentials", loggedIn);
        assertEquals(
                "Login status message should contain personalized welcome",
                "Welcome Kyle, Smith it is great to see you again.",
                loginStatus
        );
    }

    /**
     * Test Case 2: Login fails with incorrect password
     * 
     * Prerequisites:
     * - User registered with correct password: "Complex@1"
     * - Login attempted with wrong password: "WrongPass@1"
     * 
     * Expected Results:
     * - Login returns false
     * - Appropriate error message returned
     */
    @Test
    public void testLoginFailsWithWrongPassword() {
        // Arrange
        User user = new User("ab_c1", "Complex@1", "+27838968976", "A", "B");
        loginService.registerUser(user, validationService);

        // Act
        boolean loggedIn = loginService.loginUser("ab_c1", "WrongPass@1");
        String errorStatus = loginService.returnLoginStatus(false);

        // Assert
        assertFalse("Login should fail with incorrect password", loggedIn);
        assertEquals(
                "Error message should indicate authentication failure",
                "Username or password incorrect, please try again.",
                errorStatus
        );
    }

    /**
     * Test Case 3: Login fails with incorrect username
     * 
     * Prerequisites:
     * - User registered with username: "ab_c1"
     * - Login attempted with different username: "xy_z9"
     * 
     * Expected Results:
     * - Login returns false
     */
    @Test
    public void testLoginFailsWithWrongUsername() {
        // Arrange
        User user = new User("ab_c1", "Complex@1", "+27838968976", "A", "B");
        loginService.registerUser(user, validationService);

        // Act
        boolean loggedIn = loginService.loginUser("xy_z9", "Complex@1");

        // Assert
        assertFalse("Login should fail with incorrect username", loggedIn);
    }

    /**
     * Test Case 4: Registration fails with invalid username
     * 
     * Prerequisites:
     * - Invalid username: "invalid" (no underscore, exceeds 5 chars)
     * - Valid password and phone provided
     * 
     * Expected Results:
     * - Registration returns false
     * - Validation error message logged
     */
    @Test
    public void testRegisterFailsForInvalidUsername() {
        // Arrange
        User user = new User("invalid", "Complex@1", "+27838968976", "A", "B");

        // Act
        boolean registered = loginService.registerUser(user, validationService);

        // Assert
        assertFalse("Registration should fail with invalid username", registered);
    }

    /**
     * Test Case 5: Registration fails with invalid password
     * 
     * Prerequisites:
     * - Valid username but invalid password (no special character)
     * 
     * Expected Results:
     * - Registration returns false
     */
    @Test
    public void testRegisterFailsForInvalidPassword() {
        // Arrange
        User user = new User("test_1", "Password123", "+27838968976", "Test", "User");

        // Act
        boolean registered = loginService.registerUser(user, validationService);

        // Assert
        assertFalse("Registration should fail with invalid password", registered);
    }

    /**
     * Test Case 6: Registration fails with invalid phone number
     * 
     * Prerequisites:
     * - Valid username and password but invalid phone (missing country code)
     * 
     * Expected Results:
     * - Registration returns false
     */
    @Test
    public void testRegisterFailsForInvalidPhoneNumber() {
        // Arrange
        User user = new User("test_1", "Strong@123", "0838968976", "Test", "User");

        // Act
        boolean registered = loginService.registerUser(user, validationService);

        // Assert
        assertFalse("Registration should fail with invalid phone number", registered);
    }

    /**
     * Test Case 7: Successful login with alternate valid credentials
     * 
     * Expected Results:
     * - Registration and login both succeed with different credentials
     */
    @Test
    public void testSuccessfulLoginWithAlternateCredentials() {
        // Arrange
        User user = new User("mike_s", "PowerPass@55", "+27712345678", "Mike", "Smith");

        // Act
        boolean registered = loginService.registerUser(user, validationService);
        boolean loggedIn = loginService.loginUser("mike_s", "PowerPass@55");

        // Assert
        assertTrue("User should be registered successfully with alternate credentials", registered);
        assertTrue("User should be able to login with alternate credentials", loggedIn);
    }
}

