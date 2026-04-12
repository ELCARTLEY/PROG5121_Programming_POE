package model;

public class Message {

    private String recipient;
    private String message;
    private String flag;
    private String messageId;
    private String messageHash;

    // Required for Gson
    public Message() {
    }

    public Message(String recipient, String message, String flag) {
        this.recipient = recipient;
        this.message = message;
        this.flag = flag;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }

    @Override
    public String toString() {
        return "Message{" +
                "recipient='" + recipient + '\'' +
                ", message='" + message + '\'' +
                ", flag='" + flag + '\'' +
                ", messageId='" + messageId + '\'' +
                ", messageHash='" + messageHash + '\'' +
                '}';
    }
}