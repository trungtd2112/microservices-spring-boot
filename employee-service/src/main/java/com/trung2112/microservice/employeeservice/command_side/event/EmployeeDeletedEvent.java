package com.trung2112.microservice.employeeservice.command_side.event;

public class EmployeeDeletedEvent {
    private String employeeId;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
