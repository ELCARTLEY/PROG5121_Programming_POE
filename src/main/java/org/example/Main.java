package org.example;

import model.User;
import service.LoginService;
import service.ValidationService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ValidationService validator = new ValidationService();
        LoginService loginService = new LoginService();

        System.out.println("=== REGISTER ===");

        // ================= FIRST NAME =================
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        // ================= LAST NAME =================
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        // ================= USERNAME =================
        String username;
        while (true) {
            System.out.print("Enter username: ");
            username = scanner.nextLine();

            if (validator.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");

                if (!retryOrExit(scanner)) return;
            }
        }

        // ================= PASSWORD =================
        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine();

            if (validator.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");

                if (!retryOrExit(scanner)) return;
            }
        }

        // ================= PHONE =================
        String phone;
        while (true) {
            System.out.print("Enter phone number (+27...): ");
            phone = scanner.nextLine();

            if (validator.checkCellPhoneNumber(phone)) {
                System.out.println("Cell phone number successfully added.");
                break;
            } else {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");

                if (!retryOrExit(scanner)) return;
            }
        }

        // CREATE USER
        User user = new User(username, password, phone, firstName, lastName);
        loginService.registerUser(user, validator);

        // ================= LOGIN =================
        int attempts = 0;
        boolean loggedIn = false;

        while (attempts < 3 && !loggedIn) {

            System.out.println("\n=== LOGIN ===");

            System.out.print("Username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Password: ");
            String loginPassword = scanner.nextLine();

            loggedIn = loginService.loginUser(loginUsername, loginPassword);

            if (loggedIn) {
                System.out.println(loginService.returnLoginStatus(true));
                break;
            } else {
                attempts++;
                System.out.println("Username or password incorrect, please try again.");

                if (attempts < 3) {
                    if (!retryOrExit(scanner)) return;
                }
            }
        }

        if (!loggedIn) {
            System.out.println("Too many failed attempts. Program exiting.");
        }

        scanner.close();
    }

    // ================= HELPER METHOD =================
    private static boolean retryOrExit(Scanner scanner) {
        while (true) {
            System.out.println("\n1. Try Again");
            System.out.println("2. Exit");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                return true;
            } else if (choice.equals("2")) {
                System.out.println("Exiting program...");
                return false;
            } else {
                System.out.println("Invalid option. Please choose 1 or 2.");
            }
        }
    }
}