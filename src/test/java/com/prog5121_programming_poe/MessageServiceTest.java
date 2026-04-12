package com.prog5121_programming_poe;

import model.Message;
import org.junit.Before;
import org.junit.Test;
import service.MessageService;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class MessageServiceTest {

    private MessageService messageService;
    private static final String STORAGE_FILE = "stored_messages.json";

    @Before
    public void setUp() {
        // Clean up the storage file before each test to ensure a clean state
        File dbFile = new File(STORAGE_FILE);
        if (dbFile.exists()) {
            dbFile.delete();
        }

        messageService = new MessageService(); // This will initialize with an empty list

        // Populate with fresh test data for each test run
        messageService.processMessage(new Message("+27834557896", "Did you get the cake?", "Sent"));
        messageService.processMessage(new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored"));
        messageService.processMessage(new Message("+27834484567", "Yohoooo, I am at your gate.", "Disregard"));
        messageService.processMessage(new Message("0838884567", "It is dinner time!", "Sent"));
        messageService.processMessage(new Message("+27838884567", "Ok, 1 am leaving without you.", "Stored"));
    }

    @Test
    public void testArrayPopulation() {
        // Test if all arrays are populated correctly based on the test data
        assertEquals("Should have 2 sent messages", 2, messageService.getSentMessages().size());
        assertEquals("Should have 2 stored messages", 2, messageService.getStoredMessages().size());
        assertEquals("Should have 1 disregarded message", 1, messageService.getDisregardedMessages().size());
        assertEquals("Should have 5 message IDs in total", 5, messageService.getMessageIds().size());
        assertEquals("Should have 5 message hashes in total", 5, messageService.getMessageHashes().size());
    }

    @Test
    public void testGetLongestStoredMessage() {
        String longestMessageDetails = messageService.getLongestStoredMessage();
        // The first stored message is longer
        assertTrue("The longest message details should contain the correct text",
                longestMessageDetails.contains("Where are you? You are late! I have asked you to be on time."));
    }

    @Test
    public void testSearchMessageById_Found() {
        // Get a real ID from a stored message
        Message firstStoredMessage = messageService.getStoredMessages().get(0);
        String realId = firstStoredMessage.getMessageId();

        String result = messageService.searchMessageById(realId);
        assertTrue("Should find the message by its ID", result.contains(firstStoredMessage.getRecipient()));
    }

    @Test
    public void testSearchMessageById_NotFound() {
        String result = messageService.searchMessageById("non-existent-id");
        assertEquals("Should return 'Message not found' for a non-existent ID", "Message not found.", result);
    }

    @Test
    public void testSearchMessagesByRecipient() {
        List<Message> results = messageService.searchMessagesByRecipient("+27838884567");
        assertEquals("Should find 2 messages for this recipient", 2, results.size());

        List<Message> noResults = messageService.searchMessagesByRecipient("+27000000000");
        assertTrue("Should find no messages for a non-existent recipient", noResults.isEmpty());
    }

    @Test
    public void testDeleteMessageByHash() {
        // Get a real hash from a stored message
        Message messageToDelete = messageService.getStoredMessages().get(0);
        String realHash = messageToDelete.getMessageHash();

        assertTrue("Delete operation should return true for a successful deletion", messageService.deleteMessageByHash(realHash));
        assertEquals("Stored messages list should have 1 less message after deletion", 1, messageService.getStoredMessages().size());
        assertFalse("Message hash should be removed from the hashes list", messageService.getMessageHashes().contains(realHash));
    }

    @Test
    public void testDeleteMessageByHash_NotFound() {
        assertFalse("Delete operation should return false for a non-existent hash", messageService.deleteMessageByHash("non-existent-hash"));
        assertEquals("Stored messages list should not change size", 2, messageService.getStoredMessages().size());
    }

    @Test
    public void testGetStoredMessagesReport() {
        String report = messageService.getStoredMessagesReport();
        assertTrue("Report should contain the main header", report.contains("===== STORED MESSAGES REPORT ====="));
        assertTrue("Report should contain data for the first stored message", report.contains("+27838884567"));
        assertTrue("Report should contain data for the second stored message", report.contains("Ok, 1 am leaving without you."));
    }

    @Test
    public void testPersistence() {
        // 1. Delete a message and confirm it's gone
        Message messageToDelete = messageService.getStoredMessages().get(0);
        String hashToDelete = messageToDelete.getMessageHash();
        messageService.deleteMessageByHash(hashToDelete);
        assertEquals("List should have 1 message after deletion", 1, messageService.getStoredMessages().size());

        // 2. Create a new service instance, which will load from the file
        MessageService newServiceInstance = new MessageService();

        // 3. Check if the loaded data reflects the deletion
        assertEquals("Newly loaded list should also have only 1 message", 1, newServiceInstance.getStoredMessages().size());
        assertNull("The deleted message should not be found in the new list",
                newServiceInstance.getStoredMessages().stream()
                        .filter(m -> m.getMessageHash().equals(hashToDelete))
                        .findFirst().orElse(null));
    }
}