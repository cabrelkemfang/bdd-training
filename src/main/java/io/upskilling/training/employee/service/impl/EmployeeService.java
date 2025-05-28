package io.upskilling.training.employee.service.impl;

import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.dto.PaginatedResponse;
import io.upskilling.training.employee.dto.PaymentRequest;
import io.upskilling.training.employee.entity.EmployeeEntity;
import io.upskilling.training.employee.exception.EmployeeNotFoundException;
import io.upskilling.training.employee.mapper.EmployeeMapper;
import io.upskilling.training.employee.repository.EmployeeRepository;
import io.upskilling.training.employee.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public void sendEmailToAllEmployees(String message) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private final EmployeeMapper employeeMapper;
    private final EmployeeValidator employeeValidator;

    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<EmployeeResponse> getEmployees(int page, int size) {
        var employeeResponsePage = employeeRepository.findAll(PageRequest.of(page - 1, size))
                .map(employeeMapper::mapToEmployeeResponse);

        return employeeMapper.buildResponse(employeeResponsePage);
    }

    @Override
    public void createEmployee(EmployeeRequest request) {
        employeeValidator.validate(request);
        var employee = employeeMapper.mapToEmployee(request);
        employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeResponse findEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employeeMapper::mapToEmployeeResponse)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    @Override
    public void deleteEmployeeById(Long employeeId) {
        var employeeEntity = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employeeRepository.delete(employeeEntity);
    }

    @Override
    public String makePaymentForEmployee(Long employeeId, PaymentRequest paymentRequest) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        return switch (paymentRequest.getPaymentMethod()) {
            case MCB -> mcbPaymentService(paymentRequest, employee);
            case ABSA -> absaPaymentService(paymentRequest, employee);
            default ->
                    throw new IllegalArgumentException("Unsupported payment method: " + paymentRequest.getPaymentMethod());
        };

    }

    private String absaPaymentService(PaymentRequest paymentRequest,
                                      EmployeeEntity employee) {
        // Implement ABSA payment logic here

        return """
                Payment processed successfully via ABSA 
                Employee ID: %d
                Employee: %s,
                Amount : %s
                """.formatted(employee.getEmployeeId(), employee.getFirstName(), paymentRequest.getAmount());
    }

    private String mcbPaymentService(PaymentRequest paymentRequest,
                                     EmployeeEntity employee) {
        // Implement MCB payment logic here
        return """
                Payment processed successfully via MCB 
                Employee ID: %d
                Employee: %s,
                Amount : %s
                """.formatted(employee.getEmployeeId(), employee.getFirstName(), paymentRequest.getAmount());
    }
}
