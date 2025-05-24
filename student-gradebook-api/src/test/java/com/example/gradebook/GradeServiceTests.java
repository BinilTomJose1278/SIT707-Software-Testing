package com.example.gradebook;


import com.example.gradebook.model.Grade;
import com.example.gradebook.service.GradeService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GradeServiceTests {

    @Test
    public void testAddGrade() {
        GradeService service = new GradeService();
        Grade grade = new Grade("1", "Math", 90.0);
        service.addGrade(grade);
        assertEquals(1, service.getAllGrades().size());
    }

    // ❌ FAILING TEST – for CI demo
    @Test
    public void testFailing() {
        assertEquals(90, 45 + 45); // Intentionally fails
    }
}
