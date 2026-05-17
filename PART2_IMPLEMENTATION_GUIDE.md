# Part 2 - EasyKanban Tasks Management Implementation Guide

## Overview
This document outlines the implementation of Part 2 of the PROG5121 Programming POE assignment, which adds a Task Management feature to the existing login system. The implementation follows SOLID principles to ensure maintainable, scalable, and testable code.

## SOLID Principles Applied

### 1. **Single Responsibility Principle (SRP)**
- **Task.java**: Responsible only for task data and task-specific business logic (validation, ID generation, details formatting)
- **TaskService.java**: Responsible only for managing collections of tasks and task-related operations
- **Main.java**: Separated UI logic into dedicated methods (displayEasyKanbanMenu, handleAddTasks, collectTaskDetails, selectTaskStatus)
- **ValidationService.java**: Focuses only on validation operations

### 2. **Open/Closed Principle (OCP)**
- The code is open for extension (new task status types can be added without modifying existing code)
- Closed for modification (existing functionality remains unchanged)

### 3. **Liskov Substitution Principle (LSP)**
- Task objects can be used interchangeably in arrays and collections
- TaskService can manage Task objects without type casting

### 4. **Interface Segregation Principle (ISP)**
- Classes depend only on methods they actually use
- TaskService only uses the methods it needs from ValidationService

### 5. **Dependency Inversion Principle (DIP)**
- TaskService depends on ValidationService (abstraction of validation logic)
- Constructor injection used for TaskService to receive ValidationService dependency

## File Structure

```
PROG5121_Programming_POE/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── model/
│   │   │   │   ├── User.java (existing)
│   │   │   │   └── Task.java (NEW)
│   │   │   ├── org/example/
│   │   │   │   └── Main.java (UPDATED)
│   │   │   └── service/
│   │   │       ├── LoginService.java (existing)
│   │   │       ├── ValidationService.java (existing)
│   │   │       └── TaskService.java (NEW)
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/prog5121_programming_poe/
│               ├── LoginServiceTest.java (existing)
│               ├── ValidationServiceTest.java (existing)
│               └── TaskTest.java (NEW)
├── .github/
│   └── workflows/
│       └── maven.yml (NEW - CI/CD Pipeline)
└── pom.xml (UPDATED - added Maven Surefire plugin)
```

## Core Components

### 1. Task Model (task.java)
**Responsibility**: Represent and validate individual task data

**Key Methods**:
- `checkTaskDescription()`: Validates description ≤ 50 characters
- `createTaskID()`: Generates Task ID in format "XX:N:YYY" (first 2 letters of name : task number : last 3 letters of developer)
- `printTaskDetails()`: Returns formatted task information
- `returnTotalHours(Task[])`: Static method to calculate total hours across task array

**Key Fields**:
- taskName: String
- taskNumber: int (auto-incremented from 0)
- taskDescription: String (max 50 chars)
- developerFirstName: String
- developerLastName: String
- taskDuration: int (hours - use int for calculations)
- taskID: String (auto-generated)
- taskStatus: String (To Do, Done, Doing)

### 2. TaskService (TaskService.java)
**Responsibility**: Manage task collections and task-related operations

**Key Methods**:
- `addTask(Task)`: Adds task to array
- `checkTaskDescription(String)`: Validates description length
- `getTotalHours()`: Returns total combined hours
- `getTasks()`: Returns task array
- `hasRoomForMore()`: Checks if more tasks can be added

### 3. Main Application (Main.java)
**Responsibility**: Orchestrate user interaction and workflow

**New Methods**:
- `displayEasyKanbanMenu()`: Main menu after successful login
- `handleAddTasks()`: Manages task entry workflow
- `collectTaskDetails()`: Gathers individual task information
- `selectTaskStatus()`: Menu for task status selection

