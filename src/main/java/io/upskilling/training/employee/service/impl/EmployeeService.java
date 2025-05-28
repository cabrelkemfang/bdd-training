package io.upskilling.training.employee.service.impl;

import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.dto.PaginatedResponse;
import io.upskilling.training.employee.exception.EmployeeNotFoundException;
import io.upskilling.training.employee.mapper.EmployeeMapper;
import io.upskilling.training.employee.repository.EmployeePersistence;
import io.upskilling.training.employee.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final EmployeePersistence employeePersistence;
    private final EmployeeMapper employeeMapper;
    private final EmployeeValidator employeeValidator;

    @Transactional(readOnly = true)
    public PaginatedResponse<EmployeeResponse> getEmployees(int page, int size) {
        var employeeResponsePage = employeePersistence.findAll(PageRequest.of(page - 1, size));

        return employeeMapper.buildResponse(employeeResponsePage);
    }

    public void createEmployee(EmployeeRequest request) {
        employeeValidator.validate(request);
        employeePersistence.save(request);
    }

    @Transactional(readOnly = true)
    public EmployeeResponse findEmployeeById(Long employeeId) {
        return employeePersistence.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public void deleteEmployeeById(Long employeeId) {
        var employeeEntity = employeePersistence.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employeePersistence.deleteById(employeeId);
    }
}
