
package com.ontrack.OnTrackProject;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class OnTrackSystemTest {

 private OnTrackSystem onTrackSystem;
 private Student testStudent;
 private Tutor testTutor;
 private Task testTask;

 @BeforeEach
 void setUp() {
     onTrackSystem = new OnTrackSystem();
     testStudent = new Student("S001", "johndoe", "john@example.com", "John", "Doe", "STU123456");
     testTutor = new Tutor("T001", "janesmithtutor", "jane@university.edu", "Jane", "Smith", "EMP789", "Computer Science");
     testTask = new Task("TASK001", "Assignment 1", "Complete the coding assignment", "SIT707", getFutureDate(7), 100);
 }

 private Date getFutureDate(int daysFromNow) {
     Calendar cal = Calendar.getInstance();
     cal.add(Calendar.DAY_OF_MONTH, daysFromNow);
     return cal.getTime();
 }

 private Date getPastDate(int daysAgo) {
     Calendar cal = Calendar.getInstance();
     cal.add(Calendar.DAY_OF_MONTH, -daysAgo);
     return cal.getTime();
 }

 @Test
 void testUserCreationCorrectness() {
     assertEquals("S001", testStudent.getUserId());
     assertEquals("johndoe", testStudent.getUsername());
     assertEquals("john@example.com", testStudent.getEmail());
     assertEquals("John Doe", testStudent.getFullName());
     assertEquals("STU123456", testStudent.getStudentNumber());
     assertNotNull(testStudent.getRegistrationDate());
 }

 @Test
 void testTaskSubmissionCorrectness() {
     onTrackSystem.addUser(testStudent);
     onTrackSystem.addTask(testTask);

     String submissionId = onTrackSystem.submitTask("TASK001", "S001", "My solution content");
     TaskSubmission submission = onTrackSystem.getSubmission(submissionId);

     assertNotNull(submission);
     assertEquals("TASK001", submission.getTaskId());
     assertEquals("S001", submission.getStudentId());
     assertEquals("My solution content", submission.getSubmittedContent());
     assertEquals(TaskSubmission.SubmissionStatus.PENDING, submission.getStatus());
 }

 @Test
 void testProgressCalculationCorrectness() {
     testStudent.enrollInUnit("SIT707");
     testStudent.updateUnitProgress("SIT707", 75.5);

     assertEquals(75.5, testStudent.getUnitProgress("SIT707"), 0.01);
     assertEquals(75.5, testStudent.getOverallProgress(), 0.01);
 }

 @Test
 void testMessageSystemCorrectness() {
     onTrackSystem.addUser(testStudent);
     onTrackSystem.addUser(testTutor);

     String messageId = onTrackSystem.sendMessage("S001", "T001", "Hello tutor");

     List<Message> studentMessages = onTrackSystem.getMessagesForUser("S001");
     List<Message> tutorMessages = onTrackSystem.getMessagesForUser("T001");

     assertEquals(1, studentMessages.size());
     assertEquals(1, tutorMessages.size());
     assertEquals(messageId, studentMessages.get(0).getMessageId());
     assertEquals(messageId, tutorMessages.get(0).getMessageId());
 }

 @Test
 void testBoundaryEmptyInputs() {
     assertThrows(IllegalArgumentException.class, () -> new Student(null, "user", "email@test.com", "First", "Last", "STU123"));
     assertThrows(IllegalArgumentException.class, () -> new Student("", "user", "email@test.com", "First", "Last", "STU123"));
     assertThrows(IllegalArgumentException.class, () -> new Student("   ", "user", "email@test.com", "First", "Last", "STU123"));
 }

 @Test
 void testBoundaryProgressValues() {
     testStudent.enrollInUnit("SIT707");
     testStudent.updateUnitProgress("SIT707", 0.0);
     assertEquals(0.0, testStudent.getUnitProgress("SIT707"));
     testStudent.updateUnitProgress("SIT707", 100.0);
     assertEquals(100.0, testStudent.getUnitProgress("SIT707"));
     assertThrows(IllegalArgumentException.class, () -> testStudent.updateUnitProgress("SIT707", -0.1));
     assertThrows(IllegalArgumentException.class, () -> testStudent.updateUnitProgress("SIT707", 100.1));
 }

 @Test
 void testBoundaryTaskMarks() {
     assertDoesNotThrow(() -> new Task("T1", "Zero marks task", "Description", "SIT707", new Date(), 0));
     assertThrows(IllegalArgumentException.class, () -> new Task("T1", "Negative marks task", "Description", "SIT707", new Date(), -1));
 }

 @ParameterizedTest
 @ValueSource(strings = {"", " ", "\t", "\n"})
 void testBoundaryWhitespaceInputs(String input) {
     assertThrows(IllegalArgumentException.class, () -> new Task(input, "Title", "Description", "SIT707", new Date(), 100));
 }

 @Test
 void testInverseUserEnrollment() {
     testStudent.enrollInUnit("SIT707");
     assertTrue(testStudent.getEnrolledUnits().contains("SIT707"));
 }
 @Test
 void testPerformanceOfMessageCreation() {
     onTrackSystem.addUser(testStudent);
     onTrackSystem.addUser(testTutor);

     long start = System.nanoTime();
     for (int i = 0; i < 1000; i++) {
         onTrackSystem.sendMessage("S001", "T001", "Test message " + i);
     }
     long duration = System.nanoTime() - start;
     System.out.println("Time for 1000 messages: " + duration / 1_000_000 + " ms");

     assertTrue(onTrackSystem.getMessagesForUser("S001").size() >= 1000);
 }
 @Test
 void testGradingSubmission() {
     onTrackSystem.addUser(testStudent);
     onTrackSystem.addTask(testTask);
     String subId = onTrackSystem.submitTask("TASK001", "S001", "Solution");

     onTrackSystem.gradeSubmission(subId, 85.0, "Well done");

     TaskSubmission submission = onTrackSystem.getSubmission(subId);
     assertEquals(85.0, submission.getGrade());
     assertEquals("Well done", submission.getFeedback());
     assertEquals(TaskSubmission.SubmissionStatus.GRADED, submission.getStatus());
 }
 @Test
 void testUnreadMessages() {
     onTrackSystem.addUser(testStudent);
     onTrackSystem.addUser(testTutor);

     onTrackSystem.sendMessage("T001", "S001", "Please check feedback");
     List<Message> unread = onTrackSystem.getUnreadMessages("S001");

     assertEquals(1, unread.size());
     assertFalse(unread.get(0).isRead());
 }
 @Test
 void testOverdueTasks() {
     Task overdueTask = new Task("T002", "Past Task", "Was due", "SIT707", getPastDate(2), 50);
     onTrackSystem.addTask(overdueTask);

     List<Task> overdue = onTrackSystem.getOverdueTasks();
     assertTrue(overdue.stream().anyMatch(t -> t.getTaskId().equals("T002")));
 }
 @Test
 void testPendingSubmissions() {
     onTrackSystem.addUser(testStudent);
     onTrackSystem.addTask(testTask);
     onTrackSystem.submitTask("TASK001", "S001", "Pending work");

     List<TaskSubmission> pending = onTrackSystem.getPendingSubmissions();
     assertFalse(pending.isEmpty());
 }
 @Test
 void testSetTaskDescription() {
     testTask.setDescription("Updated description");
     assertEquals("Updated description", testTask.getDescription());
 }
 @Test
 void testNullDueDateHandling() {
     Task noDueDateTask = new Task("T003", "No Due", "Flexible", "SIT708", null, 100);
     assertFalse(noDueDateTask.isOverdue());
     assertEquals(Long.MAX_VALUE, noDueDateTask.getDaysUntilDue());
 }
 @Test
 void testMarkMessageAsRead() {
     onTrackSystem.addUser(testStudent);
     onTrackSystem.addUser(testTutor);

     String msgId = onTrackSystem.sendMessage("T001", "S001", "Check this out");
     Message msg = onTrackSystem.getMessagesForUser("S001").get(0);

     assertFalse(msg.isRead());
     msg.markAsRead();
     assertTrue(msg.isRead());
 }
 @Test
 void testIsLateSubmission() {
     TaskSubmission submission = new TaskSubmission("SUB1", "T1", "S1", "Late content");
     Date pastDue = getPastDate(1);
     assertTrue(submission.isLateSubmission(pastDue));
 }
 @Test
 void testUserSetters() {
     testStudent.setFirstName("Updated");
     testStudent.setLastName("Name");
     testStudent.setEmail("updated@example.com");

     assertEquals("Updated", testStudent.getFirstName());
     assertEquals("Name", testStudent.getLastName());
     assertEquals("updated@example.com", testStudent.getEmail());
 }
 @Test
 void testZeroProgressWhenNoTasks() {
     onTrackSystem.addUser(testStudent);
     double progress = onTrackSystem.calculateStudentProgress("S001", "SIT999");
     assertEquals(0.0, progress);
 }
 @Test
 void testSetCustomSubmissionStatus() {
     TaskSubmission submission = new TaskSubmission("S1", "T1", "S1", "Content");
     submission.setStatus(TaskSubmission.SubmissionStatus.REQUIRES_RESUBMISSION);
     assertEquals(TaskSubmission.SubmissionStatus.REQUIRES_RESUBMISSION, submission.getStatus());
 }
 @Test
 void testTaskSetStatusReviewed() {
     testTask.setStatus(Task.TaskStatus.REVIEWED);
     assertEquals(Task.TaskStatus.REVIEWED, testTask.getStatus());
 }
 @Test
 void testTaskDescriptionGetter() {
     testTask.setDescription("Check Description");
     assertEquals("Check Description", testTask.getDescription());
 }
 @Test
 void testAddAttachmentsToSubmission() {
     TaskSubmission submission = new TaskSubmission("S2", "T1", "S1", "Answer");
     submission.addAttachment("file1.pdf");
     submission.addAttachment("file2.png");

     List<String> files = submission.getAttachments();
     assertEquals(2, files.size());
     assertTrue(files.contains("file1.pdf"));
 }
 @Test
 void testSetSubmissionStatus() {
     TaskSubmission submission = new TaskSubmission("S3", "T1", "S1", "Some work");
     submission.setStatus(TaskSubmission.SubmissionStatus.UNDER_REVIEW);
     assertEquals(TaskSubmission.SubmissionStatus.UNDER_REVIEW, submission.getStatus());
 }
 @Test
 void testMessageAgeInMinutes() {
     Message msg = new Message("M001", "S001", "T001", "Time check");
     long age = msg.getAgeInMinutes();
     assertTrue(age >= 0); // Should be very small, since just created
 }
 @Test
 void testOverallProgressWithNoUnits() {
     Student emptyStudent = new Student("S010", "empty", "empty@user.com", "Empty", "User", "STU000");
     assertEquals(0.0, emptyStudent.getOverallProgress());
 }
 @Test
 void testSubmitTaskInvalidInput() {
     assertThrows(IllegalArgumentException.class, () ->
         onTrackSystem.submitTask("INVALID_TASK", "S001", "Test"));

     assertThrows(IllegalArgumentException.class, () ->
         onTrackSystem.submitTask("TASK001", "INVALID_STUDENT", "Test"));
 }
 @Test
 void testSetTaskFieldsIndividually() {
     Task task = new Task("T100", "Initial Title", "Initial Description", "UNIT100", getFutureDate(5), 100);
     task.setDescription("Updated Description");
     assertEquals("Updated Description", task.getDescription());
 }



 @Test
 void testMessageRelatedTaskIdSetterGetter() {
     Message message = new Message("MSG001", "S001", "T001", "Message content");
     message.setRelatedTaskId("TASK123");
     assertEquals("TASK123", message.getRelatedTaskId());
 }

 @Test
 void testAuthenticateUserValidAndInvalid() {
     onTrackSystem.addUser(testStudent);
     assertTrue(onTrackSystem.authenticateUser("johndoe", "anyPassword")); // Password not validated
     assertFalse(onTrackSystem.authenticateUser("invaliduser", "pass"));
 }

 @Test
 void testSubmitTaskStatusUpdate() {
     onTrackSystem.addUser(testStudent);
     onTrackSystem.addTask(testTask);
     String submissionId = onTrackSystem.submitTask("TASK001", "S001", "Answer");
     assertEquals(Task.TaskStatus.SUBMITTED, testTask.getStatus());
 }

 @Test
 void testInvalidUserMessageSendThrows() {
     assertThrows(IllegalArgumentException.class, () -> onTrackSystem.sendMessage("invalid", "T001", "text"));
     assertThrows(IllegalArgumentException.class, () -> onTrackSystem.sendMessage("S001", "invalid", "text"));
 }

 @Test
 void testSubmissionConstructorValidations() {
     assertThrows(IllegalArgumentException.class, () -> new TaskSubmission(null, "T", "S", "C"));
     assertThrows(IllegalArgumentException.class, () -> new TaskSubmission("ID", "", "S", "C"));
     assertThrows(IllegalArgumentException.class, () -> new TaskSubmission("ID", "T", "", "C"));
 }

 @Test
 void testAddInvalidAttachmentIgnored() {
     TaskSubmission submission = new TaskSubmission("S4", "T1", "S1", "Work");
     submission.addAttachment(null);
     submission.addAttachment("   ");
     assertTrue(submission.getAttachments().isEmpty());
 }

 @Test
 void testGetTotalCountsInSystem() {
     onTrackSystem.addUser(testStudent);
     onTrackSystem.addTask(testTask);
     onTrackSystem.submitTask("TASK001", "S001", "Done");

     assertEquals(1, onTrackSystem.getTotalUsers());
     assertEquals(1, onTrackSystem.getTotalTasks());
     assertEquals(1, onTrackSystem.getTotalSubmissions());
 }

 @Test
 void testGetTasksByUnitCode() {
     onTrackSystem.addTask(testTask);
     List<Task> tasks = onTrackSystem.getTasksByUnit("SIT707");
     assertEquals(1, tasks.size());
     assertEquals("TASK001", tasks.get(0).getTaskId());
 }

 @Test
 void testEmptySystemEdgeCases() {
     assertTrue(onTrackSystem.getPendingSubmissions().isEmpty());
     assertTrue(onTrackSystem.getOverdueTasks().isEmpty());
     assertTrue(onTrackSystem.getUnreadMessages("S001").isEmpty());
 }

 @Test
 void testProgressCalculationMultipleTasks() {
     onTrackSystem.addUser(testStudent);
     testStudent.enrollInUnit("SIT700");

     Task t1 = new Task("T1", "Title1", "Desc1", "SIT700", getFutureDate(2), 100);
     Task t2 = new Task("T2", "Title2", "Desc2", "SIT700", getFutureDate(2), 100);

     onTrackSystem.addTask(t1);
     onTrackSystem.addTask(t2);

     String sub1 = onTrackSystem.submitTask("T1", "S001", "A");
     String sub2 = onTrackSystem.submitTask("T2", "S001", "B");

     onTrackSystem.gradeSubmission(sub1, 80, "Good");
     onTrackSystem.gradeSubmission(sub2, 90, "Great");

     double progress = onTrackSystem.calculateStudentProgress("S001", "SIT700");
     assertEquals(50, progress); // 2 of 2 graded
 }
 @Test
 void testProgressWithPartialGrading() {
     onTrackSystem.addUser(testStudent);
     testStudent.enrollInUnit("SIT800");

     Task t1 = new Task("T3", "Part 1", "Partial", "SIT800", getFutureDate(3), 50);
     Task t2 = new Task("T4", "Part 2", "Unmarked", "SIT800", getFutureDate(3), 50);

     onTrackSystem.addTask(t1);
     onTrackSystem.addTask(t2);

     String sub1 = onTrackSystem.submitTask("T3", "S001", "Answer");
     onTrackSystem.submitTask("T4", "S001", "Answer");

     onTrackSystem.gradeSubmission(sub1, 50, "Nice");

     double progress = onTrackSystem.calculateStudentProgress("S001", "SIT800");
     assertEquals(50.0, progress); // 1 of 2 graded
 }

 @Test
 void testUnenrollUnitAndProgressReset() {
     testStudent.enrollInUnit("SIT999");
     testStudent.updateUnitProgress("SIT999", 80.0);
     assertEquals(80.0, testStudent.getUnitProgress("SIT999"));
     testStudent.unenrollFromUnit("SIT999");
     assertEquals(0.0, testStudent.getUnitProgress("SIT999"));
 }

 @Test
 void testRemoveAttachmentFunctionality() {
     TaskSubmission submission = new TaskSubmission("SUBREMOVE", "T002", "S002", "File upload");
     submission.addAttachment("test1.pdf");
     submission.addAttachment("test2.docx");
     submission.removeAttachment("test1.pdf");
     assertFalse(submission.getAttachments().contains("test1.pdf"));
 }
 @Test
 void testGetTaskAndSubmissionById() {
     onTrackSystem.addUser(testStudent);
     onTrackSystem.addTask(testTask);
     String submissionId = onTrackSystem.submitTask("TASK001", "S001", "Some content");

     Task fetchedTask = onTrackSystem.getTask("TASK001");
     TaskSubmission fetchedSubmission = onTrackSystem.getSubmission(submissionId);

     assertEquals("TASK001", fetchedTask.getTaskId());
     assertEquals(submissionId, fetchedSubmission.getSubmissionId());
 }
 @Test
 void testSetEmailInvalidFormatThrows() {
     assertThrows(IllegalArgumentException.class, () -> testStudent.setEmail("invalidEmail"));
 }
 @Test
 void testTutorAssignmentToUnit() {
     testTutor.assignToUnit("SIT707");
     assertTrue(testTutor.isAssignedToUnit("SIT707"));
 }
 @Test
 void testTaskIsOverdueReturnsTrue() {
     Task pastTask = new Task("T005", "Old Task", "desc", "SIT999", getPastDate(1), 50);
     assertTrue(pastTask.isOverdue());
 }
 @Test
 void testTaskDaysUntilDueWithFutureDate() {
     Task upcomingTask = new Task("T007", "Future", "desc", "SIT999", getFutureDate(3), 50);
     assertTrue(upcomingTask.getDaysUntilDue() <= 3);
 }
 @Test
 void testMessageTimestampNotNull() {
     Message msg = new Message("M002", "S001", "T001", "Hello");
     assertNotNull(msg.getTimestamp());
 }


}

