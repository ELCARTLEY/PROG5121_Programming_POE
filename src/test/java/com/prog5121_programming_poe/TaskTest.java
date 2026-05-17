package com.prog5121_programming_poe;

import model.Task;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for Task class
 * Tests task description validation, ID generation, details formatting, and total hours calculation
 */
public class TaskTest {

    private Task task1;
    private Task task2;
    private Task[] tasks;

    @Before
    public void setUp() {
        // Test Data 1
        task1 = new Task("Login Feature", 0, "Create Login to authenticate users",
                "Robyn", "Harrison", 8, "To Do");

        // Test Data 2
        task2 = new Task("Add Task Feature", 1, "Create Add Task feature to add task users",
                "Mike", "Smith", 10, "Doing");
    }

    // ================= CHECK TASK DESCRIPTION TESTS =================

    /**
     * Test that a valid task description (within 50 characters) passes validation
     */
    @Test
    public void testCheckTaskDescription_Success() {
        assertTrue("Task description should be valid (35 characters)",
                task1.checkTaskDescription());
    }

    /**
     * Test that a task description exceeding 50 characters fails validation
     */
    @Test
    public void testCheckTaskDescription_Failure() {
        Task invalidTask = new Task("Test Task", 0,
                "This is a very long task description that definitely exceeds fifty characters",
                "John", "Doe", 5, "To Do");
        assertFalse("Task description should be invalid (exceeds 50 characters)",
                invalidTask.checkTaskDescription());
    }

    /**
     * Test that a task description of exactly 50 characters passes
     */
    @Test
    public void testCheckTaskDescription_Exactly50Characters() {
        String description50 = "12345678901234567890123456789012345678901234567890"; // 50 chars
        Task task50 = new Task("Test", 0, description50, "John", "Doe", 5, "To Do");
        assertTrue("Task description with exactly 50 characters should be valid",
                task50.checkTaskDescription());
    }

    // ================= CREATE TASK ID TESTS =================

    /**
     * Test that Task ID is created in the correct format for test data 1
     * Expected format: AD:0:SON (first 2 letters : task number : last 3 letters of last name)
     */
    @Test
    public void testCreateTaskID_TestData1() {
        String expectedID = "LO:0:SON";
        assertEquals("Task ID should be LO:0:SON for Login Feature with Robyn Harrison",
                expectedID, task1.getTaskID());
    }

    /**
     * Test that Task ID is created in the correct format for test data 2
     */
    @Test
    public void testCreateTaskID_TestData2() {
        String expectedID = "AD:1:ITH";
        assertEquals("Task ID should be AD:1:ITH for Add Task Feature with Mike Smith",
                expectedID, task2.getTaskID());
    }

    /**
     * Test Task ID generation in a loop (multiple task IDs)
     */
    @Test
    public void testCreateTaskID_LoopMultipleTasks() {
        Task[] loopTasks = new Task[3];
        loopTasks[0] = new Task("Login Feature", 0, "Create Login", "Robyn", "Harrison", 8, "To Do");
        loopTasks[1] = new Task("Add Task Feature", 1, "Add Task Feature", "Mike", "Smith", 10, "Doing");
        loopTasks[2] = new Task("Report Feature", 2, "Create Report", "Sarah", "Johnson", 5, "Done");

        assertEquals("First task ID should be LO:0:SON", "LO:0:SON", loopTasks[0].getTaskID());
        assertEquals("Second task ID should be AD:1:ITH", "AD:1:ITH", loopTasks[1].getTaskID());
        assertEquals("Third task ID should be RE:2:SON", "RE:2:SON", loopTasks[2].getTaskID());
    }

    // ================= PRINT TASK DETAILS TESTS =================