**Workflow**:
1. User registration (existing)
2. User login (existing)
3. **[NEW]** Welcome to EasyKanban display
4. **[NEW]** Menu loop:
   - Option 1: Add Tasks
   - Option 2: Show Report (Coming Soon)
   - Option 3: Quit

## Application Flow

### Registration & Login (Existing)
```
1. Register user with:
   - First name
   - Last name
   - Username (validation: contains "_", max 5 chars)
   - Password (validation: 8+ chars, capital letter, number, special char)
   - Phone (validation: +27XXXXXXXXX format)

2. Login with username and password (max 3 attempts)
```

### Task Management (NEW)
```
1. Display "Welcome to EasyKanban"

2. Main Menu Loop:
   ├─ Option 1: Add Tasks
   │  ├─ Ask: How many tasks to enter?
   │  └─ For each task:
   │     ├─ Collect: Task Name
   │     ├─ Collect: Task Description (validate ≤ 50 chars)
   │     ├─ Collect: Developer First Name
   │     ├─ Collect: Developer Last Name
   │     ├─ Collect: Task Duration (hours, int)
   │     ├─ Menu: Select Task Status (To Do/Done/Doing)
   │     ├─ Auto-generate: Task Number (0, 1, 2...)
   │     ├─ Auto-generate: Task ID (XX:N:YYY format)
   │     ├─ Validate: Description must be ≤ 50 chars
   │     │  └─ Success: Display task in JOptionPane
   │     │  └─ Failure: Retry task entry
   │  └─ Display: Total combined hours
   ├─ Option 2: Show Report → "Coming Soon"
   └─ Option 3: Quit → Exit program
```

## Task ID Generation Example

**Test Data 1**:
- Task Name: "Login Feature" → First 2 letters: "LO"
- Task Number: 0
- Developer: "Robyn Harrison" → Last 3 letters of last name: "SON"
- **Result**: `LO:0:SON`

**Test Data 2**:
- Task Name: "Add Task Feature" → First 2 letters: "AD"
- Task Number: 1
- Developer: "Mike Smith" → Last 3 letters of last name: "ITH"
- **Result**: `AD:1:ITH`

## Unit Testing

### Test File: TaskTest.java
Located in: `src/test/java/com/prog5121_programming_poe/TaskTest.java`

**Test Coverage**:

#### Task Description Validation
- `testCheckTaskDescription_Success()`: 35 character description passes
- `testCheckTaskDescription_Failure()`: 60 character description fails
- `testCheckTaskDescription_Exactly50Characters()`: Edge case - exactly 50 chars

#### Task ID Generation
- `testCreateTaskID_TestData1()`: "LO:0:SON" for Login Feature + Robyn Harrison
- `testCreateTaskID_TestData2()`: "AD:1:ITH" for Add Task Feature + Mike Smith
- `testCreateTaskID_LoopMultipleTasks()`: Multiple IDs generated correctly in loop
- `testCreateTaskID_SingleLetterName()`: Edge case - single letter task name

#### Task Details Printing
- `testPrintTaskDetails_FormatAndContent()`: Verifies format and content

#### Total Hours Calculation
- `testReturnTotalHours_TwoTasks()`: 8 + 10 = 18 hours
- `testReturnTotalHours_LoopMultipleTasks()`: 5 tasks = 89 hours (15+20+18+22+14)
- `testReturnTotalHours_LastIterationLoop()`: Last task contributes correctly
- `testReturnTotalHours_WithNullTasks()`: Null tasks handled gracefully

#### Getters
- `testGetters()`: All getter methods return correct values

### Running Tests

**Using Maven**:
```bash
mvn test
```

**Using IDE**:
- Right-click TaskTest.java → Run 'TaskTest'
- Or use keyboard shortcut (usually Ctrl+Shift+F10 in IntelliJ)

## GitHub Actions CI/CD Pipeline

### File: .github/workflows/maven.yml

**Triggers**:
- On push to main or Rhanbannasks branch
- On pull requests to main or Rhanbannasks branch

