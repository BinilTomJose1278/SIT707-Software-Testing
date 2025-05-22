package com.sit707.ontrack_app;

import java.util.*;

public class TaskService {

    private static final Map<String, List<String>> taskDB = new HashMap<>();

    static {
        taskDB.put("S123", Arrays.asList("Task 1 - Submitted", "Task 2 - Reviewed"));
        taskDB.put("S456", Arrays.asList("Task 3 - Pending"));
    }

    public List<String> getSubmittedTasks(String studentId) {
        return taskDB.getOrDefault(studentId, new ArrayList<>());
    }
}
