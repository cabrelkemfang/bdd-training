package io.upskilling.training.employee.service;

import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.dto.PaginatedResponse;
import io.upskilling.training.employee.exception.EmployeeNotFoundException;
import io.upskilling.training.employee.mapper.EmployeeMapper;
import io.upskilling.training.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeeValidator employeeValidator;

    @Transactional(readOnly = true)
    public PaginatedResponse<EmployeeResponse> getEmployees(int page, int size) {
        var employeeResponsePage = employeeRepository.findAll(PageRequest.of(page - 1, size))
                .map(employeeMapper::mapToEmployeeResponse);

        return employeeMapper.buildResponse(employeeResponsePage);
    }

    public void createEmployee(EmployeeRequest request) {
        employeeValidator.validate(request);
        var employee = employeeMapper.mapToEmployee(request);
        employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public EmployeeResponse findEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employeeMapper::mapToEmployeeResponse)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public void deleteEmployeeById(Long employeeId) {
        var employeeEntity = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employeeRepository.delete(employeeEntity);
    }
}
