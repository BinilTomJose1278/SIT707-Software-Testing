package com.example.gradebook.service;

import com.example.gradebook.model.Grade;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GradeService {

    private final List<Grade> grades = Collections.synchronizedList(new ArrayList<>());

    public Grade addGrade(Grade grade) {
        grades.add(grade);
        return grade;
    }

    public List<Grade> getAllGrades() {
        return new ArrayList<>(grades);
    }

    public Map<String, Double> getSubjectWiseAverages(String studentId) {
        return grades.stream()
                .filter(g -> g.getStudentId().equals(studentId))
                .collect(Collectors.groupingBy(
                    g -> capitalize(g.getSubject()),
                    Collectors.averagingDouble(Grade::getScore)
                ));
    }

    // Helper to capitalize only the first letter
    private String capitalize(String subject) {
        if (subject == null || subject.isEmpty()) return subject;
        return subject.substring(0, 1).toUpperCase() + subject.substring(1).toLowerCase();
    }

}
