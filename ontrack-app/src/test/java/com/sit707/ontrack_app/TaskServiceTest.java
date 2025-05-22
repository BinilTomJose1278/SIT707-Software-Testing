package com.sit707.ontrack_app;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TaskServiceTest {

    @Test
    public void testValidStudentReturnsTasks() {
        TaskService service = new TaskService();
        List<String> tasks = service.getSubmittedTasks("S123");
        assertEquals(3, tasks.size());
        assertTrue(tasks.contains("Task 1 - Submitted"));
        assertTrue(tasks.contains("Task 2 - Reviewed"));
    }

    @Test
    public void testUnknownStudentReturnsEmptyList() {
        TaskService service = new TaskService();
        List<String> tasks = service.getSubmittedTasks("S999");
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }
}
