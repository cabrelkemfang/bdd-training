package io.upskilling.training.employee.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long employeeId) {
        super("Employee Not Found " + employeeId);
    }
}
