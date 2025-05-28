package io.upskilling.training.employee.service;

import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.dto.PaginatedResponse;
import io.upskilling.training.employee.dto.PaymentRequest;

public interface IEmployeeService {
    PaginatedResponse<EmployeeResponse> getEmployees(int page, int size);

    void createEmployee(EmployeeRequest request);

    EmployeeResponse findEmployeeById(Long employeeId);

    void deleteEmployeeById(Long employeeId);

    String makePaymentForEmployee(Long employeeId, PaymentRequest paymentRequest);

    void sendEmailToAllEmployees(String message);
}