    /**
     * Test that task details are printed in the correct format
     */
    @Test
    public void testPrintTaskDetails_FormatAndContent() {
        String details = task1.printTaskDetails();

        assertTrue("Task details should contain task status",
                details.contains("To Do"));
        assertTrue("Task details should contain developer name",
                details.contains("Robyn") && details.contains("Harrison"));
        assertTrue("Task details should contain task number",
                details.contains("0"));
        assertTrue("Task details should contain task name",
                details.contains("Login Feature"));
        assertTrue("Task details should contain task description",
                details.contains("Create Login to authenticate users"));
        assertTrue("Task details should contain task ID",
                details.contains("LO:0:SON"));
        assertTrue("Task details should contain task duration",
                details.contains("8 hours"));
    }

    // ================= RETURN TOTAL HOURS TESTS =================

    /**
     * Test that total hours are correctly calculated for two tasks
     * Test Data: 8 hours + 10 hours = 18 hours
     */
    @Test
    public void testReturnTotalHours_TwoTasks() {
        tasks = new Task[2];
        tasks[0] = task1;
        tasks[1] = task2;

        int totalHours = Task.returnTotalHours(tasks);
        assertEquals("Total hours should be 18 (8 + 10)",
                18, totalHours);
    }

    /**
     * Test that total hours are correctly accumulated in a loop
     * Additional test data: 5 tasks with various durations = 89 hours
     */
    @Test
    public void testReturnTotalHours_LoopMultipleTasks() {
        tasks = new Task[5];
        tasks[0] = new Task("Task1", 0, "Description 1", "Dev1", "Last1", 15, "To Do");
        tasks[1] = new Task("Task2", 1, "Description 2", "Dev2", "Last2", 20, "Done");
        tasks[2] = new Task("Task3", 2, "Description 3", "Dev3", "Last3", 18, "Doing");
        tasks[3] = new Task("Task4", 3, "Description 4", "Dev4", "Last4", 22, "To Do");
        tasks[4] = new Task("Task5", 4, "Description 5", "Dev5", "Last5", 14, "Done");

        int totalHours = Task.returnTotalHours(tasks);
        assertEquals("Total hours should be 89 (15+20+18+22+14)",
                89, totalHours);
    }

    /**
     * Test that total hours are correctly calculated on last iteration of loop
     * Last task in loop should contribute to total
     */
    @Test
    public void testReturnTotalHours_LastIterationLoop() {
        tasks = new Task[2];
        tasks[0] = task1; // 8 hours
        tasks[1] = task2; // 10 hours

        int totalHours = Task.returnTotalHours(tasks);
        assertEquals("Last iteration should add 10 hours to total",
                18, totalHours);
    }

    /**
     * Test that null tasks are handled gracefully in total hours calculation
     */
    @Test
    public void testReturnTotalHours_WithNullTasks() {
        tasks = new Task[3];
        tasks[0] = task1; // 8 hours
        tasks[1] = null;
        tasks[2] = task2; // 10 hours

        int totalHours = Task.returnTotalHours(tasks);
        assertEquals("Total hours should be 18, ignoring null task",
                18, totalHours);
    }

    // ================= GETTER TESTS =================

    /**
     * Test that all getters return correct values
     */
    @Test
    public void testGetters() {
        assertEquals("Task name should match", "Login Feature", task1.getTaskName());
        assertEquals("Task number should match", 0, task1.getTaskNumber());
        assertEquals("Task description should match", "Create Login to authenticate users",
                task1.getTaskDescription());
        assertEquals("Developer first name should match", "Robyn", task1.getDeveloperFirstName());
        assertEquals("Developer last name should match", "Harrison", task1.getDeveloperLastName());
        assertEquals("Task duration should match", 8, task1.getTaskDuration());
        assertEquals("Task ID should match", "LO:0:SON", task1.getTaskID());
        assertEquals("Task status should match", "To Do", task1.getTaskStatus());
    }

    /**
     * Test that Task ID matches expected format with different name lengths
     */
    @Test
    public void testCreateTaskID_SingleLetterName() {
        Task shortNameTask = new Task("A", 0, "Test", "John", "Doe", 5, "To Do");
        String taskID = shortNameTask.getTaskID();
        assertTrue("Task ID should handle single letter name", taskID.startsWith("A:"));
    }
}

