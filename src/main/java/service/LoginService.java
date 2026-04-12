package service;
import model.User;
import service.ValidationService;

public class LoginService {

    private User registeredUser;

    public boolean registerUser(User user, ValidationService validator) {

        if (!validator.checkUserName(user.getUsername())) {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            return false;
        }

        if (!validator.checkPasswordComplexity(user.getPassword())) {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            return false;
        }

        if (!validator.checkCellPhoneNumber(user.getPhoneNumber())) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            return false;
        }

        this.registeredUser = user;

        System.out.println("Username successfully captured.");
        System.out.println("Password successfully captured.");
        System.out.println("Cell phone number successfully added.");

        return true;
    }

    public boolean loginUser(String username, String password) {
        return registeredUser != null &&
                registeredUser.getUsername().equals(username) &&
                registeredUser.getPassword().equals(password);
    }

    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + registeredUser.getFirstName() + ", " + registeredUser.getLastName() + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}