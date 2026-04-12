package com.prog5121_programming_poe;

import model.User;
import org.junit.Test;
import service.LoginService;
import service.ValidationService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginServiceTest {

    private final LoginService loginService = new LoginService();
    private final ValidationService validationService = new ValidationService();

    @Test
    public void testRegisterAndLoginSuccess() {
        User user = new User("kyl_1", "Strong@123", "+27838968976", "Kyle", "Smith");

        boolean registered = loginService.registerUser(user, validationService);
        boolean loggedIn = loginService.loginUser("kyl_1", "Strong@123");

        assertTrue(registered);
        assertTrue(loggedIn);
        assertEquals(
                "Welcome Kyle, Smith it is great to see you again.",
                loginService.returnLoginStatus(true)
        );
    }

    @Test
    public void testLoginFailsWithWrongPassword() {
        User user = new User("ab_c1", "Complex@1", "+27838968976", "A", "B");
        loginService.registerUser(user, validationService);

        boolean loggedIn = loginService.loginUser("ab_c1", "WrongPass@1");

        assertFalse(loggedIn);
        assertEquals(
                "Username or password incorrect, please try again.",
                loginService.returnLoginStatus(false)
        );
    }

    @Test
    public void testRegisterFailsForInvalidUsername() {
        User user = new User("invalid", "Complex@1", "+27838968976", "A", "B");

        boolean registered = loginService.registerUser(user, validationService);

        assertFalse(registered);
    }
}

