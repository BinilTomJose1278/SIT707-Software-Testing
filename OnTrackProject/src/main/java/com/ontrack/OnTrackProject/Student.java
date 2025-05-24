package com.ontrack.OnTrackProject;
import java.util.*;

public class Student extends User {
    private String studentNumber;
    private List<String> enrolledUnits;
    private Map<String, Double> unitProgress;

    public Student(String userId, String username, String email, String firstName, String lastName, String studentNumber) {
        super(userId, username, email, firstName, lastName);

        if (studentNumber == null || studentNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Student number cannot be null or empty");
        }

        this.studentNumber = studentNumber;
        this.enrolledUnits = new ArrayList<>();
        this.unitProgress = new HashMap<>();
    }

    public void enrollInUnit(String unitCode) {
        if (unitCode == null || unitCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit code cannot be null or empty");
        }
        if (!enrolledUnits.contains(unitCode)) {
            enrolledUnits.add(unitCode);
            unitProgress.put(unitCode, 0.0);
        }
    }
    public void unenrollFromUnit(String unitCode) {
        if (unitCode != null && enrolledUnits.contains(unitCode)) {
            enrolledUnits.remove(unitCode);
            unitProgress.remove(unitCode);
        }
    }


    public double getUnitProgress(String unitCode) {
        return unitProgress.getOrDefault(unitCode, 0.0);
    }

    public void updateUnitProgress(String unitCode, double progress) {
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("Progress must be between 0 and 100");
        }
        if (enrolledUnits.contains(unitCode)) {
            unitProgress.put(unitCode, progress);
        }
    }

    public double getOverallProgress() {
        if (unitProgress.isEmpty()) return 0.0;
        return unitProgress.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public List<String> getEnrolledUnits() {
        return new ArrayList<>(enrolledUnits);
    }
}
