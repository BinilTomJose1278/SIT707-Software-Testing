package com.ontrack.OnTrackProject;

import java.util.*;

public class Task {
    private String taskId;
    private String title;
    private String description;
    private String unitCode;
    private Date dueDate;
    private int maxMarks;
    private TaskStatus status;

    public enum TaskStatus {
        AVAILABLE, SUBMITTED, REVIEWED, COMPLETED
    }

    public Task(String taskId, String title, String description, String unitCode, Date dueDate, int maxMarks) {
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }
        if (maxMarks < 0) {
            throw new IllegalArgumentException("Max marks cannot be negative");
        }

        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.unitCode = unitCode;
        this.dueDate = dueDate;
        this.maxMarks = maxMarks;
        this.status = TaskStatus.AVAILABLE;
    }

    public boolean isOverdue() {
        return dueDate != null && new Date().after(dueDate);
    }

    public long getDaysUntilDue() {
        if (dueDate == null) return Long.MAX_VALUE;
        long diff = dueDate.getTime() - new Date().getTime();
        return diff / (24 * 60 * 60 * 1000);
    }

    public String getTaskId() { return taskId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getUnitCode() { return unitCode; }
    public Date getDueDate() { return dueDate; }
    public int getMaxMarks() { return maxMarks; }
    public TaskStatus getStatus() { return status; }

    public void setStatus(TaskStatus status) { this.status = status; }
    public void setDescription(String description) { this.description = description; }
}
