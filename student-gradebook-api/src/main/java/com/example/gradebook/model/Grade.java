package com.example.gradebook.model;

public class Grade {
    private String studentId;
    private String subject;
    private double score;

    public Grade() {}

    public Grade(String studentId, String subject, double score) {
        this.studentId = studentId;
        this.subject = subject;
        this.score = score;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
