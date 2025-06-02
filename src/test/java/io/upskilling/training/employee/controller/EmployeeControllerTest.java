package io.upskilling.training.employee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.entity.Department;
import io.upskilling.training.employee.exception.EmployeeAlreadyExistsException;
import io.upskilling.training.employee.exception.InvalidEmailFormatException;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeControllerTest {

    private static final String API_PATH = "/api/employees";

    @Autowired
    private MockMvcTester mockMvcTester;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeRequest employeeRequest;

    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setUp() {
        employeeRequest = EmployeeRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("test@gmail.com")
                .phoneNumber("+23067676767")
                .department(Department.HR)
                .build();

        employeeResponse = EmployeeResponse.builder()
                .employeeId(1L)
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .email(employeeRequest.getEmail())
                .phoneNumber(employeeRequest.getPhoneNumber())
                .department(employeeRequest.getDepartment())
                .build();
    }


    @Test
    @Order(1)
    void shouldCreateEmployee() throws JsonProcessingException {
        var response = mockMvcTester.post()
                .uri(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequest))
                .exchange();

        assertThat(response)
                .isNotNull()
                .hasStatus(HttpStatus.CREATED)
                .bodyText()
                .isEqualTo("Employee created Successfully");
    }

    @Test
    @Order(2)
    void shouldFindAllEmployee() {
        var response = mockMvcTester.get()
                .uri(API_PATH)
                .param("page", "1")
                .param("size", "10")
                .exchange();


        assertThat(response)
                .isNotNull()
                .hasStatusOk()
                .bodyJson()
                .convertTo(InstanceOfAssertFactories.list(EmployeeResponse.class))
                .usingRecursiveComparison()
                .ignoringFields("localDateTime")
                .isEqualTo(List.of(employeeResponse));
    }

    @Test
    @Order(3)
    void shouldFailWhenCreateEmployee() throws JsonProcessingException {
        var request = EmployeeRequest.builder()
                .email("invalid-email")
                .build();

        var response = mockMvcTester.post()
                .uri(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .exchange();

        assertThat(response)
                .failure()
                .isInstanceOf(InvalidEmailFormatException.class)
                .hasMessage("Invalid email format: invalid-email");
    }

    @Test
    @Order(4)
    void shouldFailWhenCreateEmployeeWithSameEmail() throws JsonProcessingException {

        var response = mockMvcTester.post()
                .uri(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequest))
                .exchange();

        assertThat(response)
                .failure()
                .isInstanceOf(EmployeeAlreadyExistsException.class)
                .hasMessage("Employee with email test@gmail.com already exists.");
    }


    @Test
    @Order(5)
    void shouldFindEmployeeById() {
        var response = mockMvcTester.get()
                .uri(API_PATH + "/1")
                .exchange();

        assertThat(response)
                .isNotNull()
                .hasStatusOk()
                .bodyJson()
                .convertTo(EmployeeResponse.class)
                .usingRecursiveComparison()
                .ignoringFields("localDateTime")
                .isEqualTo(employeeResponse);
    }

}