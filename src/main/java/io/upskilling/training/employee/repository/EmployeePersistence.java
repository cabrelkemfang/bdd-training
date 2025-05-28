package io.upskilling.training.employee.repository;

import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmployeePersistence {
    Page<EmployeeResponse> findAll(Pageable pageable);

    Optional<EmployeeResponse> findById(Long id);

    void save(EmployeeRequest employeeRequest);

    void deleteById(Long employeeId);

    boolean existsByEmail(String email);
}
