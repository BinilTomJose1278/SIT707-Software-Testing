package com.ontrack.OnTrackProject;

import java.util.*;

public class TaskSubmission {
    private String submissionId;
    private String taskId;
    private String studentId;
    private Date submissionDate;
    private String submittedContent;
    private List<String> attachments;
    private Double grade;
    private String feedback;
    private SubmissionStatus status;

    public enum SubmissionStatus {
        PENDING, UNDER_REVIEW, GRADED, REQUIRES_RESUBMISSION
    }

    public TaskSubmission(String submissionId, String taskId, String studentId, String submittedContent) {
        if (submissionId == null || submissionId.trim().isEmpty())
            throw new IllegalArgumentException("Submission ID cannot be null or empty");
        if (taskId == null || taskId.trim().isEmpty())
            throw new IllegalArgumentException("Task ID cannot be null or empty");
        if (studentId == null || studentId.trim().isEmpty())
            throw new IllegalArgumentException("Student ID cannot be null or empty");

        this.submissionId = submissionId;
        this.taskId = taskId;
        this.studentId = studentId;
        this.submittedContent = submittedContent;
        this.submissionDate = new Date();
        this.attachments = new ArrayList<>();
        this.status = SubmissionStatus.PENDING;
    }

    public void addAttachment(String filename) {
        if (filename != null && !filename.trim().isEmpty()) {
            attachments.add(filename);
        }
    }

    public void setGrade(double grade, String feedback) {
        if (grade < 0) throw new IllegalArgumentException("Grade cannot be negative");
        this.grade = grade;
        this.feedback = feedback;
        this.status = SubmissionStatus.GRADED;
    }

    public boolean isLateSubmission(Date dueDate) {
        return dueDate != null && submissionDate.after(dueDate);
    }
    public void removeAttachment(String filename) {
        attachments.remove(filename);
    }


    public String getSubmissionId() { return submissionId; }
    public String getTaskId() { return taskId; }
    public String getStudentId() { return studentId; }
    public Date getSubmissionDate() { return submissionDate; }
    public String getSubmittedContent() { return submittedContent; }
    public List<String> getAttachments() { return new ArrayList<>(attachments); }
    public Double getGrade() { return grade; }
    public String getFeedback() { return feedback; }
    public SubmissionStatus getStatus() { return status; }
    public void setStatus(SubmissionStatus status) { this.status = status; }
}