**Pipeline Steps**:
1. Check out code
2. Set up JDK 8
3. Build with Maven (clean compile)
4. Run tests with Maven
5. Generate test report (if tests complete)

**Benefits**:
- Automatic test execution on every push
- Early detection of breaking changes
- Ensures code quality standards
- Generates test reports

### GitHub Setup

1. Ensure code is pushed to GitHub
2. GitHub Actions enabled in repository settings
3. Workflow file in `.github/workflows/maven.yml`
4. Tests run automatically on push/PR

## Key Features Implemented

✅ Users can only add tasks after successful login
✅ Welcome message: "Welcome to EasyKanban"
✅ Numeric menu: 1) Add Tasks, 2) Show Report, 3) Quit
✅ Application runs until user selects Quit
✅ User defines number of tasks to enter
✅ Task information collection with validation
✅ Auto-generated task numbers (0, 1, 2...)
✅ Task description validation (≤ 50 characters)
✅ Auto-generated Task IDs (format: XX:N:YYY)
✅ Task status selection menu (To Do, Done, Doing)
✅ Task details displayed in JOptionPane
✅ Total hours calculation and display
✅ Comprehensive unit tests
✅ GitHub Actions CI/CD pipeline
✅ SOLID principles applied throughout

## Data Types Used

- **Task Name**: String
- **Task Number**: int (auto-incremented)
- **Task Description**: String (max 50 chars)
- **Developer Name**: String (separate first and last name)
- **Task Duration**: int (hours - for calculations)
- **Task ID**: String (auto-generated in specific format)
- **Task Status**: String (categorical: "To Do", "Done", "Doing")

## Error Handling

- Task description validation with user-friendly error message
- Invalid task duration input handling (must be positive integer)
- Invalid menu selection handling
- Task array boundary checking
- Null task handling in total hours calculation
- Exception handling for NumberFormatException

## Best Practices Implemented

1. **Comments and Documentation**: Methods have JavaDoc comments
2. **Consistent Naming**: Clear, descriptive variable and method names
3. **Separation of Concerns**: UI logic separated from business logic
4. **Input Validation**: All user inputs validated
5. **Error Messages**: Clear, actionable error messages for users
6. **Resource Management**: Scanner properly closed
7. **SOLID Principles**: All five SOLID principles applied
8. **Testing**: Comprehensive unit test coverage
9. **CI/CD**: Automated testing pipeline setup

## How to Use

### Running the Application

1. **Compile**:
   ```bash
   mvn clean compile
   ```

2. **Run**:
   ```bash
   mvn exec:java -Dexec.mainClass="org.example.Main"
   ```

3. **Or from IDE**:
   - Right-click Main.java → Run 'Main.main()'

### Example Usage Scenario

```
=== REGISTER ===
Enter first name: John
Enter last name: Doe
Enter username: john_d
Username successfully captured.
Enter password: Password123!
Password successfully captured.
Enter phone number (+27...): +27123456789
Cell phone number successfully added.

=== LOGIN ===
Username: john_d
Password: Password123!
Welcome John, Doe it is great to see you again.

=== Welcome to EasyKanban ===
Select an option:
1. Add Tasks
2. Show Report
3. Quit
Enter your choice: 1

How many tasks do you wish to enter? 2

--- Task 1 ---
Enter task name: Login Feature
Enter task description (max 50 characters): Create Login to authenticate users
Enter developer first name: Robyn
Enter developer last name: Harrison
Enter task duration (in hours): 8

Select task status:
1. To Do
2. Done
3. Doing
Enter your choice (1-3): 1
Task successfully captured

[JOptionPane displays task details]

--- Task 2 ---
[Similar task entry process]

=== Total Hours ===
Total combined hours across all tasks: 18 hours
```

## Conclusion

This implementation provides a fully functional task management system integrated with the existing user registration and login system. The code follows SOLID principles, includes comprehensive testing, and is automated with CI/CD pipelines for quality assurance.

