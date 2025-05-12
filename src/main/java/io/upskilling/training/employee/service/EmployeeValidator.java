package io.upskilling.training.employee.service;

import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.exception.EmployeeAlreadyExistsException;
import io.upskilling.training.employee.exception.InvalidEmailFormatException;
import io.upskilling.training.employee.exception.InvalidPhoneNumberFormatException;
import io.upskilling.training.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EmployeeValidator {

    private final EmployeeRepository employeeRepository;

    public void validate(EmployeeRequest employeeRequest) {
        // validate if  user id already present
        if (employeeRepository.existsByEmail(employeeRequest.getEmail())) {
            throw new EmployeeAlreadyExistsException(employeeRequest);
        }

        // Validate email
        if (!isValidEmail(employeeRequest.getEmail())) {
            throw new InvalidEmailFormatException(employeeRequest);
        }

        // Validate phone number (Mauritius)
        if (!isValidPhoneNumber(employeeRequest.getPhoneNumber())) {
            throw new InvalidPhoneNumberFormatException(employeeRequest);
        }
    }

    // Phone number validation for Mauritius format
    private static boolean isValidPhoneNumber(String phoneNumber) {
        // Mauritius phone numbers should be in the format: +230XXXXXXXX
        String phoneRegex = "^\\+230\\d{8}$";
        return phoneNumber != null && Pattern.matches(phoneRegex, phoneNumber);
    }

    // Email validation using regex
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && Pattern.matches(emailRegex, email);
    }
}
