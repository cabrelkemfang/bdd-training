package io.upskilling.training.employee.service;

import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.dto.PaymentRequest;

public interface Payment {
    String makePayment(EmployeeResponse employee, PaymentRequest paymentRequest);
}
