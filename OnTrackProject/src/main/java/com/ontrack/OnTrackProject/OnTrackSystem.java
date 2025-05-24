package com.ontrack.OnTrackProject;

import java.util.*;
import java.util.stream.Collectors;

public class OnTrackSystem {
    private Map<String, User> users;
    private Map<String, Task> tasks;
    private Map<String, TaskSubmission> submissions;
    private List<Message> messages;

    public OnTrackSystem() {
        this.users = new HashMap<>();
        this.tasks = new HashMap<>();
        this.submissions = new HashMap<>();
        this.messages = new ArrayList<>();
    }

    // User Management
    public void addUser(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        users.put(user.getUserId(), user);
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public boolean authenticateUser(String username, String password) {
        return username != null && password != null &&
                users.values().stream().anyMatch(u -> u.getUsername().equals(username));
    }

    // Task Management
    public void addTask(Task task) {
        if (task == null) throw new IllegalArgumentException("Task cannot be null");
        tasks.put(task.getTaskId(), task);
    }

    public Task getTask(String taskId) {
        return tasks.get(taskId);
    }

    public List<Task> getTasksByUnit(String unitCode) {
        return tasks.values().stream()
                .filter(task -> unitCode.equals(task.getUnitCode()))
                .collect(Collectors.toList());
    }

    public List<Task> getOverdueTasks() {
        return tasks.values().stream()
                .filter(Task::isOverdue)
                .collect(Collectors.toList());
    }

    // Submission Management
    public String submitTask(String taskId, String studentId, String content) {
        if (!tasks.containsKey(taskId)) throw new IllegalArgumentException("Task not found");
        if (!users.containsKey(studentId)) throw new IllegalArgumentException("Student not found");

        String submissionId = "SUB_" + System.currentTimeMillis();
        TaskSubmission submission = new TaskSubmission(submissionId, taskId, studentId, content);
        submissions.put(submissionId, submission);

        Task task = tasks.get(taskId);
        task.setStatus(Task.TaskStatus.SUBMITTED);

        return submissionId;
    }

    public TaskSubmission getSubmission(String submissionId) {
        return submissions.get(submissionId);
    }

    public List<TaskSubmission> getStudentSubmissions(String studentId) {
        return submissions.values().stream()
                .filter(sub -> studentId.equals(sub.getStudentId()))
                .collect(Collectors.toList());
    }

    public void gradeSubmission(String submissionId, double grade, String feedback) {
        TaskSubmission submission = submissions.get(submissionId);
        if (submission == null) throw new IllegalArgumentException("Submission not found");
        submission.setGrade(grade, feedback);
    }

    // Messaging
    public String sendMessage(String senderId, String receiverId, String content) {
        if (!users.containsKey(senderId) || !users.containsKey(receiverId))
            throw new IllegalArgumentException("Invalid sender or receiver");

        String messageId = "MSG_" + System.currentTimeMillis();
        Message message = new Message(messageId, senderId, receiverId, content);
        messages.add(message);
        return messageId;
    }

    public List<Message> getMessagesForUser(String userId) {
        return messages.stream()
                .filter(msg -> userId.equals(msg.getSenderId()) || userId.equals(msg.getReceiverId()))
                .collect(Collectors.toList());
    }

    public List<Message> getUnreadMessages(String userId) {
        return messages.stream()
                .filter(msg -> userId.equals(msg.getReceiverId()) && !msg.isRead())
                .collect(Collectors.toList());
    }

    // Progress Tracking
    public double calculateStudentProgress(String studentId, String unitCode) {
        Student student = (Student) users.get(studentId);
        if (student == null) return 0.0;

        List<TaskSubmission> studentSubs = getStudentSubmissions(studentId);
        List<Task> unitTasks = getTasksByUnit(unitCode);

        if (unitTasks.isEmpty()) return 0.0;

        long completedTasks = studentSubs.stream()
                .filter(sub -> unitTasks.stream()
                        .anyMatch(task -> task.getTaskId().equals(sub.getTaskId())))
                .filter(sub -> sub.getStatus() == TaskSubmission.SubmissionStatus.GRADED)
                .count();

        return (double) completedTasks / unitTasks.size() * 100;
    }

    // Stats
    public int getTotalUsers() { return users.size(); }
    public int getTotalTasks() { return tasks.size(); }
    public int getTotalSubmissions() { return submissions.size(); }

    public List<TaskSubmission> getPendingSubmissions() {
        return submissions.values().stream()
                .filter(sub -> sub.getStatus() == TaskSubmission.SubmissionStatus.PENDING)
                .collect(Collectors.toList());
    }
}
