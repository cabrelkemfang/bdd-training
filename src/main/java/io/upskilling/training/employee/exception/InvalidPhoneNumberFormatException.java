package io.upskilling.training.employee.exception;


import io.upskilling.training.employee.dto.EmployeeRequest;

public class InvalidPhoneNumberFormatException extends RuntimeException {

    public InvalidPhoneNumberFormatException(EmployeeRequest employeeRequest) {
        super("Invalid phone number format " + employeeRequest.getPhoneNumber());
    }
}
