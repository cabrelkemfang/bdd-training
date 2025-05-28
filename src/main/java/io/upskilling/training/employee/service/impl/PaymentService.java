package io.upskilling.training.employee.service.impl;

import io.upskilling.training.employee.dto.PaymentRequest;
import io.upskilling.training.employee.service.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final EmployeeService employeeService;
    private final Map<String, Payment> paymentsMap;

    public String makePaymentForEmployee(Long employeeId, PaymentRequest paymentRequest) {
        var employee = employeeService.findEmployeeById(employeeId);
        var payment = paymentsMap.get(paymentRequest.getPaymentMethod().name());
        return payment.makePayment(employee, paymentRequest);
    }
}
