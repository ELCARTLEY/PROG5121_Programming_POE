package org.example;

import model.Message;
import model.User;
import service.LoginService;
import service.MessageService;
import service.ValidationService;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String STORAGE_FILE = "stored_messages.json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ValidationService validator = new ValidationService();
        LoginService loginService = new LoginService();
        MessageService messageService = new MessageService();

        // Populate with test data ONLY if the storage file doesn't exist
        populateTestData(messageService);

        System.out.println("Welcome to the Messaging App!");

        // Registration
        registerUser(scanner, validator, loginService);

        // Login
        boolean loggedIn = loginUser(scanner, loginService);

        if (loggedIn) {
            System.out.println("\nWelcome to the application!");
            mainMenu(scanner, messageService);
        } else {
            System.out.println("Too many failed login attempts. Program exiting.");
        }

        scanner.close();
    }

    private static void registerUser(Scanner scanner, ValidationService validator, LoginService loginService) {
        System.out.println("\n=== REGISTER ===");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        String username;
        while (true) {
            System.out.print("Enter username (underscore, <= 5 chars): ");
            username = scanner.nextLine();
            if (validator.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Invalid username format.");
                if (!retryOrExit(scanner)) System.exit(0);
            }
        }

        String password;
        while (true) {
            System.out.print("Enter password (>= 8 chars, 1 uppercase, 1 number, 1 special char): ");
            password = scanner.nextLine();
            if (validator.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Invalid password format.");
                if (!retryOrExit(scanner)) System.exit(0);
            }
        }
        
        String phone;
        while (true) {
            System.out.print("Enter phone number (+27...): ");
            phone = scanner.nextLine();

            if (validator.checkCellPhoneNumber(phone)) {
                System.out.println("Cell phone number successfully added.");
                break;
            } else {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");

                if (!retryOrExit(scanner)) System.exit(0);
            }
        }


        User user = new User(username, password, phone, firstName, lastName);
        loginService.registerUser(user, validator);
    }

    private static boolean loginUser(Scanner scanner, LoginService loginService) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println("\n=== LOGIN ===");
            System.out.print("Username: ");
            String loginUsername = scanner.nextLine();
            System.out.print("Password: ");
            String loginPassword = scanner.nextLine();

            if (loginService.loginUser(loginUsername, loginPassword)) {
                System.out.println(loginService.returnLoginStatus(true));
                return true;
            } else {
                attempts++;
                System.out.println("Username or password incorrect, please try again.");
                if (attempts < 3) {
                    if (!retryOrExit(scanner)) break;
                }
            }
        }
        return false;
    }

    private static void mainMenu(Scanner scanner, MessageService messageService) {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Send a new message");
            System.out.println("2. Stored Messages");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    sendMessage(scanner, messageService);
                    break;
                case "2":
                    storedMessagesMenu(scanner, messageService);
                    break;
                case "3":
                    System.out.println("Exiting application...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void sendMessage(Scanner scanner, MessageService messageService) {
        System.out.print("Enter recipient's phone number: ");
        String recipient = scanner.nextLine();
        System.out.print("Enter message: ");
        String messageContent = scanner.nextLine();
        System.out.print("Enter flag (Sent/Disregard/Stored): ");
        String flag = scanner.nextLine();

        Message message = new Message(recipient, messageContent, flag);
        messageService.processMessage(message);
        System.out.println("Message processed.");
    }

    private static void storedMessagesMenu(Scanner scanner, MessageService messageService) {
        while (true) {
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("1. Display sender and recipient of all stored messages");
            System.out.println("2. Display the longest stored message");
            System.out.println("3. Search for a message by ID");
            System.out.println("4. Search for messages by recipient");
            System.out.println("5. Delete a message by hash");
            System.out.println("6. Display full report of stored messages");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayStoredMessageRecipients(messageService);
                    break;
                case "2":
                    System.out.println(messageService.getLongestStoredMessage());
                    break;
                case "3":
                    System.out.print("Enter Message ID: ");
                    String id = scanner.nextLine();
                    System.out.println(messageService.searchMessageById(id));
                    break;
                case "4":
                    System.out.print("Enter recipient's phone number: ");
                    String recipient = scanner.nextLine();
                    displayMessagesByRecipient(messageService, recipient);
                    break;
                case "5":
                    System.out.print("Enter Message Hash to delete: ");
                    String hash = scanner.nextLine();
                    if (messageService.deleteMessageByHash(hash)) {
                        System.out.println("Message deleted successfully.");
                    } else {
                        System.out.println("Message with given hash not found.");
                    }
                    break;
                case "6":
                    System.out.println(messageService.getStoredMessagesReport());
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void displayStoredMessageRecipients(MessageService messageService) {
        List<Message> stored = messageService.getStoredMessages();
        if (stored.isEmpty()) {
            System.out.println("No stored messages.");
            return;
        }
        System.out.println("--- Stored Messages (Sender -> Recipient) ---");
        // In this context, "sender" is the user of the app. We don't have that info in the Message object.
        // The prompt asks for "sender and recipient", but the Message object only has a recipient.
        // I will display the recipient.
        for (Message msg : stored) {
            System.out.println("Recipient: " + msg.getRecipient());
        }
    }

    private static void displayMessagesByRecipient(MessageService messageService, String recipient) {
        List<Message> results = messageService.searchMessagesByRecipient(recipient);
        if (results.isEmpty()) {
            System.out.println("No messages found for this recipient.");
            return;
        }
        System.out.println("--- Messages for " + recipient + " ---");
        for (Message msg : results) {
            System.out.println("Message: " + msg.getMessage());
        }
    }


    private static void populateTestData(MessageService messageService) {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) {
            messageService.processMessage(new Message("+27834557896", "Did you get the cake?", "Sent"));
            messageService.processMessage(new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored"));
            messageService.processMessage(new Message("+27834484567", "Yohoooo, I am at your gate.", "Disregard"));
            messageService.processMessage(new Message("0838884567", "It is dinner time!", "Sent"));
            messageService.processMessage(new Message("+27838884567", "Ok, 1 am leaving without you.", "Stored"));
        }
    }

    private static boolean retryOrExit(Scanner scanner) {
        while (true) {
            System.out.println("\n1. Try Again\n2. Exit");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();
            if ("1".equals(choice)) return true;
            if ("2".equals(choice)) return false;
            System.out.println("Invalid option.");
        }
    }
}