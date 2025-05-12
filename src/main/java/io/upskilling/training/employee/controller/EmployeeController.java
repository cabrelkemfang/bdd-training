package io.upskilling.training.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.dto.PaginatedResponse;
import io.upskilling.training.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Management", description = "APIs for managing employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(
            summary = "Get all employees",
            description = "Returns a paginated list of all employees",
            tags = {"Employee Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the list of employees",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EmployeeResponse.class)
                    )
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeResponse>> findEmployee(
            @Parameter(description = "Page number (1-based indexing)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        var employee = employeeService.getEmployees(page, size);

        return ResponseEntity.ok()
                .headers(getHttpHeaders(employee))
                .contentType(MediaType.APPLICATION_JSON)
                .body(employee.response());
    }

    @Operation(
            summary = "Create a new employee",
            description = "Creates a new employee with the provided information",
            tags = {"Employee Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Employee successfully created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(type = "string")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data or employee already exists",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createEmployee(
            @Parameter(description = "Employee information", required = true)
            @RequestBody EmployeeRequest employeeRequest) {
        employeeService.createEmployee(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Employee created Successfully");
    }

    @Operation(
            summary = "Get employee by ID",
            description = "Returns a single employee by their ID",
            tags = {"Employee Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the employee",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EmployeeResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{employee_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponse> findEmployeeById(
            @Parameter(description = "ID of the employee to retrieve", required = true, example = "1")
            @PathVariable("employee_id") Long employeeId) {
        var employee = employeeService.findEmployeeById(employeeId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(employee);
    }

    private static HttpHeaders getHttpHeaders(PaginatedResponse<EmployeeResponse> employee) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(employee.totalElements()));
        headers.add("X-Total-Pages", String.valueOf(employee.totalPages()));
        headers.add("X-Current-Page", String.valueOf(employee.currentPage()));
        headers.add("X-Page-Size", String.valueOf(employee.pageSize()));
        return headers;
    }
}
