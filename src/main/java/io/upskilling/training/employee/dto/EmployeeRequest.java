package io.upskilling.training.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.upskilling.training.employee.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request object for creating or updating an employee")
public class EmployeeRequest {
    @Schema(description = "Employee's first name", example = "John", required = true)
    private String firstName;

    @Schema(description = "Employee's last name", example = "Doe", required = true)
    private String lastName;

    @Schema(description = "Employee's email address", example = "john.doe@example.com", required = true)
    private String email;

    @Schema(description = "Employee's phone number in Mauritius format", example = "+2301234567", required = true)
    private String phoneNumber;

    @Schema(description = "Employee's department", example = "IT", required = true)
    private Department department;
}
