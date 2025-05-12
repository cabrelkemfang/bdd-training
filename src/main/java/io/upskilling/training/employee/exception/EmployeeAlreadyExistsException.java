package io.upskilling.training.employee.exception;


import io.upskilling.training.employee.dto.EmployeeRequest;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(EmployeeRequest employee) {
        super("Employee with email " + employee.getEmail() + " already exists.");
    }
}
