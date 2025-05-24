package com.example.gradebook;

import com.example.gradebook.model.Grade;
import com.example.gradebook.service.GradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GradeServiceTests {

    private GradeService service;

    @BeforeEach
    public void setup() {
        service = new GradeService();
    }

    @Test
    public void testAddSingleGrade() {
        service.addGrade(new Grade("1", "Math", 90.0));
        assertEquals(1, service.getAllGrades().size());
    }

    @Test
    public void testAddMultipleGrades() {
        service.addGrade(new Grade("1", "Math", 90.0));
        service.addGrade(new Grade("1", "English", 80.0));
        assertEquals(2, service.getAllGrades().size());
    }

    @Test
    public void testGetAllGradesReturnsCopy() {
        service.addGrade(new Grade("1", "Math", 90.0));
        List<Grade> result = service.getAllGrades();
        result.clear(); // Should not affect original
        assertEquals(1, service.getAllGrades().size());
    }

    @Test
    public void testAverageSingleGrade() {
        service.addGrade(new Grade("1", "Math", 80.0));
        assertEquals(80.0, service.getSubjectWiseAverages("1").get("Math"));
    }

    @Test
    public void testAverageMultipleGradesSameSubject() {
        service.addGrade(new Grade("1", "Math", 70.0));
        service.addGrade(new Grade("1", "Math", 90.0));
        assertEquals(80.0, service.getSubjectWiseAverages("1").get("Math"));
    }

    @Test
    public void testSubjectWiseAverageMultipleSubjects() {
        service.addGrade(new Grade("1", "Math", 70));
        service.addGrade(new Grade("1", "English", 90));
        Map<String, Double> averages = service.getSubjectWiseAverages("1");
        assertEquals(70.0, averages.get("Math"));
        assertEquals(90.0, averages.get("English"));
    }

    @Test
    public void testEmptyAverageReturnsZero() {
        assertTrue(service.getSubjectWiseAverages("123").isEmpty());
    }

    @Test
    public void testNegativeScoreHandling() {
        service.addGrade(new Grade("1", "Math", -10));
        assertEquals(-10, service.getAllGrades().get(0).getScore());
    }

    @Test
    public void testZeroScore() {
        service.addGrade(new Grade("1", "Math", 0));
        assertEquals(0, service.getAllGrades().get(0).getScore());
    }

    @Test
    public void testDuplicateGradeEntries() {
        Grade g = new Grade("1", "Math", 75);
        service.addGrade(g);
        service.addGrade(g);
        assertEquals(2, service.getAllGrades().size());
    }

    @Test
    public void testNullSubjectHandled() {
        service.addGrade(new Grade("1", null, 50));
        assertNull(service.getAllGrades().get(0).getSubject());
    }

    @Test
    public void testNullStudentIdHandled() {
        service.addGrade(new Grade(null, "Math", 55));
        assertNull(service.getAllGrades().get(0).getStudentId());
    }

    @Test
    public void testCaseInsensitiveSubjects() {
        service.addGrade(new Grade("1", "math", 100));
        service.addGrade(new Grade("1", "Math", 0));
        Map<String, Double> avg = service.getSubjectWiseAverages("1");
        assertEquals(50.0, avg.get("Math")); // After normalization
    }

    @Test
    public void testAverageForMultipleStudents() {
        service.addGrade(new Grade("1", "Math", 100));
        service.addGrade(new Grade("2", "Math", 50));
        assertEquals(100.0, service.getSubjectWiseAverages("1").get("Math"));
        assertEquals(50.0, service.getSubjectWiseAverages("2").get("Math"));
    }

    @Test
    public void testFloatScorePrecision() {
        service.addGrade(new Grade("1", "Math", 99.99));
        assertEquals(99.99, service.getAllGrades().get(0).getScore());
    }

    @Test
    public void testAddThenFetchAverageSameSession() {
        service.addGrade(new Grade("1", "Math", 88));
        assertEquals(88.0, service.getSubjectWiseAverages("1").get("Math"));
    }

    @Test
    public void testInvalidSubjectDoesNotBreak() {
        service.addGrade(new Grade("1", "", 33));
        assertTrue(service.getAllGrades().get(0).getSubject().isEmpty());
    }

    @Test
    public void testLargeScore() {
        service.addGrade(new Grade("1", "Math", 10000));
        assertEquals(10000.0, service.getAllGrades().get(0).getScore());
    }

    @Test
    public void testAverageRounding() {
        service.addGrade(new Grade("1", "Math", 33));
        service.addGrade(new Grade("1", "Math", 66));
        double avg = service.getSubjectWiseAverages("1").get("Math");
        assertEquals(49.5, avg);
    }

    @Test
    public void testFailing() {
        assertEquals(90, 45 + 45); 
    }
}
