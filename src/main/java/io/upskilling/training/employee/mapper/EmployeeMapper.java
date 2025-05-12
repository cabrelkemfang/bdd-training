package io.upskilling.training.employee.mapper;

import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.dto.PaginatedResponse;
import io.upskilling.training.employee.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper
public interface EmployeeMapper {

    EmployeeResponse mapToEmployeeResponse(EmployeeEntity employeeEntity);

    EmployeeEntity mapToEmployee(EmployeeRequest employee);

    default PaginatedResponse<EmployeeResponse> buildResponse(Page<EmployeeResponse> employeeResponsePage) {
        return PaginatedResponse.<EmployeeResponse>builder()
                .response(employeeResponsePage.getContent())
                .totalElements(employeeResponsePage.getTotalElements())
                .totalPages(employeeResponsePage.getTotalPages())
                .currentPage(employeeResponsePage.getNumber() + 1)
                .pageSize(employeeResponsePage.getSize())
                .build();
    }
}
