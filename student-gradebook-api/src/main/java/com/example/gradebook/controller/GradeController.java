package com.example.gradebook.controller;

import com.example.gradebook.model.Grade;
import com.example.gradebook.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    // Add a new grade
    @PostMapping
    public Grade addGrade(@RequestBody Grade grade) {
        return gradeService.addGrade(grade);
    }

    // Get all grades
    @GetMapping
    public List<Grade> getAllGrades() {
        return gradeService.getAllGrades();
    }

    // ✅ Get subject-wise averages for a student ID
    @GetMapping("/average/subjects/{studentId}")
    public Map<String, Double> getSubjectWiseAverages(@PathVariable String studentId) {
        return gradeService.getSubjectWiseAverages(studentId);
    }
}
