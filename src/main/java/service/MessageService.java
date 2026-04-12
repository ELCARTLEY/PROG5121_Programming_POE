package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Message;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class MessageService {

    private static final String STORAGE_FILE = "stored_messages.json";

    private final List<Message> sentMessages = new ArrayList<>();
    private final List<Message> disregardedMessages = new ArrayList<>();
    private List<Message> storedMessages = new ArrayList<>();

    private final List<String> messageHashes = new ArrayList<>();
    private final List<String> messageIds = new ArrayList<>();

    public MessageService() {
        loadStoredMessages();
    }

    public void processMessage(Message message) {

        message.setMessageId(UUID.randomUUID().toString());

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(message.getMessage().getBytes());

            StringBuilder hashString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hashString.append('0');
                }

                hashString.append(hex);
            }

            message.setMessageHash(hashString.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageIds.add(message.getMessageId());
        messageHashes.add(message.getMessageHash());

        if ("Sent".equalsIgnoreCase(message.getFlag())) {

            sentMessages.add(message);

        } else if ("Disregard".equalsIgnoreCase(message.getFlag())) {

            disregardedMessages.add(message);

        } else if ("Stored".equalsIgnoreCase(message.getFlag())) {

            storedMessages.add(message);
            saveStoredMessages();
        }
    }

    private void saveStoredMessages() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(STORAGE_FILE)) {
            gson.toJson(storedMessages, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadStoredMessages() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(STORAGE_FILE)) {
            Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
            storedMessages = gson.fromJson(reader, listType);

            if (storedMessages != null) {
                for (Message message : storedMessages) {
                    if (message.getMessageId() != null && !messageIds.contains(message.getMessageId())) {
                        messageIds.add(message.getMessageId());
                    }
                    if (message.getMessageHash() != null && !messageHashes.contains(message.getMessageHash())) {
                        messageHashes.add(message.getMessageHash());
                    }
                }
            }
        } catch (IOException e) {
            storedMessages = new ArrayList<>();
        }

        if (storedMessages == null) {
            storedMessages = new ArrayList<>();
        }
    }

    public List<Message> getStoredMessages() {
        return storedMessages;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public List<Message> getDisregardedMessages() {
        return disregardedMessages;
    }

    public List<String> getMessageHashes() {
        return messageHashes;
    }

    public List<String> getMessageIds() {
        return messageIds;
    }

    public String getLongestStoredMessage() {

        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }

        Message longest = storedMessages.get(0);

        for (Message message : storedMessages) {

            if (message.getMessage().length()
                    > longest.getMessage().length()) {

                longest = message;
            }
        }

        return "Recipient: "
                + longest.getRecipient()
                + "\nMessage: "
                + longest.getMessage();
    }

    public String searchMessageById(String messageId) {

        for (Message message : storedMessages) {

            if (messageId != null
                    && messageId.equals(message.getMessageId())) {

                return "Recipient: "
                        + message.getRecipient()
                        + "\nMessage: "
                        + message.getMessage();
            }
        }

        return "Message not found.";
    }

    public List<Message> searchMessagesByRecipient(String recipient) {

        List<Message> results = new ArrayList<>();

        for (Message message : storedMessages) {

            if (recipient != null
                    && recipient.equalsIgnoreCase(
                    message.getRecipient())) {

                results.add(message);
            }
        }

        return results;
    }

    public boolean deleteMessageByHash(String messageHash) {

        Iterator<Message> iterator =
                storedMessages.iterator();

        while (iterator.hasNext()) {

            Message message = iterator.next();

            if (messageHash != null
                    && messageHash.equals(
                    message.getMessageHash())) {

                iterator.remove();

                messageHashes.remove(
                        message.getMessageHash());

                messageIds.remove(
                        message.getMessageId());

                saveStoredMessages();

                return true;
            }
        }

        return false;
    }

    public String getStoredMessagesReport() {

        if (storedMessages.isEmpty()) {
            return "No stored messages to report.";
        }

        StringBuilder report = new StringBuilder();

        report.append("===== STORED MESSAGES REPORT =====\n\n");

        for (Message message : storedMessages) {

            report.append("Recipient: ")
                    .append(message.getRecipient())
                    .append("\n");

            report.append("Message: ")
                    .append(message.getMessage())
                    .append("\n");

            report.append("Message ID: ")
                    .append(message.getMessageId())
                    .append("\n");

            report.append("Message Hash: ")
                    .append(message.getMessageHash())
                    .append("\n");

            report.append("---------------------------------\n");
        }

        return report.toString();
    }
}