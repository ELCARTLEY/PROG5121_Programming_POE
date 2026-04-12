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

        User user = null;
        boolean isRegistered = false;

        // ================= REGISTER =================
        while (!isRegistered) {

            System.out.println("\n=== REGISTER ===");

            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            System.out.print("Enter phone number (+27...): ");
            String phone = scanner.nextLine();

            user = new User(username, password, phone, firstName, lastName);

            isRegistered = loginService.registerUser(user, validator);

            if (!isRegistered) {
                System.out.println("\n1. Try Again");
                System.out.println("2. Exit");
                System.out.print("Choose option: ");
                String choice = scanner.nextLine();

                if (choice.equals("2")) {
                    System.out.println("Exiting program...");
                    return;
                }
            }
        }

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
                    System.out.println("\n1. Try Again");
                    System.out.println("2. Exit");
                    System.out.print("Choose option: ");
                    String choice = scanner.nextLine();

                    if (choice.equals("2")) {
                        System.out.println("Exiting program...");
                        return;
                    }
                }
            }
        }

        if (!loggedIn) {
            System.out.println("Too many failed attempts. Program exiting.");
        }

        scanner.close();
    }
}