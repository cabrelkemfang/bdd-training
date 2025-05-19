package io.upskilling.training.employee.stepDefinitons;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeStepDefinitions {

    private static final String API_PATH = "/api/employees";

    private Response response;
    private EmployeeRequest employeeRequest;
    private Long employeeId;

    @LocalServerPort
    private Integer port;

    @Before
    public void setup() {
        // Initialize RestAssured with the random port assigned by Spring Boot
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Given("L'utilisateur souhaite créer un employé avec les informations suivantes:")
    public void utilisateur_souhaite_creer_employe(EmployeeRequest employees) {
        this.employeeRequest = employees;
    }

    @When("L'utilisateur soumet la requête pour créer l'employé")
    public void utilisateur_soumet_requete_creer_employe() {
        response = given()
                .contentType(ContentType.JSON)
                .body(employeeRequest)
                .when()
                .post(API_PATH);
    }

    @Then("L'employé doit être créé avec succès avec le message: {string}")
    public void employe_cree_avec_succes(String message) {
        response.then()
                .statusCode(HttpStatus.CREATED.value());

        assertThat(response.getBody().asString())
                .isEqualTo(message);
    }
}
