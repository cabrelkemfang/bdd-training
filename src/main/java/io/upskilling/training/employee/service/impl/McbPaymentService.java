package io.upskilling.training.employee.service.impl;

import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.dto.PaymentRequest;
import io.upskilling.training.employee.service.Payment;
import org.springframework.stereotype.Service;

@Service("MCB")
public class McbPaymentService implements Payment {
    @Override
    public String makePayment(EmployeeResponse employee, PaymentRequest paymentRequest) {
        // Implement MCB payment logic here
        return """
                Payment processed successfully via MCB 
                Employee ID: %d
                Employee: %s,
                Amount : %s
                """.formatted(employee.employeeId(), employee.firstName(), paymentRequest.getAmount());
    }
}
