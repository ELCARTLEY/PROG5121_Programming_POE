# Messaging Application

This is a simple console-based Java application that demonstrates user registration, login, and message handling functionalities. It's built using Apache Maven and includes features for sending, storing, and managing messages.

## Features

*   **User Authentication**:
    *   **Registration**: New users can register with a first name, last name, a unique username, a secure password, and a phone number.
    *   **Login**: Registered users can log in with their credentials. The system allows up to three login attempts before exiting.

*   **Message Handling**:
    *   **Send Messages**: Once logged in, users can send messages to a recipient. Each message is processed and assigned a unique ID and a SHA-256 hash.
    *   **Categorize Messages**: Messages can be flagged as `Sent`, `Stored`, or `Disregarded`.
    *   **Persistent Storage**: Messages flagged as `Stored` are saved to a `stored_messages.json` file, allowing data to persist between application sessions.

*   **Stored Messages Management**:
    *   View all stored messages (sender and recipient).
    *   Find the longest message among the stored ones.
    *   Search for a specific message using its unique ID.
    *   List all messages sent to a particular recipient.
    *   Delete a message from storage using its hash.
    *   Generate a full report of all details for every stored message.

## Getting Started

### Prerequisites

*   Java Development Kit (JDK) 8 or higher.
*   Apache Maven.

### How to Run

1.  **Clone the repository**:
    ```bash
    git clone <repository-url>
    cd PROG5121_Programming_POE
    ```

2.  **Build the project using Maven**:
    This command will compile the source code and download the required dependencies.
    ```bash
    mvn clean install
    ```

3.  **Run the application**:
    After a successful build, you can run the application from your IDE by running the `main` method in `org.example.Main`.

    Alternatively, you can run the compiled JAR file from the command line:
    ```bash
    java -cp target/PROG5121_Programming_POE-1.0-SNAPSHOT.jar org.example.Main
    ```

## Running the Tests

This project includes a suite of unit tests to ensure all functionalities are working as expected.

To run the tests, execute the following Maven command:
```bash
mvn test
```

The tests cover:
*   User input validation (username, password).
*   User login logic.
*   Correct population of message arrays.
*   All features in the "Stored Messages" menu.
*   Persistence of stored messages after deletion.

## CI/CD

This project is configured with a GitHub Actions workflow (`.github/workflows/TestJava.yml`) that automatically builds the project and runs the unit tests on every push to the repository. This ensures that the codebase remains stable and that new changes do not break existing functionality.
