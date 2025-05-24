package com.ontrack.OnTrackProject;
import java.util.*;

public class Tutor extends User {
    private String employeeId;
    private List<String> assignedUnits;
    private String department;

    public Tutor(String userId, String username, String email, String firstName, String lastName, String employeeId, String department) {
        super(userId, username, email, firstName, lastName);

        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be null or empty");
        }

        this.employeeId = employeeId;
        this.department = department;
        this.assignedUnits = new ArrayList<>();
    }

    public void assignToUnit(String unitCode) {
        if (unitCode != null && !assignedUnits.contains(unitCode)) {
            assignedUnits.add(unitCode);
        }
    }

    public boolean isAssignedToUnit(String unitCode) {
        return assignedUnits.contains(unitCode);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public List<String> getAssignedUnits() {
        return new ArrayList<>(assignedUnits);
    }
}
