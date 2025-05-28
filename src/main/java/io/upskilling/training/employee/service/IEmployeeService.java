package io.upskilling.training.employee.service;

import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.dto.PaginatedResponse;

public interface IEmployeeService {
    PaginatedResponse<EmployeeResponse> getEmployees(int page, int size);
    void createEmployee(EmployeeRequest request);
    EmployeeResponse findEmployeeById(Long employeeId);
    void deleteEmployeeById(Long employeeId);
}

