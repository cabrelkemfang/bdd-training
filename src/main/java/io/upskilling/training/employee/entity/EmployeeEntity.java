package io.upskilling.training.employee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Department department;
    private LocalDateTime localDateTime;

    @PrePersist
    private void onCreate() {
        this.localDateTime = LocalDateTime.now();
    }
}
