package org.example;

import model.User;
import model.Task;
import service.LoginService;
import service.TaskService;
import service.ValidationService;

import javax.swing.JOptionPane;
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
            scanner.close();
            return;
        }

        // ================= EASYKANBAN MENU =================
        displayEasyKanbanMenu(scanner, validator);

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

    /**
     * Displays the EasyKanban main menu with options for task management
     * Follows SOLID principles by separating UI logic into dedicated methods
     */
    private static void displayEasyKanbanMenu(Scanner scanner, ValidationService validator) {
        System.out.println("\n=== Welcome to EasyKanban ===");
        boolean running = true;

        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add Tasks");
            System.out.println("2. Show Report");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleAddTasks(scanner, validator);
                    break;
                case "2":
                    System.out.println("Coming Soon");
                    break;
                case "3":
                    System.out.println("Thank you for using EasyKanban. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }

    /**
     * Handles the task addition workflow
     * Collects task count and processes task entries
     */
    private static void handleAddTasks(Scanner scanner, ValidationService validator) {
        System.out.print("\nHow many tasks do you wish to enter? ");
        int numberOfTasks;

        try {
            numberOfTasks = Integer.parseInt(scanner.nextLine());
            if (numberOfTasks <= 0) {
                System.out.println("Please enter a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return;
        }

        TaskService taskService = new TaskService(numberOfTasks, validator);

        // Collect tasks
        for (int i = 0; i < numberOfTasks; i++) {
            System.out.println("\n--- Task " + (i + 1) + " ---");
            Task task = collectTaskDetails(scanner, i, validator);

            if (task != null) {
                if (task.checkTaskDescription()) {
                    taskService.addTask(task);
                    System.out.println("Task successfully captured");

                    // Display task details in JOptionPane
                    JOptionPane.showMessageDialog(null, task.printTaskDetails(),
                        "Task Details - " + task.getTaskID(), JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("Please enter a task description of less than 50 characters");
                    i--; // Retry this task
                }
            }
        }

        // Display total hours
        int totalHours = taskService.getTotalHours();
        System.out.println("\n=== Total Hours ===");
        System.out.println("Total combined hours across all tasks: " + totalHours + " hours");
    }

    /**
     * Collects individual task details from user input
     * Validates task descriptions
     */
    private static Task collectTaskDetails(Scanner scanner, int taskNumber, ValidationService validator) {
        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine();

        System.out.print("Enter task description (max 50 characters): ");
        String taskDescription = scanner.nextLine();

        System.out.print("Enter developer first name: ");
        String developerFirstName = scanner.nextLine();

        System.out.print("Enter developer last name: ");
        String developerLastName = scanner.nextLine();

        int taskDuration;
        while (true) {
            try {
                System.out.print("Enter task duration (in hours): ");
                taskDuration = Integer.parseInt(scanner.nextLine());
                if (taskDuration <= 0) {
                    System.out.println("Please enter a positive number for duration.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        String taskStatus = selectTaskStatus(scanner);

        return new Task(taskName, taskNumber, taskDescription,
                       developerFirstName, developerLastName, taskDuration, taskStatus);
    }

    /**
     * Displays task status menu and gets user selection
     */
    private static String selectTaskStatus(Scanner scanner) {
        while (true) {
            System.out.println("\nSelect task status:");
            System.out.println("1. To Do");
            System.out.println("2. Done");
            System.out.println("3. Doing");
            System.out.print("Enter your choice (1-3): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    return "To Do";
                case "2":
                    return "Done";
                case "3":
                    return "Doing";
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }
