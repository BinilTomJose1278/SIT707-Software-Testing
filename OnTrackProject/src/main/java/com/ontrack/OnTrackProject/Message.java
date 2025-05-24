package com.ontrack.OnTrackProject;

import java.util.*;

public class Message {
    private String messageId;
    private String senderId;
    private String receiverId;
    private String content;
    private Date timestamp;
    private boolean isRead;
    private String relatedTaskId;

    public Message(String messageId, String senderId, String receiverId, String content) {
        if (messageId == null || messageId.trim().isEmpty())
            throw new IllegalArgumentException("Message ID cannot be null or empty");
        if (senderId == null || senderId.trim().isEmpty())
            throw new IllegalArgumentException("Sender ID cannot be null or empty");
        if (receiverId == null || receiverId.trim().isEmpty())
            throw new IllegalArgumentException("Receiver ID cannot be null or empty");
        if (content == null || content.trim().isEmpty())
            throw new IllegalArgumentException("Message content cannot be null or empty");

        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = new Date();
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public long getAgeInMinutes() {
        return (new Date().getTime() - timestamp.getTime()) / (60 * 1000);
    }

    public String getMessageId() { return messageId; }
    public String getSenderId() { return senderId; }
    public String getReceiverId() { return receiverId; }
    public String getContent() { return content; }
    public Date getTimestamp() { return timestamp; }
    public boolean isRead() { return isRead; }
    public String getRelatedTaskId() { return relatedTaskId; }

    public void setRelatedTaskId(String relatedTaskId) {
        this.relatedTaskId = relatedTaskId;
    }
}
