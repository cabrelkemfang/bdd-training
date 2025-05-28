package io.upskilling.training.employee.controller;

import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.dto.PaymentRequest;
import io.upskilling.training.employee.service.impl.EmployeeService;
import io.upskilling.training.employee.utils.PaginationHeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    public static final String EMPLOYEE_CREATED_SUCCESSFULLY = "Employee created Successfully";
    private final EmployeeService employeeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeResponse>> findEmployee(@RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        var employee = employeeService.getEmployees(page, size);

        return ResponseEntity.ok()
                .headers(PaginationHeaderUtil.buildPaginationHeaders(employee))
                .contentType(MediaType.APPLICATION_JSON)
                .body(employee.response());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        employeeService.createEmployee(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(EMPLOYEE_CREATED_SUCCESSFULLY);
    }


    @GetMapping(value = "/{employee_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponse> findEmployeeById(@PathVariable("employee_id") Long employeeId) {
        var employee = employeeService.findEmployeeById(employeeId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(employee);
    }

    @PostMapping(value = "/{employee_id}/payments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> makePaymentForEmployee(@PathVariable("employee_id") Long employeeId,
                                                         @RequestBody PaymentRequest paymentRequest) {
        var paymentResponse = employeeService.makePaymentForEmployee(employeeId, paymentRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentResponse);
    }
}
