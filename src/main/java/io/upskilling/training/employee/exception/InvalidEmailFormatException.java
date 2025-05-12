package io.upskilling.training.employee.exception;


import io.upskilling.training.employee.dto.EmployeeRequest;

public class InvalidEmailFormatException extends RuntimeException {

    public InvalidEmailFormatException(EmployeeRequest employeeRequest) {
        super("Invalid email format: " + employeeRequest.getEmail());
    }
}
