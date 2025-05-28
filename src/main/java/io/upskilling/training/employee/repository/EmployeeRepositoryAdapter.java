package io.upskilling.training.employee.repository;

import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements EmployeePersistence {

    private final EmployeeRepository employeeRepository;

    @Override
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    private final EmployeeMapper employeeMapper;

    @Override
    public Page<EmployeeResponse> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(employeeMapper::mapToEmployeeResponse);
    }

    @Override
    public Optional<EmployeeResponse> findById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::mapToEmployeeResponse);
    }

    @Override
    public void save(EmployeeRequest employeeRequest) {
        var employee = employeeMapper.mapToEmployee(employeeRequest);
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
