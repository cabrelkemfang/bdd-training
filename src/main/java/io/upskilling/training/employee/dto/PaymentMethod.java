package io.upskilling.training.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    MCB("Mauritius Commercial Bank"),
    ABSA("ABSA Bank");

    final String name;
}
