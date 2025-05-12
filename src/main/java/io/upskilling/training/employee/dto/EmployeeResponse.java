package io.upskilling.training.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.upskilling.training.employee.entity.Department;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(description = "Response object containing employee information")
public record EmployeeResponse(
        @Schema(description = "Unique identifier of the employee", example = "1")
        Long employeeId,

        @Schema(description = "Employee's first name", example = "John")
        String firstName,

        @Schema(description = "Employee's last name", example = "Doe")
        String lastName,

        @Schema(description = "Employee's email address", example = "john.doe@example.com")
        String email,

        @Schema(description = "Employee's department")
        Department department,

        @Schema(description = "Timestamp when the employee was created", example = "2023-06-15T10:30:45")
        LocalDateTime localDateTime) {
}
