package model;

public class Task {
    private String taskName;
    private int taskNumber;
    private String taskDescription;
    private String developerFirstName;
    private String developerLastName;
    private int taskDuration;
    private String taskID;
    private String taskStatus;

    // Constructor
    public Task(String taskName, int taskNumber, String taskDescription,
                String developerFirstName, String developerLastName,
                int taskDuration, String taskStatus) {
        this.taskName = taskName;
        this.taskNumber = taskNumber;
        this.taskDescription = taskDescription;
        this.developerFirstName = developerFirstName;
        this.developerLastName = developerLastName;
        this.taskDuration = taskDuration;
        this.taskStatus = taskStatus;
        this.taskID = createTaskID();
    }

    // Getters
    public String getTaskName() {
        return taskName;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getDeveloperFirstName() {
        return developerFirstName;
    }

    public String getDeveloperLastName() {
        return developerLastName;
    }

    public int getTaskDuration() {
        return taskDuration;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * Validates that the task description does not exceed 50 characters
     * @return true if description is 50 characters or less, false otherwise
     */
    public boolean checkTaskDescription() {
        return this.taskDescription.length() <= 50;
    }

    /**
     * Creates and returns the Task ID in the format:
     * First 2 letters of task name : Task number : Last 3 letters of developer last name (all CAPS)
     * @return the formatted Task ID
     */
    public String createTaskID() {
        String firstTwoLetters = taskName.substring(0, Math.min(2, taskName.length())).toUpperCase();
        String lastThreeLetters = developerLastName.substring(
            Math.max(0, developerLastName.length() - 3)
        ).toUpperCase();
        return String.format("%s:%d:%s", firstTwoLetters, taskNumber, lastThreeLetters);
    }

    /**
     * Returns the full task details in the required format:
     * Status, Developer Details, Task Number, Task Name, Task Description, Task ID, Duration
     * @return formatted string with all task details
     */
    public String printTaskDetails() {
        return String.format(
            "Task Status: %s\n" +
            "Developer Details: %s %s\n" +
            "Task Number: %d\n" +
            "Task Name: %s\n" +
            "Task Description: %s\n" +
            "Task ID: %s\n" +
            "Task Duration: %d hours",
            taskStatus,
            developerFirstName,
            developerLastName,
            taskNumber,
            taskName,
            taskDescription,
            taskID,
            taskDuration
        );
    }

    /**
     * Calculates the total hours from an array of tasks
     * @param tasks array of Task objects
     * @return total combined hours
     */
    public static int returnTotalHours(Task[] tasks) {
        int totalHours = 0;
        for (Task task : tasks) {
            if (task != null) {
                totalHours += task.getTaskDuration();
            }
        }
        return totalHours;
    }
}
