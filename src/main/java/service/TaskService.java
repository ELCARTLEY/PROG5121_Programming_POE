package service;

import model.Task;

/**
 * TaskService - Responsible for task management operations
 * Follows SOLID principles - Single Responsibility: manages task-related business logic
 */
public class TaskService {

    private Task[] tasks;
    private int taskCount;
    private ValidationService validationService;

    /**
     * Constructor - Dependency Injection following SOLID principle
     * @param numberOfTasks maximum number of tasks to be added
     * @param validationService service for validation operations
     */
    public TaskService(int numberOfTasks, ValidationService validationService) {
        this.tasks = new Task[numberOfTasks];
        this.taskCount = 0;
        this.validationService = validationService;
    }

    /**
     * Adds a task to the task array
     * @param task the task to be added
     * @return true if task was added successfully, false if array is full
     */
    public boolean addTask(Task task) {
        if (taskCount < tasks.length) {
            tasks[taskCount] = task;
            taskCount++;
            return true;
        }
        return false;
    }

    /**
     * Validates task description length
     * @param description the task description to validate
     * @return true if description is 50 characters or less, false otherwise
     */
    public boolean checkTaskDescription(String description) {
        return description.length() <= 50;
    }

    /**
     * Gets the total number of hours across all tasks
     * @return total hours
     */
    public int getTotalHours() {
        return Task.returnTotalHours(tasks);
    }

    /**
     * Gets all tasks that have been added
     * @return array of tasks
     */
    public Task[] getTasks() {
        return tasks;
    }

    /**
     * Gets the number of tasks added so far
     * @return task count
     */
    public int getTaskCount() {
        return taskCount;
    }

    /**
     * Gets the maximum number of tasks allowed
     * @return maximum tasks
     */
    public int getMaxTasks() {
        return tasks.length;
    }

    /**
     * Checks if there is room to add more tasks
     * @return true if more tasks can be added, false otherwise
     */
    public boolean hasRoomForMore() {
        return taskCount < tasks.length;
    }
}

