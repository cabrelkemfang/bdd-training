package io.upskilling.training.employee.dto;

import io.upskilling.training.employee.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Department department;
}
